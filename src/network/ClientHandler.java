package network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import entities.user.components.UserData;

class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;

    private PrintWriter output;
    private ConcurrentHashMap<String, UserData> connectedUsers;
    private String clientIp;

    public ClientHandler(Server server, Socket socket, ConcurrentHashMap<String, UserData> connectedUsers) {
        this.server = server;
        this.socket = socket;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            output = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                try {
                    UserData userData = new UserData(new JSONObject(message));
                    String username = userData.getUsername();
                    clientIp = socket.getInetAddress().getHostAddress();

                    userData.setIp(clientIp);

                    connectedUsers.put(clientIp, userData);

                    System.out.println(username + ": " + message);
                    server.broadcast(message, this);
                } catch (JSONException e) {
                    System.out.println("Error: Message couldn't be parsed.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        } finally {
            server.removeClient(this);
            connectedUsers.remove(clientIp);

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
