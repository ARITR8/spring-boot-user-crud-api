package com.example.practice.usercrud.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Base entity class providing common fields for all JPA entities.
 * 
 * <p>This abstract class encapsulates common persistence concerns:
 * <ul>
 *   <li>Primary key generation using database sequences</li>
 *   <li>Automatic timestamp tracking for creation and modification</li>
 *   <li>Optimistic locking via version field to prevent concurrent modification conflicts</li>
 * </ul>
 * 
 * <p>All entities should extend this class to inherit these capabilities.
 * 
 * @author System Generated
 * @since 1.0.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

    /**
     * Primary key identifier.
     * 
     * <p>Generated automatically using database sequence strategy.
     * This ensures efficient, database-agnostic ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * Timestamp when the entity was first persisted.
     * 
     * <p>Automatically set by JPA Auditing on first save.
     * Immutable after creation to maintain audit trail integrity.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp when the entity was last modified.
     * 
     * <p>Automatically updated by JPA Auditing on every save operation.
     * Tracks the most recent modification time for audit purposes.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Optimistic locking version field.
     * 
     * <p>Automatically incremented by JPA on each update.
     * Prevents lost updates in concurrent scenarios by detecting
     * stale entity states and throwing OptimisticLockException.
     * 
     * <p>Initial value is 0, incremented on each update.
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;
}