package network;

import entities.user.components.UserData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The <code>ClientHandler</code> class manages communication between the server and a single client.
 * It reads data sent by the client, updates the user list, and broadcasts messages to other clients.
 */
class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private final ConcurrentHashMap<String, UserData> connectedUsers;
    private final CopyOnWriteArrayList<UserData> users;

    private PrintWriter output;
    private String clientIp;

    /**
     * Constructs a new handler for a connected client.
     *
     * @param server         the server instance managing this client
     * @param socket         the client socket
     * @param connectedUsers the map of connected users keyed by IP
     * @param users          the active user list
     */
    public ClientHandler(Server server, Socket socket, ConcurrentHashMap<String, UserData> connectedUsers, CopyOnWriteArrayList<UserData> users) {
        this.server = server;
        this.socket = socket;
        this.connectedUsers = connectedUsers;
        this.users = users;
    }

    /**
     * Starts listening for messages from the client.
     * Handles user registration and updates or broadcasts JSON messages to all clients.
     */
    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            output = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                try {
                    UserData userData = new UserData(new JSONObject(message));
                    clientIp = socket.getInetAddress().getHostAddress();

                    userData.setIp(clientIp);
                    connectedUsers.put(clientIp, userData);
                    users.add(userData);

                    server.broadcast(message, this);
                } catch (JSONException e) {
                    try {
                        JSONArray array = new JSONArray(message);

                        for (int i = 0; i < array.length(); i++) {
                            UserData userData = new UserData((JSONObject) array.get(i));
                            connectedUsers.put(userData.getIp(), userData);

                            for (UserData user : users)
                                if (user.getIp().equals(userData.getIp()))
                                    user.updateTankScore(userData.getTank(), user.getScore());
                        }
                    } catch (JSONException ex) {
                        System.out.println("Error: Message couldn't be parsed.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            server.removeClient(this);
            users.remove(connectedUsers.get(clientIp));
            connectedUsers.remove(clientIp);

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a string message to the client.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        output.println(message);
    }
}
