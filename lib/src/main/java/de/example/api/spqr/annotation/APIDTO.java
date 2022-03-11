package de.example.api.spqr.annotation;

import org.immutables.value.Value;

import de.example.api.GQLEndpoint;

/**
 * The immutable value style definition for all DTO's that are included in the generated GraphQL schema and therefore exposed by the
 * {@link GQLEndpoint}.
 * <p>
 * You have to <b>RESTART ECLIPSE!</b> for changes to this file to take effect! Also rebuild the affected classes!
 * 
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
@Value.Style(typeAbstract = "*DTO", // 'POJO' suffix will be detected and trimmed
        typeImmutable = "*", // No prefix or suffix for generated immutable type
        typeModifiable = "*", // No prefix or suffix for generated mutable type
        depluralize = true, // enable depluralization for add method of collections: queries() -> addQuery()
        depluralizeDictionary = { "alias:aliases", "child:children" }, // add any unusual plural forms here (trailing "s" and "ies" -> "y" are handled
                                                                       // automatically)
        allParameters = true, // all attributes will become constructor parameters as if they are annotated with @Value.Parameter
        visibility = Value.Style.ImplementationVisibility.PUBLIC, // Generated class will be always public
        defaults = @Value.Immutable(builder = true, copy = false)) // Enable copy methods and builder
public @interface APIDTO {

}