package core.exceptions;

public class ServerBindingException extends Exception {
    public ServerBindingException() {
        super("Server already running.");
    }

    public ServerBindingException(String message) {
        super(message);
    }
}
