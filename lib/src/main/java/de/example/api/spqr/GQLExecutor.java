package de.example.api.spqr;

import de.example.api.dto.UserDTO.UserRole;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * GraphQL execution controller
 *
 * @author Thomas Fritsche
 * @since 05.03.2022
 */
public class GQLExecutor<R> {

    public ExecutionInput buildInput(GQLRequest graphQLRequest, R request, Object executionContext) {
        // GraphQL parsing -> validation -> execution
        ExecutionInput.Builder inputBuilder = ExecutionInput.newExecutionInput()
                                                            .query(graphQLRequest.getQuery())
                                                            .operationName(graphQLRequest.getOperationName())
                                                            .variables(graphQLRequest.getVariables())
                                                            .context(executionContext);
        return inputBuilder.build();
    }

    public ExecutionResult execute(GQLRequest graphQLRequest, R request) {
        GraphQL graphQL = GQLSchemaProvider.get().getSchema(UserRole.ADMIN);
        return graphQL.execute(buildInput(graphQLRequest, request, new GQLExecutionContext()));
    }
}
