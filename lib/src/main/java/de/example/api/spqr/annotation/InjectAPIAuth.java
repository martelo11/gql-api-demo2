package de.example.api.spqr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.immutables.annotate.InjectAnnotation;
import org.immutables.annotate.InjectAnnotation.Where;

import de.example.api.dto.UserDTO.UserRole;
import io.leangen.graphql.annotations.GraphQLIgnore;

/**
 * Inject {@link GraphQLIgnore} annotation to the generated (immutable class) method. Necessary for the integration with SPQR framework.
 *
 * @author Thomas Fritsche
 * @since 09.03.2022
 * @see "https://immutables.github.io/immutable.html#annotation-injection"
 * @see "https://github.com/immutables/immutables/issues/1260"
 */
@InjectAnnotation(type = APIAuth.class, target = Where.ACCESSOR, code = "([[*]])")
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface InjectAPIAuth {

    UserRole requiredUserRole();
}