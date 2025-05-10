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

class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private final ConcurrentHashMap<String, UserData> connectedUsers;
    private final CopyOnWriteArrayList<UserData> users;

    private PrintWriter output;
    private String clientIp;

    public ClientHandler(Server server, Socket socket, ConcurrentHashMap<String, UserData> connectedUsers, CopyOnWriteArrayList<UserData> users) {
        this.server = server;
        this.socket = socket;
        this.connectedUsers = connectedUsers;
        this.users = users;
    }

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

                            System.out.println("Updating users");
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

    public void sendMessage(String message) {
        output.println(message);
    }
}
