package core.exceptions;

/**
 * The {@code ServerCreationException} is thrown when an error occurs while
 * attempting to initialize or start the server.
 */
public class ServerCreationException extends Exception {
    public ServerCreationException() {
        super("Server couldn't be created.");
    }

    /**
     * Constructs a {@code ServerCreationException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ServerCreationException(String message) {
        super(message);
    }
}
