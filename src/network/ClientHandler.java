package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private PrintWriter output;
    private String clientName;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            output = new PrintWriter(socket.getOutputStream(), true);

            // Ask for a name
            output.println("Enter your name:");
            clientName = input.readLine();
            System.out.println(clientName + " has joined.");
            server.broadcast(clientName + " has joined the chat!", this);

            String message;
            while ((message = input.readLine()) != null) {
                System.out.println(clientName + ": " + message);
                server.broadcast(clientName + ": " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            server.broadcast(clientName + " has left the chat.", this);

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }
}
