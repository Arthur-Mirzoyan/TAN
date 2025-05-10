package network;

import core.exceptions.IPNotFoundException;
import entities.user.User;
import entities.user.components.UserData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    private final UserData userData;
    private final ClientResources resources;

    private ConcurrentHashMap<String, UserData> connectedUsers = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<UserData> users = new CopyOnWriteArrayList<>();
    private boolean isReady;

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

    public boolean getIsReady() {
        return this.isReady;
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

    public void sendJSON() {
        sendJSON(users);
    }

    public void listenForServerMessages() {
        BufferedReader socketReader = resources.getSocketReader();

        try {
            String serverMessage;

            while ((serverMessage = socketReader.readLine()) != null) {
                System.out.println("ServerMessage: " + serverMessage);
                if (serverMessage.equals("START")) isReady = true;
                else
                    try {
                        JSONArray array = new JSONArray(serverMessage);

                        for (int i = 0; i < array.length(); i++) {
                            UserData userData = new UserData((JSONObject) array.get(i));
                            connectedUsers.put(userData.getIp(), userData);

                            boolean isFound = false;

                            for (UserData user : users)
                                if (user.getIp().equals(userData.getIp())) {
                                    user.updateTankScore(userData.getTank(), user.getScore());
                                    isFound = true;
                                }

                            if (!isFound) users.add(userData);
                        }
                    } catch (JSONException ex) {
                        System.out.println("Error: Message couldn't be parsed.");
                    }
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }

    public CopyOnWriteArrayList<UserData> getConnectedUsers() {
        return users;
    }

    public UserData getClientUser() {
        return connectedUsers.get(getUserData().getIp());
    }
}
