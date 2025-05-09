package core.exceptions;

public class ServerCreationException extends Exception {
    public ServerCreationException() {
        super("Server couldn't be created.");
    }

    public ServerCreationException(String message) {
        super(message);
    }
}
