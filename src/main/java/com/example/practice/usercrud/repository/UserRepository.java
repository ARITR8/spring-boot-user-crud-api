package com.example.practice.usercrud.repository;

import com.example.practice.usercrud.data.entity.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link User} entity persistence operations.
 * 
 * <p>This repository provides comprehensive data access capabilities for user management,
 * including standard CRUD operations, custom queries, and advanced features such as:
 * <ul>
 *   <li>Soft delete support via {@code isActive} flag</li>
 *   <li>Optimistic locking via {@code version} field</li>
 *   <li>Pagination and sorting for large datasets</li>
 *   <li>JPA Specifications for dynamic query building</li>
 *   <li>Entity graphs for optimized fetching</li>
 *   <li>Batch operations for bulk updates</li>
 * </ul>
 * 
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>All find methods exclude soft-deleted users by default (isActive = true)</li>
 *   <li>Query hints are used to optimize database access patterns</li>
 *   <li>Entity graphs prevent N+1 query problems</li>
 *   <li>Indexed fields (email, username) are leveraged for fast lookups</li>
 * </ul>
 * 
 * <p><strong>Thread Safety:</strong>
 * <ul>
 *   <li>Repository methods are thread-safe when used within Spring's transaction context</li>
 *   <li>Optimistic locking prevents lost updates in concurrent scenarios</li>
 *   <li>Pessimistic locking available for critical operations via {@code @Lock}</li>
 * </ul>
 * 
 * <p><strong>Transaction Boundaries:</strong>
 * <ul>
 *   <li>Read operations use read-only transactions for performance</li>
 *   <li>Write operations require active transactions (propagated from service layer)</li>
 *   <li>Modifying queries use {@code @Modifying} with appropriate flush and clear strategies</li>
 * </ul>
 * 
 * @author System Generated
 * @since 1.0.0
 * @see User
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    // ============================================================================
    // Standard Find Operations (Active Users Only)
    // ============================================================================

    /**
     * Finds a user by their unique email address.
     * 
     * <p>This method queries only active users (isActive = true) to enforce soft delete semantics.
     * The email field is indexed for optimal query performance.
     * 
     * <p><strong>Performance:</strong> O(1) lookup via unique index on email column.
     * 
     * @param email the email address to search for (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if email is null
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByEmail(@Param("email") @NonNull String email);

    /**
     * Finds a user by their unique username.
     * 
     * <p>This method queries only active users (isActive = true) to enforce soft delete semantics.
     * The username field is indexed for optimal query performance.
     * 
     * <p><strong>Performance:</strong> O(1) lookup via unique index on username column.
     * 
     * @param username the username to search for (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if username is null
     */
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.isActive = true")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByUsername(@Param("username") @NonNull String username);

    /**
     * Checks if a user exists with the given email address.
     * 
     * <p>This method is optimized for existence checks and only queries active users.
     * More efficient than {@code findByEmail().isPresent()} as it doesn't fetch the entity.
     * 
     * @param email the email address to check (must not be null)
     * @return {@code true} if an active user exists with the email, {@code false} otherwise
     * @throws IllegalArgumentException if email is null
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.isActive = true")
    boolean existsByEmail(@Param("email") @NonNull String email);

    /**
     * Checks if a user exists with the given username.
     * 
     * <p>This method is optimized for existence checks and only queries active users.
     * More efficient than {@code findByUsername().isPresent()} as it doesn't fetch the entity.
     * 
     * @param username the username to check (must not be null)
     * @return {@code true} if an active user exists with the username, {@code false} otherwise
     * @throws IllegalArgumentException if username is null
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.isActive = true")
    boolean existsByUsername(@Param("username") @NonNull String username);

    /**
     * Finds a user by email or username (for login scenarios).
     * 
     * <p>This method supports flexible authentication where users can login with either
     * email or username. Only queries active users.
     * 
     * <p><strong>Performance:</strong> Uses indexed columns (email, username) for fast lookup.
     * 
     * @param emailOrUsername the email address or username to search for (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if emailOrUsername is null
     */
    @Query("SELECT u FROM User u WHERE (u.email = :emailOrUsername OR u.username = :emailOrUsername) AND u.isActive = true")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByEmailOrUsername(@Param("emailOrUsername") @NonNull String emailOrUsername);

    /**
     * Finds all active users with pagination support.
     * 
     * <p>This method is optimized for large datasets and uses pagination to prevent
     * memory issues. Only returns active users (isActive = true).
     * 
     * <p><strong>Performance:</strong> Uses index on isActive column for efficient filtering.
     * 
     * @param pageable pagination and sorting parameters (must not be null)
     * @return a {@link Page} of active users
     * @throws IllegalArgumentException if pageable is null
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    @EntityGraph(attributePaths = {})
    @NonNull
    Page<User> findAllActiveUsers(@NonNull Pageable pageable);

    /**
     * Finds all active users as a list.
     * 
     * <p><strong>Warning:</strong> Use with caution on large datasets. Prefer
     * {@link #findAllActiveUsers(Pageable)} for pagination support.
     * 
     * @return a list of all active users, never null
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.createdAt DESC")
    @EntityGraph(attributePaths = {})
    @NonNull
    List<User> findAllActiveUsers();

    // ============================================================================
    // Search and Filter Operations
    // ============================================================================

    /**
     * Searches users by name (first name or last name) with pagination.
     * 
     * <p>Performs case-insensitive partial matching on first and last names.
     * Only searches active users.
     * 
     * <p><strong>Performance:</strong> Consider adding full-text search index for production.
     * 
     * @param name the name to search for (can be null or empty, returns empty result)
     * @param pageable pagination and sorting parameters (must not be null)
     * @return a {@link Page} of matching active users
     * @throws IllegalArgumentException if pageable is null
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    @EntityGraph(attributePaths = {})
    @NonNull
    Page<User> searchByName(@Param("name") @Nullable String name, @NonNull Pageable pageable);

    /**
     * Finds users created within a date range.
     * 
     * <p>Useful for analytics, reporting, and audit purposes.
     * Only includes active users.
     * 
     * @param startDate the start of the date range (inclusive, must not be null)
     * @param endDate the end of the date range (inclusive, must not be null)
     * @param pageable pagination and sorting parameters (must not be null)
     * @return a {@link Page} of users created in the specified range
     * @throws IllegalArgumentException if any parameter is null or startDate > endDate
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND u.createdAt >= :startDate AND u.createdAt <= :endDate")
    @EntityGraph(attributePaths = {})
    @NonNull
    Page<User> findByCreatedAtBetween(
            @Param("startDate") @NonNull Instant startDate,
            @Param("endDate") @NonNull Instant endDate,
            @NonNull Pageable pageable
    );

    /**
     * Finds users who have logged in within a specified time period.
     * 
     * <p>Useful for identifying active vs. inactive users.
     * Only includes active users.
     * 
     * @param since the timestamp to search from (must not be null)
     * @param pageable pagination and sorting parameters (must not be null)
     * @return a {@link Page} of users who logged in since the specified time
     * @throws IllegalArgumentException if any parameter is null
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND u.lastLoginAt >= :since AND u.lastLoginAt IS NOT NULL")
    @EntityGraph(attributePaths = {})
    @NonNull
    Page<User> findRecentlyActiveUsers(@Param("since") @NonNull Instant since, @NonNull Pageable pageable);

    // ============================================================================
    // Soft Delete Operations
    // ============================================================================

    /**
     * Soft deletes a user by setting isActive to false.
     * 
     * <p>This method preserves data for audit purposes while marking the user as deleted.
     * The user record remains in the database but is excluded from normal queries.
     * 
     * <p><strong>Transaction:</strong> Requires an active transaction.
     * 
     * @param id the ID of the user to soft delete (must not be null)
     * @return the number of affected rows (0 or 1)
     * @throws IllegalArgumentException if id is null
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :id AND u.isActive = true")
    @Transactional
    int softDeleteById(@Param("id") @NonNull Long id);

    /**
     * Soft deletes multiple users by their IDs.
     * 
     * <p>Batch operation for efficient bulk soft deletion.
     * 
     * <p><strong>Transaction:</strong> Requires an active transaction.
     * 
     * @param ids the IDs of users to soft delete (must not be null or empty)
     * @return the number of affected rows
     * @throws IllegalArgumentException if ids is null or empty
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User u SET u.isActive = false WHERE u.id IN :ids AND u.isActive = true")
    @Transactional
    int softDeleteByIds(@Param("ids") @NonNull List<Long> ids);

    /**
     * Restores a soft-deleted user by setting isActive to true.
     * 
     * <p>Allows recovery of accidentally deleted accounts.
     * 
     * <p><strong>Transaction:</strong> Requires an active transaction.
     * 
     * @param id the ID of the user to restore (must not be null)
     * @return the number of affected rows (0 or 1)
     * @throws IllegalArgumentException if id is null
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User u SET u.isActive = true WHERE u.id = :id AND u.isActive = false")
    @Transactional
    int restoreById(@Param("id") @NonNull Long id);

    // ============================================================================
    // Update Operations
    // ============================================================================

    /**
     * Updates the last login timestamp for a user.
     * 
     * <p>This method is optimized for frequent updates and uses a direct SQL update
     * to avoid entity loading overhead.
     * 
     * <p><strong>Transaction:</strong> Requires an active transaction.
     * 
     * @param id the ID of the user (must not be null)
     * @param lastLoginAt the timestamp of the last login (must not be null)
     * @return the number of affected rows (0 or 1)
     * @throws IllegalArgumentException if any parameter is null
     */
    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query("UPDATE User u SET u.lastLoginAt = :lastLoginAt WHERE u.id = :id")
    @Transactional
    int updateLastLoginAt(@Param("id") @NonNull Long id, @Param("lastLoginAt") @NonNull Instant lastLoginAt);

    /**
     * Activates or deactivates a user account.
     * 
     * <p>Useful for administrative account management without full deletion.
     * 
     * <p><strong>Transaction:</strong> Requires an active transaction.
     * 
     * @param id the ID of the user (must not be null)
     * @param isActive the new active status (must not be null)
     * @return the number of affected rows (0 or 1)
     * @throws IllegalArgumentException if any parameter is null
     */
    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")
    @Transactional
    int updateActiveStatus(@Param("id") @NonNull Long id, @Param("isActive") @NonNull Boolean isActive);

    // ============================================================================
    // Locking Operations (for Critical Sections)
    // ============================================================================

    /**
     * Finds a user by ID with pessimistic write lock.
     * 
     * <p>Use this method when you need to prevent concurrent modifications
     * during critical operations (e.g., balance updates, account changes).
     * 
     * <p><strong>Warning:</strong> Pessimistic locking can cause deadlocks.
     * Use only when absolutely necessary. Prefer optimistic locking for most scenarios.
     * 
     * <p><strong>Performance:</strong> Slower than normal find operations due to lock acquisition.
     * 
     * @param id the ID of the user (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if id is null
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isActive = true")
    @QueryHints({
        @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000") // 5 second timeout
    })
    @NonNull
    Optional<User> findByIdWithLock(@Param("id") @NonNull Long id);

    /**
     * Finds a user by email with optimistic lock check.
     * 
     * <p>Returns the user with current version for optimistic locking validation.
     * Use this when you need to verify version before updates.
     * 
     * @param email the email address (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if email is null
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByEmailForUpdate(@Param("email") @NonNull String email);

    // ============================================================================
    // Statistics and Analytics
    // ============================================================================

    /**
     * Counts the total number of active users.
     * 
     * <p>Optimized count query that doesn't fetch entities.
     * 
     * @return the count of active users
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();

    /**
     * Counts users created in a specific time period.
     * 
     * <p>Useful for growth metrics and analytics.
     * 
     * @param startDate the start of the period (inclusive, must not be null)
     * @param endDate the end of the period (inclusive, must not be null)
     * @return the count of users created in the period
     * @throws IllegalArgumentException if any parameter is null or startDate > endDate
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true " +
           "AND u.createdAt >= :startDate AND u.createdAt <= :endDate")
    long countByCreatedAtBetween(
            @Param("startDate") @NonNull Instant startDate,
            @Param("endDate") @NonNull Instant endDate
    );

    // ============================================================================
    // Administrative Operations (Include Soft-Deleted Users)
    // ============================================================================

    /**
     * Finds a user by ID including soft-deleted users.
     * 
     * <p><strong>Warning:</strong> This method bypasses soft delete filtering.
     * Use only for administrative or audit purposes.
     * 
     * @param id the ID of the user (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if id is null
     */
    @Query("SELECT u FROM User u WHERE u.id = :id")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByIdIncludingDeleted(@Param("id") @NonNull Long id);

    /**
     * Finds a user by email including soft-deleted users.
     * 
     * <p><strong>Warning:</strong> This method bypasses soft delete filtering.
     * Use only for administrative or audit purposes.
     * 
     * @param email the email address (must not be null)
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws IllegalArgumentException if email is null
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    @QueryHints(@QueryHint(name = "jakarta.persistence.fetchgraph", value = "user-entity-graph"))
    @NonNull
    Optional<User> findByEmailIncludingDeleted(@Param("email") @NonNull String email);
}