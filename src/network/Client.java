package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String name;
    private final ClientResources resources;

    public Client(String name, String host, int port) {
        this.name = name;
        this.resources = this.connectToServer(host, port);
    }

    public String getName() {
        return this.name;
    }

    // Method to connect to the server and return the ClientResources object
    public ClientResources connectToServer(String host, int port) {
        try {
            System.out.println("Connecting to " + host + ":" + port);
            Socket socket = new Socket(host, port);
            System.out.println("socket");
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("socketReader");
            PrintWriter clientWriter = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("clientWriter");
            Scanner clientReader = new Scanner(System.in);
            System.out.println("clientReader");

            return new ClientResources(socket, socketReader, clientWriter, clientReader);
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
            return null;
        }
    }

    public boolean isConnectedToServer() {
        return this.resources != null;
    }

    public void kill() {
        this.resources.close();
    }

    // Method to handle user messaging and server responses
    public void startMessaging() {
        Scanner clientReader = resources.getClientReader();
        PrintWriter clientWriter = resources.getClientWriter();

        // Start a separate thread to listen for messages from the server
        new Thread(this::listenForServerMessages).start();

        // Start message loop for user input
        System.out.println("Start messaging (type 'exit' to quit):");
        while (true) {
            System.out.print("> ");
            String message = clientReader.nextLine();
            clientWriter.println(message); // Send the message to the server
        }
    }

    // Method to listen for messages from the server
    private void listenForServerMessages() {
        BufferedReader socketReader = resources.getSocketReader();

        try {
            String serverMessage = socketReader.readLine();

            while (serverMessage != null) {
                System.out.println("\n" + serverMessage + "> ");
                serverMessage = socketReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }
}
