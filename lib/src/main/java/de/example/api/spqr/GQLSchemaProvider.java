package de.example.api.spqr;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import de.example.api.dto.UserDTO.UserRole;
import de.example.api.service.UserService;
import de.example.api.spqr.annotation.APIAuth;
import graphql.DirectivesUtil;
import graphql.GraphQL;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import graphql.schema.visibility.GraphqlFieldVisibility;
import io.leangen.graphql.GraphQLRuntime;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

/**
 * Singleton: {@link GraphQL} schema generator and provider.
 *
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
public class GQLSchemaProvider {

    private static final Logger LOG = LoggerFactory.getLogger(GQLSchemaProvider.class);

    /** Singleton instance of this class */
    private static GQLSchemaProvider SHARED_INSTANCE = null;
    /** Map with {@link GraphQL} schema for {@link UserRole} */
    private static HashMap<UserRole, GraphQL> SCHEMA_MAPPING = Maps.newHashMap();

    /**
     * @return The singleton instance of this class
     */
    public static synchronized GQLSchemaProvider get() {
        if (SHARED_INSTANCE == null) {
            SHARED_INSTANCE = new GQLSchemaProvider();
        }
        return SHARED_INSTANCE;
    }

    /**
     * Generate {@link GraphQL} schemas.
     */
    public void generate() {
        // suppress NULL values (for deserialization)
        // https://stackoverflow.com/questions/11449211/how-to-prevent-null-values-inside-a-map-and-null-fields-inside-a-bean-from-getti
        // Is it possible to distinguish between null fields and undefined fields ?
        // https://github.com/leangen/graphql-spqr/issues/197
        final ObjectMapper mapper = new ObjectMapper().setDefaultPropertyInclusion(Include.NON_NULL).setDefaultPropertyInclusion(Include.NON_EMPTY);
        final JacksonValueMapperFactory valueMapperFactory = JacksonValueMapperFactory.builder().withPrototype(mapper).build();

        // important: the sequence of method calls matters!
        final GraphQLSchema dtoSchema = new GraphQLSchemaGenerator()
                                                                    // not mandatory but strongly recommended to set your"root" packages
                                                                    .withBasePackages("de.example.api.dto")
                                                                    // register type mapper
                                                                    .withTypeMappers(new GQLTypeMapper())
                                                                    // Custom Jackson factory to exclude NULL values
                                                                    .withValueMapperFactory(valueMapperFactory)
                                                                    // register the services
                                                                    .withOperationsFromSingleton(new UserService())
                                                                    // A resolver builder that exposes only annotated methods
                                                                    .withResolverBuilders(new AnnotatedResolverBuilder().withJavaDeprecationRespected(false))
                                                                    .generate();
        init(dtoSchema, UserRole.ADMIN);
        init(dtoSchema, UserRole.USER);
    }

    /**
     * Initialise and customise the given schema with SPQR and register it locally into {@link #SCHEMA_MAPPING}.
     * 
     * @param schema schema to init
     * @param userRole user role for the schema
     */
    private void init(final GraphQLSchema schema, final UserRole userRole) {
        final GraphQLCodeRegistry codeRegistry = schema.getCodeRegistry()
                                                       .transform(schemaBuilder -> schemaBuilder.fieldVisibility(new UserRoleBasedVisibility(userRole)));
        final GraphQLSchema transformedSchema = schema.transform(schemaBuilder -> schemaBuilder.codeRegistry(codeRegistry));
        debugPrint(transformedSchema, userRole);
        // use GraphQLRuntime wrapper for additional instrumentation chaining support
        GraphQL graph = GraphQLRuntime.newGraphQL(transformedSchema).build();
        SCHEMA_MAPPING.put(userRole, graph);
    }

    /**
     * Get the {@link GraphQL} schema for {@link UserType}
     * 
     * @param userRole
     * @return {@link GraphQL} or <code>null</code>
     */
    public GraphQL getSchema(final UserRole userRole) {
        return SCHEMA_MAPPING.get(userRole);
    }

    /**
     * Log schema to console
     * 
     * @param schema schema to log
     * @param userRole
     */
    private void debugPrint(final GraphQLSchema schema, final UserRole userRole) {
        // TODO Use SPQR's SchemaPrinter
        String schemaStr = new SchemaPrinter(SchemaPrinter.Options.defaultOptions()
                                                                  .includeScalarTypes(true)
                                                                  .includeDirectives(false)
                                                                  .includeIntrospectionTypes(false)
                                                                  .includeSchemaDefinition(true)).print(schema);
        LOG.info("Generated GraphQL schema for [" + userRole + "]:\n" + schemaStr);
    }

    /**
     * Control GraphQL fields visibility (restriction) by {@link UserRole}.
     * <p>
     * Usage:
     * <p>
     * <li>Annotate any DTO field with <code>@APIAuth(requiredUserRole = UserRole.ADMIN)</code> to completely hide it from schema for other
     * {@link UserRole}s.</li>
     * <li>Default: any field is visible to any {@link UserRole}.
     */
    public static class UserRoleBasedVisibility implements GraphqlFieldVisibility {

        private final UserRole userRole;

        public UserRoleBasedVisibility(UserRole pUserRole) {
            this.userRole = pUserRole;
        }

        @Override
        public List<GraphQLFieldDefinition> getFieldDefinitions(GraphQLFieldsContainer fieldsContainer) {
            return fieldsContainer.getFieldDefinitions().stream().filter(field -> isFieldAllowed(field, userRole)).collect(Collectors.toList());
        }

        @Override
        public GraphQLFieldDefinition getFieldDefinition(GraphQLFieldsContainer fieldsContainer, String fieldName) {
            GraphQLFieldDefinition fieldDefinition = fieldsContainer.getFieldDefinition(fieldName);
            return fieldDefinition == null || !isFieldAllowed(fieldDefinition, this.userRole) ? null : fieldDefinition;
        }

        /**
         * Check if directive {@link APIAuth#requiredUserRole()} is defined on a field/method and if its a match.
         * 
         * @param field
         * @param pUserRole
         * @return default <code>true</code> if given field/method holds no directive. If there is a required user role defined, it must match with
         *         the current {@link UserRole}.
         */
        private boolean isFieldAllowed(GraphQLDirectiveContainer field, UserRole pUserRole) {
            // find annotated directive (just a single role for now)
            Optional<GraphQLArgument> requiredUserType = DirectivesUtil.directiveWithArg(field.getDirectives(), "auth", "requiredUserRole");
            if (requiredUserType.isPresent()) {
                // check if annotated role matches current user role
                return requiredUserType.get().getValue().equals(pUserRole);
            }
            return true;
        }
    }
}
