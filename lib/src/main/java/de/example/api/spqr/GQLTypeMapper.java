package de.example.api.spqr;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import io.leangen.graphql.generator.mapping.TypeMapper;
import io.leangen.graphql.generator.mapping.TypeMappingEnvironment;

/**
 * Custom type mapper for GraphQL output and input types
 *
 * @author Thomas Fritsche
 * @since 02.03.2022
 */
public class GQLTypeMapper implements TypeMapper {

    /** java type to scalar type - mapping register */
    private static final Map<Type, GraphQLScalarType> SCALAR_TYPE_MAPPING = new HashMap<>();

    static {
        final GraphQLScalarType ISO8601Date = GraphQLScalarType.newScalar()
                                                               .name("ISO8601Date")
                                                               .description("Scalar ISO-8601 date time (internally java.sql.Timestamp)")
                                                               .coercing(new Coercing<Timestamp, String>() {

                                                                   @Override
                                                                   public String serialize(Object pObject) throws CoercingSerializeException {
                                                                       if (!(pObject instanceof Timestamp)) {
                                                                           throw new CoercingSerializeException("Expected java type 'Timestamp' but was '"
                                                                                                                + typeName(pObject) + "'.");
                                                                       }
                                                                       String iso8601String = ((Timestamp) pObject).toInstant().toString();
                                                                       return iso8601String;
                                                                   }

                                                                   @Override
                                                                   public Timestamp parseValue(Object pInput) throws CoercingParseValueException {
                                                                       if (!(pInput instanceof String)) {
                                                                           throw new CoercingParseValueException("Expected AST type 'String' but was '"
                                                                                                                 + typeName(pInput) + "'.");
                                                                       }
                                                                       try {
                                                                           return Timestamp.valueOf((String) pInput);
                                                                       }
                                                                       catch (IllegalArgumentException e) {
                                                                           // Note : You should not allow java.lang.RuntimeExceptions to come out of
                                                                           // your parseValue method
                                                                           throw new CoercingParseValueException(e);
                                                                       }
                                                                   }

                                                                   @Override
                                                                   public Timestamp parseLiteral(Object pInput) throws CoercingParseLiteralException {
                                                                       if (!(pInput instanceof String)) {
                                                                           throw new CoercingParseLiteralException("Expected AST type 'String' but was '"
                                                                                                                   + typeName(pInput) + "'.");
                                                                       }
                                                                       return this.parseValue(pInput);
                                                                   }

                                                                   /**
                                                                    * @return "null" as string or simple class name
                                                                    */
                                                                   private String typeName(Object input) {
                                                                       return (input == null) ? "null" : input.getClass().getSimpleName();
                                                                   }
                                                               })
                                                               .build();

        SCALAR_TYPE_MAPPING.put(Timestamp.class, ISO8601Date);
    }

    public static boolean hasMapping(Type javaType) {
        return SCALAR_TYPE_MAPPING.containsKey(javaType);
    }

    public static GraphQLScalarType toGraphQLScalarType(Type javaType) {
        return SCALAR_TYPE_MAPPING.get(javaType);
    }

    @Override
    public GraphQLOutputType toGraphQLType(AnnotatedType javaType, Set<Class<? extends TypeMapper>> mappersToSkip, TypeMappingEnvironment env) {
        return toGraphQLScalarType(javaType.getType());
    }

    @Override
    public GraphQLInputType toGraphQLInputType(AnnotatedType javaType, Set<Class<? extends TypeMapper>> mappersToSkip, TypeMappingEnvironment env) {
        return toGraphQLScalarType(javaType.getType());
    }

    @Override
    public boolean supports(AnnotatedElement element, AnnotatedType type) {
        return hasMapping(type.getType());
    }

}
