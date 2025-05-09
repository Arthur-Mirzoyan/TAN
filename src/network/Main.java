package network;

import entities.user.User;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    // Testing...
    public static void main(String[] args) throws IOException {
        Server server = new Server();

//        new Thread(() -> {
//            System.out.println("New Thread");
//            Client client1 = new Client("Artur1", server.getIp(), 5000);
//            Client client2 = new Client("Artur2", server.getIp(), 5000);
//
//            JSONObject json = new User("UserName", "PassWord").toJSON();
//
//            client1.sendJson(json);
//        }).start();

//        server.run();

        System.out.println("Server IP: " + server.getIp());
    }
}