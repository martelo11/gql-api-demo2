package de.example.api.spqr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.example.api.dto.UserDTO.UserRole;
import io.leangen.graphql.annotations.types.GraphQLDirective;

/**
 * Annotation for authorisation of a specific {@link UserRole} of queries / parameters / fields.
 * <p>
 * It is used as a directive to the GraphQL schema creation process.
 * <p>
 * For the successful use inside a immutable DTO class, use {@link InjectAPIAuth} instead!
 *
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
@GraphQLDirective(name = "auth")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD }) // Applicable to methods, parameters, fields
public @interface APIAuth {

    UserRole requiredUserRole();
}