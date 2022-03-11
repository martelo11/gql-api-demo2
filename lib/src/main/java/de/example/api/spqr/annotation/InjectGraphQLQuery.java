package de.example.api.spqr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.immutables.annotate.InjectAnnotation;
import org.immutables.annotate.InjectAnnotation.Where;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Inject {@link GraphQLQuery} annotation to the generated (immutable class) method. Necessary for the combination of immutables and graphQL (SPQR
 * framework).
 *
 * @author Thomas Fritsche
 * @since 09.03.2022
 * @see "https://github.com/immutables/immutables/blob/master/annotate/src/org/immutables/annotate/InjectAnnotation.java"
 */
@InjectAnnotation(type = GraphQLQuery.class, target = Where.ACCESSOR, code = "([[*]])")
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface InjectGraphQLQuery {

    String name() default "";

    String description() default "";

    String deprecationReason() default ""; // default ReservedStrings.NULL
}