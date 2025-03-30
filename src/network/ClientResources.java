package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientResources implements AutoCloseable {
    private final Socket socket;
    private final BufferedReader socketReader;
    private final PrintWriter clientWriter;
    private final Scanner clientReader;

    public ClientResources(Socket socket, BufferedReader socketReader, PrintWriter clientWriter, Scanner clientReader) {
        this.socket = socket;
        this.socketReader = socketReader;
        this.clientWriter = clientWriter;
        this.clientReader = clientReader;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }

    public PrintWriter getClientWriter() {
        return clientWriter;
    }

    public Scanner getClientReader() {
        return clientReader;
    }

    @Override
    public void close() {
        try {
            if (socket != null) socket.close();
            if (socketReader != null) socketReader.close();
            if (clientWriter != null) clientWriter.close();
            if (clientReader != null) clientReader.close();
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }
}
