package de.example.api;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.internal.guava.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.example.api.spqr.GQLExecutor;
import de.example.api.spqr.GQLRequest;
import graphql.ExecutionResult;
import io.leangen.graphql.util.Utils;

/**
 * GraphQL endpoint using JAX-RS API
 *
 * @author Thomas Fritsche
 * @since 04.03.2022
 * @see "https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services"
 */
@Path("/graphql")
public class GQLEndpoint { // extends ResourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GQLEndpoint.class);
    private static final GQLExecutor<HttpServletRequest> EXECUTOR = new GQLExecutor<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Context
    private ServletContext context;
    @Context
    private HttpServletRequest request;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Wrong media type, use " + MediaType.APPLICATION_JSON;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "{\"message\":\"GraphQL API Demo\"}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    public Response post() {

        Stopwatch stopwatch = Stopwatch.createUnstarted().start();

        // TODO use Jackson JAX-RS to automatically deserialize the incoming GQLRequest?
        byte[] bytes = new byte[request.getContentLength()];
        GQLRequest graphQLRequest = null;
        try {
            request.getInputStream().read(bytes);
            String body = new String(bytes); // TODO specify charset
            graphQLRequest = MAPPER.readValue(body, GQLRequest.class);
            if (Utils.isEmpty(graphQLRequest.getQuery())) {
                getResponse(HttpStatus.BAD_REQUEST_400, "Missing content.");
            }

        }
        catch (IOException e) {
            // throw new ServiceException(Domain.UNKNOWN, ErrorCode.INVALID_REQUEST, e.getMessage());
            LOG.error(e.getMessage());
        }

        // GraphQL execution (validation)
        final ExecutionResult executionResult = EXECUTOR.execute(graphQLRequest, request);
        executionResult.getErrors().forEach(error -> LOG.error(error.toString()));
        // get result (to meet the GraphQL specification)
        final Map<String, Object> result = executionResult.toSpecification();

        LOG.info("GraphQL execution "
                 + (graphQLRequest != null && graphQLRequest.getOperationName() != null ? "[operation: " + graphQLRequest.getOperationName() + "]"
                                                                                        : "")
                 + " took [" + stopwatch + "]");
        return getResponse(HttpStatus.OK_200, result);
    }

    /**
     * Get Response with CORS (Cross-Origin Resource Sharing) header.
     * 
     * @param status HTTP status
     * @param entity request entity
     * @return default {@link Response} with CORS support.
     */
    private Response getResponse(int status, Object entity) {
        return Response.status(status)
                       // Access-Control-Allow-* header with * means any URL endpoints to this server instance can be accessed via any domain; if we
                       // want to restrict the cross-domain access
                       // explicitly, we have to mention that domain in this header
                       .header("Access-Control-Allow-Origin", "*")
                       .header("Access-Control-Allow-Credentials", "true")
                       .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                       .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                       .entity(entity)
                       .build();
    }

    @PostConstruct
    private void init() {
    }
}
