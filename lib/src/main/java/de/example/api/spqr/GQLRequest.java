package de.example.api.spqr;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GraphQL Request
 * 
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLRequest {

    private final String query;
    private final String operationName;
    private final Map<String, Object> variables;

    @JsonCreator
    public GQLRequest(@JsonProperty("query") String query, @JsonProperty("operationName") String operationName,
                      @JsonProperty("variables") Map<String, Object> variables) {
        this.query = query;
        this.operationName = operationName;
        this.variables = variables != null ? variables : Collections.emptyMap();
    }

    public String getQuery() {
        return query;
    }

    public String getOperationName() {
        return operationName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}
