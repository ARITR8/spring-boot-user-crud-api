package com.example.practice.usercrud.repository.specification;

import com.example.practice.usercrud.data.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class providing JPA Specifications for dynamic query building on {@link User} entities.
 * 
 * <p>This class implements the Specification pattern, enabling type-safe, composable query
 * construction without writing custom repository methods for every possible search combination.
 * Specifications can be combined using {@link Specification#and(Specification)} and
 * {@link Specification#or(Specification)} for complex filtering scenarios.
 * 
 * <p><strong>Usage Examples:</strong>
 * <pre>{@code
 * // Simple search by email
 * Specification<User> spec = UserSpecification.hasEmail("user@example.com");
 * List<User> users = userRepository.findAll(spec);
 * 
 * // Combined criteria
 * Specification<User> spec = UserSpecification.isActive(true)
 *     .and(UserSpecification.hasFirstNameContaining("John"))
 *     .and(UserSpecification.createdAfter(Instant.now().minus(30, ChronoUnit.DAYS)));
 * Page<User> users = userRepository.findAll(spec, pageable);
 * 
 * // Complex search with OR conditions
 * Specification<User> spec = UserSpecification.hasEmailContaining("gmail")
 *     .or(UserSpecification.hasUsernameContaining("admin"));
 * List<User> users = userRepository.findAll(spec);
 * }</pre>
 * 
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>Specifications are translated to SQL at query execution time</li>
 *   <li>Indexed columns (email, username, isActive) are leveraged automatically</li>
 *   <li>Case-insensitive searches may not use indexes (consider full-text search for production)</li>
 *   <li>Multiple OR conditions may require query optimization</li>
 * </ul>
 * 
 * <p><strong>Null Safety:</strong>
 * <ul>
 *   <li>All methods handle null input parameters gracefully</li>
 *   <li>Null parameters result in no-op specifications (always true)</li>
 *   <li>Use {@link #alwaysTrue()} and {@link #alwaysFalse()} for explicit boolean logic</li>
 * </ul>
 * 
 * <p><strong>Thread Safety:</strong>
 * <ul>
 *   <li>All methods are stateless and thread-safe</li>
 *   <li>Specification objects are immutable and can be safely shared</li>
 * </ul>
 * 
 * @author System Generated
 * @since 1.0.0
 * @see Specification
 * @see org.springframework.data.jpa.domain.Specification
 */
public final class UserSpecification {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private UserSpecification() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ============================================================================
    // Basic Field Filters
    // ============================================================================

    /**
     * Creates a specification that matches users with the exact email address.
     * 
     * <p>This is a case-sensitive exact match. For case-insensitive matching,
     * use {@link #hasEmailContaining(String)} with proper normalization.
     * 
     * <p><strong>Performance:</strong> Uses unique index on email column for O(1) lookup.
     * 
     * @param email the email address to match (null returns always-true specification)
     * @return a specification matching users with the given email
     */
    @NonNull
    public static Specification<User> hasEmail(@Nullable String email) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(email)) {
                return cb.conjunction(); // Always true (no filter)
            }
            return cb.equal(root.get("email"), email);
        };
    }

    /**
     * Creates a specification that matches users whose email contains the given substring.
     * 
     * <p>This performs a case-insensitive partial match, useful for search functionality.
     * 
     * <p><strong>Performance:</strong> May not use index efficiently. Consider full-text search
     * for production systems with large datasets.
     * 
     * @param emailSubstring the substring to search for in email (null returns always-true specification)
     * @return a specification matching users whose email contains the substring
     */
    @NonNull
    public static Specification<User> hasEmailContaining(@Nullable String emailSubstring) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(emailSubstring)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("email")), 
                         "%" + emailSubstring.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification that matches users with the exact username.
     * 
     * <p>This is a case-sensitive exact match. For case-insensitive matching,
     * use {@link #hasUsernameContaining(String)} with proper normalization.
     * 
     * <p><strong>Performance:</strong> Uses unique index on username column for O(1) lookup.
     * 
     * @param username the username to match (null returns always-true specification)
     * @return a specification matching users with the given username
     */
    @NonNull
    public static Specification<User> hasUsername(@Nullable String username) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(username)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("username"), username);
        };
    }

    /**
     * Creates a specification that matches users whose username contains the given substring.
     * 
     * <p>This performs a case-insensitive partial match, useful for search functionality.
     * 
     * <p><strong>Performance:</strong> May not use index efficiently. Consider full-text search
     * for production systems with large datasets.
     * 
     * @param usernameSubstring the substring to search for in username (null returns always-true specification)
     * @return a specification matching users whose username contains the substring
     */
    @NonNull
    public static Specification<User> hasUsernameContaining(@Nullable String usernameSubstring) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(usernameSubstring)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("username")), 
                          "%" + usernameSubstring.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification that matches users with the exact first name.
     * 
     * <p>This is a case-sensitive exact match.
     * 
     * @param firstName the first name to match (null returns always-true specification)
     * @return a specification matching users with the given first name
     */
    @NonNull
    public static Specification<User> hasFirstName(@Nullable String firstName) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(firstName)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("firstName"), firstName);
        };
    }

    /**
     * Creates a specification that matches users whose first name contains the given substring.
     * 
     * <p>This performs a case-insensitive partial match.
     * 
     * @param firstNameSubstring the substring to search for in first name (null returns always-true specification)
     * @return a specification matching users whose first name contains the substring
     */
    @NonNull
    public static Specification<User> hasFirstNameContaining(@Nullable String firstNameSubstring) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(firstNameSubstring)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("firstName")), 
                          "%" + firstNameSubstring.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification that matches users with the exact last name.
     * 
     * <p>This is a case-sensitive exact match.
     * 
     * @param lastName the last name to match (null returns always-true specification)
     * @return a specification matching users with the given last name
     */
    @NonNull
    public static Specification<User> hasLastName(@Nullable String lastName) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(lastName)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("lastName"), lastName);
        };
    }

    /**
     * Creates a specification that matches users whose last name contains the given substring.
     * 
     * <p>This performs a case-insensitive partial match.
     * 
     * @param lastNameSubstring the substring to search for in last name (null returns always-true specification)
     * @return a specification matching users whose last name contains the substring
     */
    @NonNull
    public static Specification<User> hasLastNameContaining(@Nullable String lastNameSubstring) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(lastNameSubstring)) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("lastName")), 
                          "%" + lastNameSubstring.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification that matches users whose full name (first + last) contains the given substring.
     * 
     * <p>This performs a case-insensitive search across both first and last names,
     * useful for user search functionality.
     * 
     * <p><strong>Performance:</strong> Combines two LIKE operations with OR, may be slow on large datasets.
     * Consider full-text search indexes for production.
     * 
     * @param nameSubstring the substring to search for in full name (null returns always-true specification)
     * @return a specification matching users whose name contains the substring
     */
    @NonNull
    public static Specification<User> hasNameContaining(@Nullable String nameSubstring) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(nameSubstring)) {
                return cb.conjunction();
            }
            String lowerPattern = "%" + nameSubstring.toLowerCase() + "%";
            Predicate firstNameMatch = cb.like(cb.lower(root.get("firstName")), lowerPattern);
            Predicate lastNameMatch = cb.like(cb.lower(root.get("lastName")), lowerPattern);
            return cb.or(firstNameMatch, lastNameMatch);
        };
    }

    // ============================================================================
    // Status and Active Filters
    // ============================================================================

    /**
     * Creates a specification that matches users based on their active status.
     * 
     * <p>This is the primary filter for soft delete functionality.
     * Active users have {@code isActive = true}, deleted users have {@code isActive = false}.
     * 
     * <p><strong>Performance:</strong> Uses index on isActive column for efficient filtering.
     * 
     * @param isActive the active status to filter by (null returns always-true specification)
     * @return a specification matching users with the given active status
     */
    @NonNull
    public static Specification<User> isActive(@Nullable Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("isActive"), isActive);
        };
    }

    /**
     * Creates a specification that matches only active users (soft delete filter).
     * 
     * <p>This is a convenience method equivalent to {@code isActive(true)}.
     * Use this as the default filter in most queries to exclude soft-deleted users.
     * 
     * @return a specification matching only active users
     */
    @NonNull
    public static Specification<User> isActiveOnly() {
        return isActive(true);
    }

    /**
     * Creates a specification that matches only inactive (soft-deleted) users.
     * 
     * <p>Useful for administrative queries and audit purposes.
     * 
     * @return a specification matching only inactive users
     */
    @NonNull
    public static Specification<User> isInactiveOnly() {
        return isActive(false);
    }

    // ============================================================================
    // Date Range Filters
    // ============================================================================

    /**
     * Creates a specification that matches users created on or after the given date.
     * 
     * <p>Useful for filtering new users or users created within a time period.
     * 
     * @param date the minimum creation date (inclusive, null returns always-true specification)
     * @return a specification matching users created on or after the date
     */
    @NonNull
    public static Specification<User> createdAfter(@Nullable Instant date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("createdAt"), date);
        };
    }

    /**
     * Creates a specification that matches users created on or before the given date.
     * 
     * @param date the maximum creation date (inclusive, null returns always-true specification)
     * @return a specification matching users created on or before the date
     */
    @NonNull
    public static Specification<User> createdBefore(@Nullable Instant date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), date);
        };
    }

    /**
     * Creates a specification that matches users created within a date range.
     * 
     * <p>Both dates are inclusive. If either date is null, that boundary is ignored.
     * 
     * @param startDate the start of the date range (inclusive, null means no lower bound)
     * @param endDate the end of the date range (inclusive, null means no upper bound)
     * @return a specification matching users created in the date range
     * @throws IllegalArgumentException if startDate is after endDate
     */
        @NonNull
    public static Specification<User> createdBetween(@Nullable Instant startDate, @Nullable Instant endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date must be before or equal to end date");
            }
            
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
            }
            
            return predicates.isEmpty() 
                ? cb.conjunction() 
                : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Creates a specification that matches users updated on or after the given date.
     * 
     * @param date the minimum update date (inclusive, null returns always-true specification)
     * @return a specification matching users updated on or after the date
     */
    @NonNull
    public static Specification<User> updatedAfter(@Nullable Instant date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
        };
    }

    /**
     * Creates a specification that matches users updated on or before the given date.
     * 
     * @param date the maximum update date (inclusive, null returns always-true specification)
     * @return a specification matching users updated on or before the date
     */
    @NonNull
    public static Specification<User> updatedBefore(@Nullable Instant date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("updatedAt"), date);
        };
    }

    /**
     * Creates a specification that matches users who logged in on or after the given date.
     * 
     * <p>Useful for identifying recently active users.
     * 
     * @param date the minimum last login date (inclusive, null returns always-true specification)
     * @return a specification matching users who logged in on or after the date
     */
    @NonNull
    public static Specification<User> lastLoginAfter(@Nullable Instant date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.and(
                cb.isNotNull(root.get("lastLoginAt")),
                cb.greaterThanOrEqualTo(root.get("lastLoginAt"), date)
            );
        };
    }

    /**
     * Creates a specification that matches users who have never logged in.
     * 
     * <p>Useful for identifying inactive accounts or accounts requiring activation.
     * 
     * @return a specification matching users with null lastLoginAt
     */
    @NonNull
    public static Specification<User> hasNeverLoggedIn() {
        return (root, query, cb) -> cb.isNull(root.get("lastLoginAt"));
    }

    /**
     * Creates a specification that matches users who have logged in at least once.
     * 
     * @return a specification matching users with non-null lastLoginAt
     */
    @NonNull
    public static Specification<User> hasLoggedIn() {
        return (root, query, cb) -> cb.isNotNull(root.get("lastLoginAt"));
    }

    // ============================================================================
    // ID and Collection Filters
    // ============================================================================

    /**
     * Creates a specification that matches users with the given ID.
     * 
     * @param id the user ID to match (null returns always-true specification)
     * @return a specification matching users with the given ID
     */
    @NonNull
    public static Specification<User> hasId(@Nullable Long id) {
        return (root, query, cb) -> {
            if (id == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("id"), id);
        };
    }

    /**
     * Creates a specification that matches users whose ID is in the given collection.
     * 
     * <p>Useful for bulk operations and filtering by multiple IDs.
     * 
     * @param ids the collection of user IDs to match (null or empty returns always-true specification)
     * @return a specification matching users with IDs in the collection
     */
    @NonNull
    public static Specification<User> hasIdIn(@Nullable Collection<Long> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("id").in(ids);
        };
    }

    /**
     * Creates a specification that matches users whose ID is not in the given collection.
     * 
     * <p>Useful for excluding specific users from results.
     * 
     * @param ids the collection of user IDs to exclude (null or empty returns always-true specification)
     * @return a specification matching users with IDs not in the collection
     */
    @NonNull
    public static Specification<User> hasIdNotIn(@Nullable Collection<Long> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) {
                return cb.conjunction();
            }
            return cb.not(root.get("id").in(ids));
        };
    }

    // ============================================================================
    // Composite Search Specifications
    // ============================================================================

    /**
     * Creates a specification that matches users by email or username.
     * 
     * <p>Useful for login scenarios where users can authenticate with either identifier.
     * 
     * @param emailOrUsername the email address or username to match (null returns always-true specification)
     * @return a specification matching users with the given email or username
     */
    @NonNull
    public static Specification<User> hasEmailOrUsername(@Nullable String emailOrUsername) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(emailOrUsername)) {
                return cb.conjunction();
            }
            Predicate emailMatch = cb.equal(root.get("email"), emailOrUsername);
            Predicate usernameMatch = cb.equal(root.get("username"), emailOrUsername);
            return cb.or(emailMatch, usernameMatch);
        };
    }

    /**
     * Creates a specification for general text search across multiple fields.
     * 
     * <p>Searches in email, username, first name, and last name fields.
     * Performs case-insensitive partial matching.
     * 
     * <p><strong>Performance:</strong> Multiple OR conditions may be slow on large datasets.
     * Consider full-text search for production.
     * 
     * @param searchTerm the text to search for (null or empty returns always-true specification)
     * @return a specification matching users containing the search term in any field
     */
    @NonNull
    public static Specification<User> searchInAllFields(@Nullable String searchTerm) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(searchTerm)) {
                return cb.conjunction();
            }
            
            String lowerPattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate emailMatch = cb.like(cb.lower(root.get("email")), lowerPattern);
            Predicate usernameMatch = cb.like(cb.lower(root.get("username")), lowerPattern);
            Predicate firstNameMatch = cb.like(cb.lower(root.get("firstName")), lowerPattern);
            Predicate lastNameMatch = cb.like(cb.lower(root.get("lastName")), lowerPattern);
            
            return cb.or(emailMatch, usernameMatch, firstNameMatch, lastNameMatch);
        };
    }

    // ============================================================================
    // Utility Specifications
    // ============================================================================

    /**
     * Creates a specification that always evaluates to true (matches all users).
     * 
     * <p>Useful as a base specification or for conditional query building.
     * 
     * @return a specification that always matches
     */
    @NonNull
    public static Specification<User> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }

    /**
     * Creates a specification that always evaluates to false (matches no users).
     * 
     * <p>Useful for conditional query building or testing.
     * 
     * @return a specification that never matches
     */
    @NonNull
    public static Specification<User> alwaysFalse() {
        return (root, query, cb) -> cb.disjunction();
    }
}