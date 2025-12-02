package com.example.practice.usercrud.exception;

/**
 * Exception thrown when a requested user cannot be found in the system.
 * 
 * <p>This exception is typically thrown when:
 * <ul>
 *   <li>Querying a user by ID that doesn't exist</li>
 *   <li>Querying a user by email/username that doesn't exist</li>
 *   <li>Attempting to update/delete a non-existent user</li>
 * </ul>
 * 
 * <p><strong>HTTP Status:</strong> 404 Not Found
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UserNotFoundException with a default message.
     */
    public UserNotFoundException() {
        super("User not found");
    }

    /**
     * Constructs a new UserNotFoundException with a custom message.
     * 
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with a message formatted with the user ID.
     * 
     * @param id the user ID that was not found
     */
    public UserNotFoundException(Long id) {
        super(String.format("User with ID %d not found", id));
    }

    /**
     * Constructs a new UserNotFoundException with a message formatted with the identifier.
     * 
     * @param identifier the email or username that was not found
     * @param identifierType the type of identifier ("email" or "username")
     */
    public UserNotFoundException(String identifier, String identifierType) {
        super(String.format("User with %s '%s' not found", identifierType, identifier));
    }

    /**
     * Constructs a new UserNotFoundException with a custom message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}