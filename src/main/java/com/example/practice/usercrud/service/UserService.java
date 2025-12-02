package com.example.practice.usercrud.service;

import com.example.practice.usercrud.data.dto.request.UserRequest;
import com.example.practice.usercrud.data.dto.request.UserUpdateRequest;
import com.example.practice.usercrud.data.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for user management operations.
 * 
 * <p>This service provides business logic for user CRUD operations, including:
 * <ul>
 *   <li>User creation with password hashing</li>
 *   <li>User retrieval with various search criteria</li>
 *   <li>User updates with validation</li>
 *   <li>User deletion (soft delete)</li>
 *   <li>Business rule validation</li>
 * </ul>
 * 
 * <p><strong>Security:</strong>
 * <ul>
 *   <li>Passwords are hashed before persistence</li>
 *   <li>Email and username uniqueness is enforced</li>
 *   <li>Soft delete preserves data for audit</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
public interface UserService {

    /**
     * Creates a new user.
     * 
     * <p>Validates email and username uniqueness, hashes password, and persists the user.
     * 
     * @param userRequest the user creation request (must not be null)
     * @return the created user response
     * @throws UserAlreadyExistsException if email or username already exists
     * @throws ValidationException if validation fails
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Retrieves a user by ID.
     * 
     * @param id the user ID (must not be null)
     * @return the user response
     * @throws UserNotFoundException if user not found
     */
    UserResponse getUserById(Long id);

    /**
     * Retrieves a user by email.
     * 
     * @param email the email address (must not be null)
     * @return the user response
     * @throws UserNotFoundException if user not found
     */
    UserResponse getUserByEmail(String email);

    /**
     * Retrieves a user by username.
     * 
     * @param username the username (must not be null)
     * @return the user response
     * @throws UserNotFoundException if user not found
     */
    UserResponse getUserByUsername(String username);

    /**
     * Retrieves all active users with pagination.
     * 
     * @param pageable pagination and sorting parameters (must not be null)
     * @return a page of user responses
     */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Retrieves all active users as a list.
     * 
     * <p><strong>Warning:</strong> Use with caution on large datasets.
     * Prefer {@link #getAllUsers(Pageable)} for pagination support.
     * 
     * @return a list of all active users
     */
    List<UserResponse> getAllUsers();

    /**
     * Updates an existing user.
     * 
     * <p>Validates email and username uniqueness if being updated,
     * hashes password if provided, and updates only non-null fields.
     * 
     * @param id the user ID (must not be null)
     * @param userUpdateRequest the update request (must not be null)
     * @return the updated user response
     * @throws UserNotFoundException if user not found
     * @throws UserAlreadyExistsException if email or username already exists
     * @throws ValidationException if validation fails
     */
    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    /**
     * Deletes a user (soft delete).
     * 
     * <p>Sets isActive to false, preserving data for audit purposes.
     * 
     * @param id the user ID (must not be null)
     * @throws UserNotFoundException if user not found
     */
    void deleteUser(Long id);

    /**
     * Checks if a user exists by email.
     * 
     * @param email the email address (must not be null)
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists by username.
     * 
     * @param username the username (must not be null)
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);
}