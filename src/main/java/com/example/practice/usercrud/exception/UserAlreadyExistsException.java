package com.example.practice.usercrud.exception;

/**
 * Exception thrown when attempting to create a user with an email or username
 * that already exists in the system.
 * 
 * <p>This exception is typically thrown when:
 * <ul>
 *   <li>Creating a user with an email that already exists</li>
 *   <li>Creating a user with a username that already exists</li>
 *   <li>Updating a user's email/username to one that's already taken</li>
 * </ul>
 * 
 * <p><strong>HTTP Status:</strong> 409 Conflict
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UserAlreadyExistsException with a default message.
     */
    public UserAlreadyExistsException() {
        super("User already exists");
    }

    /**
     * Constructs a new UserAlreadyExistsException with a custom message.
     * 
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserAlreadyExistsException with a message formatted with the email.
     * 
     * @param email the email that already exists
     */
    public UserAlreadyExistsException(String email, String fieldName) {
        super(String.format("User with %s '%s' already exists", fieldName, email));
    }

    /**
     * Constructs a new UserAlreadyExistsException with a custom message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}