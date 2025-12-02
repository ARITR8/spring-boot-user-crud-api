package com.example.practice.usercrud.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object for user creation and update requests.
 * 
 * <p>This DTO represents the data sent by clients when creating or updating a user.
 * It excludes internal fields (id, timestamps, version) that are managed by the system.
 * 
 * <p><strong>Security Considerations:</strong>
 * <ul>
 *   <li>Password is accepted in plain text but must be hashed before persistence</li>
 *   <li>Never log or expose this DTO with password field</li>
 *   <li>Password validation ensures minimum security requirements</li>
 * </ul>
 * 
 * <p><strong>Validation Rules:</strong>
 * <ul>
 *   <li>Email: Required, valid format, max 255 characters</li>
 *   <li>Username: Required, 3-50 characters</li>
 *   <li>Password: Required, 8-255 characters (for create), optional for update</li>
 *   <li>First/Last name: Optional, max 100 characters each</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User's email address.
     * 
     * <p>Must be unique across all users. Used for authentication and communication.
     * 
     * <p>Constraints:
     * <ul>
     *   <li>Required for user creation</li>
     *   <li>Must be valid email format</li>
     *   <li>Maximum 255 characters</li>
     * </ul>
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    /**
     * User's chosen username.
     * 
     * <p>Must be unique across all users. Used for display and identification.
     * 
     * <p>Constraints:
     * <ul>
     *   <li>Required for user creation</li>
     *   <li>Between 3 and 50 characters</li>
     *   <li>Alphanumeric and common special characters allowed</li>
     * </ul>
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * User's password in plain text.
     * 
     * <p><strong>Security Warning:</strong> This password must be hashed using
     * a secure algorithm (BCrypt/Argon2) before persistence. Never store plain text passwords.
     * 
     * <p>Constraints:
     * <ul>
     *   <li>Required for user creation</li>
     *   <li>Between 8 and 255 characters</li>
     *   <li>Should contain mix of uppercase, lowercase, numbers, and special characters</li>
     * </ul>
     * 
     * <p><strong>Note:</strong> For update operations, password may be optional
     * if only updating other fields. Service layer should handle this logic.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    /**
     * User's first name.
     * 
     * <p>Optional field for personal identification.
     * Maximum length: 100 characters.
     */
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    /**
     * User's last name.
     * 
     * <p>Optional field for personal identification.
     * Maximum length: 100 characters.
     */
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;
}