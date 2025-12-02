package com.example.practice.usercrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA Auditing functionality.
 * 
 * <p>This configuration enables automatic timestamp tracking for entities
 * that use {@link org.springframework.data.annotation.CreatedDate} and
 * {@link org.springframework.data.annotation.LastModifiedDate} annotations.
 * 
 * <p>Without this configuration, the auditing annotations in {@link BaseEntity}
 * will not work, and {@code createdAt} and {@code updatedAt} fields will remain null.
 * 
 * <p><strong>Note:</strong> This must be a separate configuration class and cannot
 * be placed in the main application class if the application class is annotated
 * with {@code @SpringBootApplication(exclude = ...)} that might interfere with
 * auto-configuration.
 * 
 * @author System Generated
 * @since 1.0.0
 * @see org.springframework.data.jpa.repository.config.EnableJpaAuditing
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    // Configuration class - no additional code needed
    // The @EnableJpaAuditing annotation does all the work
}