package core.exceptions;

/**
 * The {@code UserNotFoundException} is thrown when an expected user entity
 * cannot be located in the system, such as during login or data retrieval.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found.");
    }

    /**
     * Constructs a {@code UserNotFoundException} with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
