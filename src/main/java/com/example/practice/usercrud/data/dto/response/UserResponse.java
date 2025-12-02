package com.example.practice.usercrud.data.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * Data Transfer Object for user response data.
 * 
 * <p>This DTO represents the data returned to clients when querying user information.
 * It excludes sensitive fields (password, version) and includes only information
 * appropriate for API consumers.
 * 
 * <p><strong>Security Considerations:</strong>
 * <ul>
 *   <li>Password is never included in responses</li>
 *   <li>Version field is excluded (internal optimistic locking mechanism)</li>
 *   <li>Timestamps are included for audit and display purposes</li>
 * </ul>
 * 
 * <p><strong>Field Details:</strong>
 * <ul>
 *   <li>id: Unique identifier (read-only, system-generated)</li>
 *   <li>email: User's email address</li>
 *   <li>username: User's chosen username</li>
 *   <li>firstName/lastName: Optional personal information</li>
 *   <li>isActive: Account status (false = soft-deleted)</li>
 *   <li>createdAt/updatedAt: Audit timestamps (read-only)</li>
 *   <li>lastLoginAt: Last login timestamp (nullable)</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the user.
     * 
     * <p>System-generated, immutable after creation.
     * Used for referencing the user in subsequent API calls.
     */
    private Long id;

    /**
     * User's email address.
     * 
     * <p>Unique identifier used for authentication and communication.
     */
    private String email;

    /**
     * User's chosen username.
     * 
     * <p>Unique identifier used for display and identification.
     */
    private String username;

    /**
     * User's first name.
     * 
     * <p>Optional personal information. May be null.
     */
    private String firstName;

    /**
     * User's last name.
     * 
     * <p>Optional personal information. May be null.
     */
    private String lastName;

    /**
     * Account active status.
     * 
     * <p>Indicates whether the user account is active.
     * <ul>
     *   <li>{@code true}: Account is active and accessible</li>
     *   <li>{@code false}: Account is soft-deleted (inactive)</li>
     * </ul>
     */
    private Boolean isActive;

    /**
     * Timestamp when the user account was created.
     * 
     * <p>Immutable, set automatically on account creation.
     * Formatted as ISO-8601 string in UTC.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createdAt;

    /**
     * Timestamp when the user account was last modified.
     * 
     * <p>Updated automatically on every modification.
     * Formatted as ISO-8601 string in UTC.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant updatedAt;

    /**
     * Timestamp of the user's last login.
     * 
     * <p>Tracked for security and analytics purposes.
     * Nullable for users who have never logged in.
     * Formatted as ISO-8601 string in UTC.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant lastLoginAt;
}