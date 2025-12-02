package com.example.practice.usercrud.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * User entity representing a user account in the system.
 * 
 * <p>This entity encapsulates user identity and profile information.
 * It extends {@link BaseEntity} to inherit common persistence fields
 * (id, timestamps, version).
 * 
 * <p>Key characteristics:
 * <ul>
 *   <li>Email serves as unique identifier and login credential</li>
 *   <li>Username provides human-readable identifier</li>
 *   <li>Password is stored (should be hashed via service layer)</li>
 *   <li>Soft delete via isActive flag preserves audit trail</li>
 * </ul>
 * 
 * <p>Database constraints:
 * <ul>
 *   <li>Email must be unique across all users</li>
 *   <li>Username must be unique across all users</li>
 *   <li>Indexes on email and username for fast lookups</li>
 * </ul>
 * 
 * @author System Generated
 * @since 1.0.0
 */
@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_username", columnList = "username", unique = true),
        @Index(name = "idx_users_active", columnList = "is_active")
    }
)
@Getter
@Setter
@ToString(callSuper = true, exclude = {"password"}) // Exclude password from toString for security
@EqualsAndHashCode(callSuper = true, exclude = {"password"}) // Exclude password from equals/hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    /**
     * User's email address.
     * 
     * <p>Must be unique, non-null, and valid email format.
     * Used as primary login identifier and for communication.
     * 
     * <p>Maximum length: 255 characters (standard email length limit).
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * User's chosen username.
     * 
     * <p>Must be unique, non-null, and between 3-50 characters.
     * Used for display and identification purposes.
     * 
     * <p>Constraints:
     * <ul>
     *   <li>Minimum: 3 characters (prevents trivial usernames)</li>
     *   <li>Maximum: 50 characters (reasonable display limit)</li>
     * </ul>
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * User's hashed password.
     * 
     * <p>Stored in hashed format (BCrypt/Argon2 recommended).
     * Never stored in plain text. Hashing should be performed
     * in the service layer before persistence.
     * 
     * <p>Maximum length: 255 characters (accommodates various hashing algorithms).
     * 
     * <p>Security note: This field is excluded from toString() and equals()
     * to prevent accidental logging or comparison of sensitive data.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * User's first name.
     * 
     * <p>Optional field for personal identification.
     * Maximum length: 100 characters.
     */
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Column(name = "first_name", length = 100)
    private String firstName;

    /**
     * User's last name.
     * 
     * <p>Optional field for personal identification.
     * Maximum length: 100 characters.
     */
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Column(name = "last_name", length = 100)
    private String lastName;

    /**
     * Soft delete flag indicating whether the user account is active.
     * 
     * <p>When false, the user account is considered deleted but
     * data is retained for audit purposes. This approach:
     * <ul>
     *   <li>Preserves referential integrity</li>
     *   <li>Maintains audit trail</li>
     *   <li>Enables account recovery if needed</li>
     * </ul>
     * 
     * <p>Default value: true (active on creation).
     */
    @NotNull(message = "Active status is required")
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp of the user's last login.
     * 
     * <p>Tracked for security and analytics purposes.
     * Nullable to handle users who have never logged in.
     * Must be in the past or present (not future).
     */
    @PastOrPresent(message = "Last login date cannot be in the future")
    @Column(name = "last_login_at")
    private Instant lastLoginAt;
}