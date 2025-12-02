package com.example.practice.usercrud.mapper;

import com.example.practice.usercrud.data.dto.request.UserRequest;
import com.example.practice.usercrud.data.dto.request.UserUpdateRequest;
import com.example.practice.usercrud.data.dto.response.UserResponse;
import com.example.practice.usercrud.data.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * MapStruct mapper interface for converting between User entities and DTOs.
 * 
 * <p>This mapper provides compile-time code generation for efficient, type-safe
 * conversions between the User entity and its corresponding DTOs (Request/Response).
 * 
 * <p><strong>Key Features:</strong>
 * <ul>
 *   <li>Compile-time code generation (no runtime reflection overhead)</li>
 *   <li>Type-safe mapping with compile-time error checking</li>
 *   <li>Automatic field mapping for matching field names</li>
 *   <li>Custom mapping strategies for special cases</li>
 *   <li>Null-safe operations with configurable null handling</li>
 * </ul>
 * 
 * <p><strong>Mapping Strategies:</strong>
 * <ul>
 *   <li>UserRequest → User: Maps all fields, ignores system-managed fields (id, timestamps, version)</li>
 *   <li>UserUpdateRequest → User: Partial update mapping, null values are ignored</li>
 *   <li>User → UserResponse: Maps all fields except password and version</li>
 * </ul>
 * 
 * <p><strong>Security Considerations:</strong>
 * <ul>
 *   <li>Password is mapped from DTO to Entity but must be hashed in service layer before persistence</li>
 *   <li>Password is never mapped from Entity to Response (excluded by design)</li>
 *   <li>Version field is excluded from all DTOs (internal optimistic locking mechanism)</li>
 * </ul>
 * 
 * <p><strong>Performance:</strong>
 * <ul>
 *   <li>MapStruct generates plain Java code at compile time</li>
 *   <li>No reflection or runtime overhead</li>
 *   <li>Efficient bulk operations for collections</li>
 * </ul>
 * 
 * @author [Your Name]
 * @since 1.0.0
 * @see org.mapstruct.Mapper
 */
@Mapper(
    componentModel = "spring", // Generates Spring @Component for dependency injection
    unmappedTargetPolicy = ReportingPolicy.IGNORE, // Ignore unmapped fields (like password in response)
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // Ignore null values in updates
)
public interface UserMapper {

    // ============================================================================
    // Request → Entity Mappings (Create/Update)
    // ============================================================================

    /**
     * Maps a UserRequest DTO to a User entity for creation.
     * 
     * <p>This method is used when creating a new user. It maps all fields from
     * the request DTO to the entity, excluding system-managed fields (id, timestamps, version).
     * 
     * <p><strong>Note:</strong> The password field is mapped as-is and must be hashed
     * in the service layer before persistence.
     * 
     * <p><strong>Inherited Fields:</strong> Fields from BaseEntity (id, createdAt, updatedAt, version)
     * are automatically handled by JPA/Hibernate and don't need explicit mapping.
     * 
     * @param userRequest the user creation request DTO (must not be null)
     * @return a new User entity with fields populated from the request
     * @throws IllegalArgumentException if userRequest is null
     */
    @Mapping(target = "lastLoginAt", ignore = true) // Set on login, not creation
    @Mapping(target = "isActive", ignore = true) // Default to true in entity
    User toEntity(UserRequest userRequest);

    /**
     * Maps a UserUpdateRequest DTO to an existing User entity for partial updates.
     * 
     * <p>This method is used for update operations where only specific fields need to be updated.
     * Null values in the request are ignored (not overwriting existing values).
     * 
     * <p><strong>Update Behavior:</strong>
     * <ul>
     *   <li>Only non-null fields from the request are mapped to the entity</li>
     *   <li>System-managed fields (id, timestamps, version) are never updated</li>
     *   <li>Password update is optional (only if provided in request)</li>
     * </ul>
     * 
     * <p><strong>Note:</strong> The password field, if provided, must be hashed
     * in the service layer before persistence.
     * 
     * <p><strong>Inherited Fields:</strong> Fields from BaseEntity are automatically
     * excluded from updates by JPA/Hibernate.
     * 
     * @param userUpdateRequest the user update request DTO (must not be null)
     * @param user the existing User entity to update (must not be null)
     * @throws IllegalArgumentException if any parameter is null
     */
    @Mapping(target = "lastLoginAt", ignore = true) // Updated separately on login
    @Mapping(target = "isActive", ignore = true) // Updated via separate endpoint
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    // ============================================================================
    // Entity → Response Mappings
    // ============================================================================

    /**
     * Maps a User entity to a UserResponse DTO.
     * 
     * <p>This method is used when returning user data to API consumers.
     * It maps all fields from the entity to the response DTO, excluding
     * sensitive fields (password, version).
     * 
     * <p><strong>Security:</strong>
     * <ul>
     *   <li>Password is automatically excluded (not present in UserResponse)</li>
     *   <li>Version field is excluded (internal mechanism, not for API consumers)</li>
     *   <li>All other fields are mapped including timestamps for audit purposes</li>
     * </ul>
     * 
     * @param user the User entity to map (must not be null)
     * @return a UserResponse DTO with user data (excluding sensitive fields)
     * @throws IllegalArgumentException if user is null
     */
   
    UserResponse toResponse(User user);

    /**
     * Maps a list of User entities to a list of UserResponse DTOs.
     * 
     * <p>This method is used for bulk operations like listing all users.
     * Each entity in the list is mapped to a response DTO using the same
     * mapping rules as {@link #toResponse(User)}.
     * 
     * <p><strong>Performance:</strong> MapStruct generates efficient iteration code
     * with no reflection overhead.
     * 
     * @param users the list of User entities to map (must not be null)
     * @return a list of UserResponse DTOs (never null, may be empty)
     * @throws IllegalArgumentException if users is null
     */
    List<UserResponse> toResponseList(List<User> users);

    // ============================================================================
    // Utility Mappings
    // ============================================================================

    /**
     * Maps a UserUpdateRequest to a User entity (for cases where you need a new entity).
     * 
     * <p>This method is similar to {@link #toEntity(UserRequest)} but for update requests.
     * Use {@link #updateEntityFromDto(UserUpdateRequest, User)} for partial updates instead.
     * 
     * <p><strong>Note:</strong> This method creates a new entity. For updates, prefer
     * {@link #updateEntityFromDto(UserUpdateRequest, User)} which updates an existing entity.
     * 
     * @param userUpdateRequest the user update request DTO (must not be null)
     * @return a new User entity with fields populated from the update request
     * @throws IllegalArgumentException if userUpdateRequest is null
     */
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    User toEntityFromUpdateRequest(UserUpdateRequest userUpdateRequest);
}