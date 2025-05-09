package network;

import core.exceptions.ServerBindingException;
import core.exceptions.ServerCreationException;
import entities.user.components.UserData;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 5000;
    public static final int THREAD_POOL_SIZE = 10;

    private String ip;
    private ArrayList<ClientHandler> clients;
    private ConcurrentHashMap<String, UserData> connectedUsers;
    private CopyOnWriteArrayList<UserData> users;

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

    public void run() throws ServerCreationException, ServerBindingException {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Messenger Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(this, clientSocket, connectedUsers, users);
                clients.add(clientHandler);
                pool.execute(clientHandler);
            }
        } catch (BindException e) {
            throw new ServerBindingException();
        } catch (IOException e) {
            throw new ServerCreationException();
        } finally {
            pool.shutdown();
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients)
            if (client != sender)  // Avoid sending the message back to the sender
                client.sendMessage(message);
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public CopyOnWriteArrayList<UserData> getConnectedUsers() {
        return users;
    }

    public UserData getClientUser(Client client) {
        return connectedUsers.get(client.getUserData().getIp());
    }
}