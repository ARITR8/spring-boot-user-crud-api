package com.example.practice.usercrud.exception;

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when validation of user data fails.
 * 
 * <p>This exception is typically thrown when:
 * <ul>
 *   <li>Business rule validation fails</li>
 *   <li>Data integrity constraints are violated</li>
 *   <li>Invalid data format is provided</li>
 * </ul>
 * 
 * <p><strong>HTTP Status:</strong> 400 Bad Request
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Map<String, List<String>> validationErrors;

    /**
     * Constructs a new ValidationException with a default message.
     */
    public ValidationException() {
        super("Validation failed");
        this.validationErrors = null;
    }

    /**
     * Constructs a new ValidationException with a custom message.
     * 
     * @param message the detail message
     */
    public ValidationException(String message) {
        super(message);
        this.validationErrors = null;
    }

    /**
     * Constructs a new ValidationException with validation errors map.
     * 
     * @param message the detail message
     * @param validationErrors map of field names to list of error messages
     */
    public ValidationException(String message, Map<String, List<String>> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    /**
     * Constructs a new ValidationException with a custom message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.validationErrors = null;
    }

    /**
     * Returns the validation errors map.
     * 
     * @return map of field names to list of error messages, or null if not set
     */
    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    /**
     * Checks if validation errors are present.
     * 
     * @return true if validation errors map is not null and not empty
     */
    public boolean hasValidationErrors() {
        return validationErrors != null && !validationErrors.isEmpty();
    }
}