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

/**
 * The <code>Client</code> class represents a connected game client.
 * It maintains connection to a server via sockets and handles the communication
 * logic including sending user data and listening for messages.
 */
public class Client {
    private final UserData userData;
    private final ClientResources resources;

    private ConcurrentHashMap<String, UserData> connectedUsers = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<UserData> users = new CopyOnWriteArrayList<>();
    private boolean isReady;

    /**
     * Constructs a <code>Client</code> object and connects to the specified server.
     * Sends initial user data upon successful connection.
     *
     * @param user the user object to associate with the client
     * @param host the IP address of the server to connect to
     * @param port the port number of the server
     * @throws IOException if the connection fails
     */
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

    /**
     * Returns the current <code>UserData</code> associated with the client.
     *
     * @return the client’s user data
     */
    public UserData getUserData() {
        return this.userData;
    }

    /**
     * Connects the client to a server and initializes I/O streams.
     *
     * @param host the server address
     * @param port the server port
     * @return a populated <code>ClientResources</code> object
     * @throws IOException if the connection setup fails
     */
    public ClientResources connectToServer(String host, int port) throws IOException {
        System.out.println("Connecting to " + host + ":" + port);

        Socket socket = new Socket(host, port);
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter clientWriter = new PrintWriter(socket.getOutputStream(), true);
        Scanner clientReader = new Scanner(System.in);

        return new ClientResources(socket, socketReader, clientWriter, clientReader);
    }

    /**
     * Returns whether the client is currently connected to a server.
     *
     * @return <code>true</code> if connected; <code>false</code> otherwise
     */
    public boolean isConnectedToServer() {
        return this.resources != null;
    }

    /**
     * Terminates the client's connection and closes resources.
     */
    public void kill() {
        this.resources.close();
    }

    /**
     * Sends a JSON object to the server.
     *
     * @param json the <code>JSONObject</code> to send
     */
    public void sendJSON(JSONObject json) {
        PrintWriter clientWriter = resources.getClientWriter();
        clientWriter.println(json.toString());
    }

    /**
     * Sends a list of users to the server as a JSON array.
     *
     * @param users the list of users to send
     */
    public void sendJSON(CopyOnWriteArrayList<UserData> users) {
        PrintWriter clientWriter = resources.getClientWriter();
        JSONArray json = new JSONArray();

        users.forEach(user -> json.put(user.toJSON()));

        clientWriter.println(json);
    }

    public void sendJSON() {
        sendJSON(users);
    }

    /**
     * Starts listening for server messages.
     * Messages are printed to the console when received.
     */
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
