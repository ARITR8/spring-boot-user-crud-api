package com.example.practice.usercrud.service.impl;

import com.example.practice.usercrud.data.dto.request.UserRequest;
import com.example.practice.usercrud.data.dto.request.UserUpdateRequest;
import com.example.practice.usercrud.data.dto.response.UserResponse;
import com.example.practice.usercrud.data.entity.User;
import com.example.practice.usercrud.exception.UserAlreadyExistsException;
import com.example.practice.usercrud.exception.UserNotFoundException;
import com.example.practice.usercrud.mapper.UserMapper;
import com.example.practice.usercrud.repository.UserRepository;
import com.example.practice.usercrud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} providing user management business logic.
 * 
 * <p>This service handles:
 * <ul>
 *   <li>User creation with password hashing and uniqueness validation</li>
 *   <li>User retrieval with various search criteria</li>
 *   <li>User updates with partial field updates and validation</li>
 *   <li>User deletion via soft delete (isActive = false)</li>
 *   <li>Business rule enforcement</li>
 * </ul>
 * 
 * <p><strong>Security Features:</strong>
 * <ul>
 *   <li>Passwords are hashed using BCrypt before persistence</li>
 *   <li>Email and username uniqueness is enforced</li>
 *   <li>Soft delete preserves data for audit trail</li>
 * </ul>
 * 
 * <p><strong>Transaction Management:</strong>
 * <ul>
 *   <li>Write operations (create, update, delete) are transactional</li>
 *   <li>Read operations use read-only transactions for performance</li>
 *   <li>Rollback on exceptions maintains data consistency</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserServiceImpl with required dependencies.
     * 
     * @param userRepository the user repository (must not be null)
     * @param userMapper the user mapper (must not be null)
     * @param passwordEncoder the password encoder (must not be null)
     */
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================================
    // Create Operations
    // ============================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserResponse createUser(@NonNull UserRequest userRequest) {
        logger.debug("Creating user with email: {}", userRequest.getEmail());

        // Validate uniqueness
        validateEmailUniqueness(userRequest.getEmail());
        validateUsernameUniqueness(userRequest.getUsername());

        // Map DTO to Entity
        User user = userMapper.toEntity(userRequest);

        // Hash password before persistence
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(hashedPassword);

        // Ensure user is active on creation
        user.setIsActive(true);

        // Save user
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    // ============================================================================
    // Read Operations
    // ============================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse getUserById(@NonNull Long id) {
        logger.debug("Retrieving user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse getUserByEmail(@NonNull String email) {
        logger.debug("Retrieving user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email, "email"));
        return userMapper.toResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponse getUserByUsername(@NonNull String username) {
        logger.debug("Retrieving user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username, "username"));
        return userMapper.toResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserResponse> getAllUsers(@NonNull Pageable pageable) {
        logger.debug("Retrieving all users with pagination: {}", pageable);
        return userRepository.findAllActiveUsers(pageable)
                .map(userMapper::toResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserResponse> getAllUsers() {
        logger.debug("Retrieving all users as list");
        return userRepository.findAllActiveUsers().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ============================================================================
    // Update Operations
    // ============================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserResponse updateUser(@NonNull Long id, @NonNull UserUpdateRequest userUpdateRequest) {
        logger.debug("Updating user with ID: {}", id);

        // Find existing user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Validate email uniqueness if email is being updated
        if (userUpdateRequest.getEmail() != null 
                && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            validateEmailUniqueness(userUpdateRequest.getEmail());
        }

        // Validate username uniqueness if username is being updated
        if (userUpdateRequest.getUsername() != null 
                && !userUpdateRequest.getUsername().equals(user.getUsername())) {
            validateUsernameUniqueness(userUpdateRequest.getUsername());
        }

        // Hash password if password is being updated
        if (userUpdateRequest.getPassword() != null 
                && !userUpdateRequest.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
            userUpdateRequest.setPassword(hashedPassword);
        } else {
            // Don't update password if not provided
            userUpdateRequest.setPassword(null);
        }

        // Update entity from DTO (only non-null fields)
        userMapper.updateEntityFromDto(userUpdateRequest, user);

        // Save updated user
        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully with ID: {}", updatedUser.getId());

        return userMapper.toResponse(updatedUser);
    }

    // ============================================================================
    // Delete Operations
    // ============================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteUser(@NonNull Long id) {
        logger.debug("Deleting user with ID: {}", id);

        // Verify user exists
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        // Soft delete
        int deletedCount = userRepository.softDeleteById(id);
        if (deletedCount == 0) {
            throw new UserNotFoundException(id);
        }

        logger.info("User soft-deleted successfully with ID: {}", id);
    }

    // ============================================================================
    // Existence Checks
    // ============================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByEmail(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByUsername(@NonNull String username) {
        return userRepository.existsByUsername(username);
    }

    // ============================================================================
    // Private Helper Methods
    // ============================================================================

    /**
     * Validates that the email is unique (not already in use).
     * 
     * @param email the email to validate (must not be null)
     * @throws UserAlreadyExistsException if email already exists
     */
    private void validateEmailUniqueness(@NonNull String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email, "email");
        }
    }

    /**
     * Validates that the username is unique (not already in use).
     * 
     * @param username the username to validate (must not be null)
     * @throws UserAlreadyExistsException if username already exists
     */
    private void validateUsernameUniqueness(@NonNull String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username, "username");
        }
    }
}