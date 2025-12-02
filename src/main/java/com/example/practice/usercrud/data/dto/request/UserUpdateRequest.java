package com.example.practice.usercrud.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object for partial user update requests.
 * 
 * <p>This DTO is used for update operations where all fields are optional,
 * allowing clients to update only specific fields without providing the entire user object.
 * 
 * <p><strong>Differences from UserRequest:</strong>
 * <ul>
 *   <li>All fields are optional (no @NotBlank constraints)</li>
 *   <li>Password update is optional (only provided if changing password)</li>
 *   <li>Email and username can be updated (with uniqueness validation in service layer)</li>
 * </ul>
 * 
 * <p><strong>Validation Rules:</strong>
 * <ul>
 *   <li>Email: Optional, but if provided must be valid format, max 255 characters</li>
 *   <li>Username: Optional, but if provided must be 3-50 characters</li>
 *   <li>Password: Optional, but if provided must be 8-255 characters</li>
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
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User's email address.
     * 
     * <p>Optional for updates. If provided, must be unique and valid format.
     */
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    /**
     * User's chosen username.
     * 
     * <p>Optional for updates. If provided, must be unique and 3-50 characters.
     */
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * User's password in plain text.
     * 
     * <p><strong>Security Warning:</strong> This password must be hashed before persistence.
     * 
     * <p>Optional for updates - only provide if changing password.
     * If provided, must be 8-255 characters.
     */
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    /**
     * User's first name.
     * 
     * <p>Optional field for personal identification.
     */
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    /**
     * User's last name.
     * 
     * <p>Optional field for personal identification.
     */
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;
}