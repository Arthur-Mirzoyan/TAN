package network;

import core.exceptions.IPNotFoundException;
import entities.user.User;
import entities.user.components.UserData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    private final UserData userData;
    private final ClientResources resources;

    public Client(User user, String host, int port) throws IOException {
        this.userData = new UserData(user);
        this.resources = this.connectToServer(host, port);

        try {
            userData.setIp(IPHelper.getLocalIP());
        } catch (IPNotFoundException e) {
            System.out.println("Client IP not found!");
        } finally {
            sendJSON(userData.toJSON());
        }
    }

    public UserData getUserData() {
        return this.userData;
    }

    public ClientResources connectToServer(String host, int port) throws IOException {
        System.out.println("Connecting to " + host + ":" + port);

        Socket socket = new Socket(host, port);
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter clientWriter = new PrintWriter(socket.getOutputStream(), true);
        Scanner clientReader = new Scanner(System.in);

        return new ClientResources(socket, socketReader, clientWriter, clientReader);
    }

    public boolean isConnectedToServer() {
        return this.resources != null;
    }

    public void kill() {
        this.resources.close();
    }

    public void sendJSON(JSONObject json) {
        PrintWriter clientWriter = resources.getClientWriter();
        clientWriter.println(json.toString());
    }

    public void sendJSON(CopyOnWriteArrayList<UserData> users) {
        PrintWriter clientWriter = resources.getClientWriter();
        JSONArray json = new JSONArray();

        users.forEach(user -> json.put(user.toJSON()));

        clientWriter.println(json);
    }


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
