package core.exceptions;

public class IPNotFoundException extends Exception {
    public IPNotFoundException() {
        super("IP not found!");
    }

    public IPNotFoundException(String message) {
        super(message);
    }
}
