package network;

import core.exceptions.ServerBindingException;
import core.exceptions.ServerCreationException;
import entities.user.components.UserData;
import org.json.JSONArray;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The <code>Server</code> class handles socket connections from clients.
 * It manages client handlers, broadcasts messages, and tracks active users.
 */
public class Server {
    public static final int PORT = 5000;
    public static final int THREAD_POOL_SIZE = 10;

    private String ip;
    private ArrayList<ClientHandler> clients;
    private ConcurrentHashMap<String, UserData> connectedUsers;
    private CopyOnWriteArrayList<UserData> users;
    private boolean isRunning;
    private ServerSocket serverSocket;

    /**
     * Initializes the server with host IP and internal data structures.
     */
    public Server() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ip = localHost.getHostAddress();
            clients = new ArrayList<>();
            connectedUsers = new ConcurrentHashMap<>();
            users = new CopyOnWriteArrayList<>();
        } catch (UnknownHostException e) {
            System.out.println("Error: Couldn't get local host.");
        }
    }

    public String getIp() {
        return ip;
    }

    /**
     * Starts the server and listens for client connections.
     * Each client is handled in a separate thread.
     *
     * @throws ServerCreationException if I/O fails during server start
     * @throws ServerBindingException  if port is already in use
     */
    public void run() throws ServerCreationException, ServerBindingException {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        isRunning = true;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Messenger Server is running...");

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    ClientHandler clientHandler = new ClientHandler(this, clientSocket, connectedUsers, users);
                    clients.add(clientHandler);
                    pool.execute(clientHandler);
                } catch (SocketException e) {
                    // This happens when serverSocket is closed during kill()
                    if (isRunning) {
                        System.out.println("Socket error: " + e.getMessage());
                    }
                    break;
                }
            }

        } catch (BindException e) {
            throw new ServerBindingException();
        } catch (IOException e) {
            throw new ServerCreationException();
        } finally {
            pool.shutdown();
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender.
     *
     * @param message the message to send
     * @param sender  the client handler who sent the message
     */
    public void broadcast(String message, ClientHandler sender) {
        System.out.println("Broadcasting: " + message);
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Removes a disconnected client from the list.
     *
     * @param clientHandler the client to remove
     */
    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    /**
     * Returns the list of currently connected users.
     */
    public CopyOnWriteArrayList<UserData> getConnectedUsers() {
        return users;
    }

    /**
     * Returns the user associated with a given client connection.
     */
    public UserData getClientUser(Client client) {
        return connectedUsers.get(client.getUserData().getIp());
    }

    public void kill() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // This will unblock accept()
            }
        } catch (IOException e) {
            System.out.println("Error closing server socket: " + e.getMessage());
        }
    }

    public void sendJSON() {
        JSONArray json = new JSONArray();

        users.forEach(user -> json.put(user.toJSON()));

        broadcast(json.toString(), null);
    }
}
