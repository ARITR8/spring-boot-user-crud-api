package com.example.practice.usercrud.controller;

import com.example.practice.usercrud.data.dto.request.UserRequest;
import com.example.practice.usercrud.data.dto.request.UserUpdateRequest;
import com.example.practice.usercrud.data.dto.response.UserResponse;
import com.example.practice.usercrud.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user management operations.
 * 
 * <p>This controller provides HTTP endpoints for user CRUD operations:
 * <ul>
 *   <li>POST /api/users - Create a new user</li>
 *   <li>GET /api/users/{id} - Get user by ID</li>
 *   <li>GET /api/users/email/{email} - Get user by email</li>
 *   <li>GET /api/users/username/{username} - Get user by username</li>
 *   <li>GET /api/users - Get all users (with pagination)</li>
 *   <li>PUT /api/users/{id} - Update a user</li>
 *   <li>DELETE /api/users/{id} - Delete a user (soft delete)</li>
 * </ul>
 * 
 * <p><strong>API Design Principles:</strong>
 * <ul>
 *   <li>RESTful resource naming conventions</li>
 *   <li>Proper HTTP status codes</li>
 *   <li>Request/Response DTOs for data transfer</li>
 *   <li>Input validation via @Valid annotations</li>
 *   <li>Comprehensive error handling via GlobalExceptionHandler</li>
 * </ul>
 * 
 * <p><strong>Security:</strong>
 * <ul>
 *   <li>All endpoints require proper authentication (configured in SecurityConfig)</li>
 *   <li>Passwords are never returned in responses</li>
 *   <li>Input validation prevents malicious data</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Constructs a new UserController with required dependencies.
     * 
     * @param userService the user service (must not be null)
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============================================================================
    // Create Operations
    // ============================================================================

    /**
     * Creates a new user.
     * 
     * <p><strong>Endpoint:</strong> POST /api/users
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>201 Created - User successfully created</li>
     *   <li>400 Bad Request - Validation failed</li>
     *   <li>409 Conflict - Email or username already exists</li>
     * </ul>
     * 
     * @param userRequest the user creation request (must not be null, validated)
     * @return the created user response with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody @NonNull UserRequest userRequest) {
        logger.info("Creating new user with email: {}", userRequest.getEmail());
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    // ============================================================================
    // Read Operations
    // ============================================================================

    /**
     * Retrieves a user by ID.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/{id}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - User found</li>
     *   <li>404 Not Found - User not found</li>
     * </ul>
     * 
     * @param id the user ID (must not be null)
     * @return the user response with HTTP 200 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @NonNull Long id) {
        logger.debug("Retrieving user by ID: {}", id);
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Retrieves a user by email.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/email/{email}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - User found</li>
     *   <li>404 Not Found - User not found</li>
     * </ul>
     * 
     * @param email the email address (must not be null)
     * @return the user response with HTTP 200 status
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable @NonNull String email) {
        logger.debug("Retrieving user by email: {}", email);
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Retrieves a user by username.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/username/{username}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - User found</li>
     *   <li>404 Not Found - User not found</li>
     * </ul>
     * 
     * @param username the username (must not be null)
     * @return the user response with HTTP 200 status
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable @NonNull String username) {
        logger.debug("Retrieving user by username: {}", username);
        UserResponse userResponse = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Retrieves all active users with pagination.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users
     * <p><strong>Query Parameters:</strong>
     * <ul>
     *   <li>page - Page number (default: 0)</li>
     *   <li>size - Page size (default: 20)</li>
     *   <li>sort - Sort criteria (e.g., "email,asc" or "createdAt,desc")</li>
     * </ul>
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - Users retrieved successfully</li>
     * </ul>
     * 
     * <p><strong>Example:</strong> GET /api/users?page=0&size=10&sort=email,asc
     * 
     * @param pageable pagination and sorting parameters
     * @return a page of user responses with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(size = 20, sort = "createdAt") @NonNull Pageable pageable) {
        logger.debug("Retrieving all users with pagination: {}", pageable);
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves all active users as a list (without pagination).
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/all
     * <p><strong>Warning:</strong> Use with caution on large datasets.
     * Prefer {@code GET /api/users} with pagination for better performance.
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - Users retrieved successfully</li>
     * </ul>
     * 
     * @return a list of all user responses with HTTP 200 status
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsersList() {
        logger.debug("Retrieving all users as list");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ============================================================================
    // Update Operations
    // ============================================================================

    /**
     * Updates an existing user.
     * 
     * <p><strong>Endpoint:</strong> PUT /api/users/{id}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - User successfully updated</li>
     *   <li>400 Bad Request - Validation failed</li>
     *   <li>404 Not Found - User not found</li>
     *   <li>409 Conflict - Email or username already exists</li>
     * </ul>
     * 
     * <p><strong>Note:</strong> Only non-null fields in the request will be updated.
     * Password is optional - only update if provided.
     * 
     * @param id the user ID (must not be null)
     * @param userUpdateRequest the update request (must not be null, validated)
     * @return the updated user response with HTTP 200 status
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UserUpdateRequest userUpdateRequest) {
        logger.info("Updating user with ID: {}", id);
        UserResponse userResponse = userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok(userResponse);
    }

    // ============================================================================
    // Delete Operations
    // ============================================================================

    /**
     * Deletes a user (soft delete).
     * 
     * <p><strong>Endpoint:</strong> DELETE /api/users/{id}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>204 No Content - User successfully deleted</li>
     *   <li>404 Not Found - User not found</li>
     * </ul>
     * 
     * <p><strong>Note:</strong> This performs a soft delete (sets isActive = false).
     * User data is preserved for audit purposes.
     * 
     * @param id the user ID (must not be null)
     * @return HTTP 204 No Content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NonNull Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================================
    // Utility Endpoints
    // ============================================================================

    /**
     * Checks if a user exists by email.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/exists/email/{email}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - Always returns (check response body for existence)</li>
     * </ul>
     * 
     * @param email the email address (must not be null)
     * @return true if user exists, false otherwise
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable @NonNull String email) {
        logger.debug("Checking if email exists: {}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * Checks if a user exists by username.
     * 
     * <p><strong>Endpoint:</strong> GET /api/users/exists/username/{username}
     * <p><strong>Status Codes:</strong>
     * <ul>
     *   <li>200 OK - Always returns (check response body for existence)</li>
     * </ul>
     * 
     * @param username the username (must not be null)
     * @return true if user exists, false otherwise
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable @NonNull String username) {
        logger.debug("Checking if username exists: {}", username);
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
}