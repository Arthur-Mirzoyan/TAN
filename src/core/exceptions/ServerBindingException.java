package core.exceptions;

/**
 * The {@code ServerBindingException} is thrown when the server fails to bind
 * to the specified port, typically due to permission or port conflict issues.
 */
public class ServerBindingException extends Exception {
    public ServerBindingException() {
        super("Server already running.");
    }

    /**
     * Constructs a {@code ServerBindingException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ServerBindingException(String message) {
        super(message);
    }
}
