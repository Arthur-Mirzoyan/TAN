package network;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        new Thread(() -> {
            System.out.println("New Thread");
            Client client = new Client("Artur", server.getIp(), 5000);
            client.startMessaging();
        }).start();

        server.run();

        System.out.println("Server IP: " + server.getIp());
    }
}