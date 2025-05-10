package core.exceptions;

/**
 * The {@code IPNotFoundException} is thrown when the IP address required for
 * a network operation cannot be located.
 */
public class IPNotFoundException extends Exception {
    public IPNotFoundException() {
        super("IP not found!");
    }

    /**
     * Constructs an {@code IPNotFoundException} with the specified detail message.
     *
     * @param message the detail message
     */
    public IPNotFoundException(String message) {
        super(message);
    }
}
