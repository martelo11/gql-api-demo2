package de.example.api;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * CORS (Cross-Origin Resource Sharing) for JAX-RS
 * <p>
 * FIXME not working yet
 *
 * @author Thomas Fritsche
 * @since 08.03.2022
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Access-Control-Allow-* header with * means any URL endpoints to this server instance can be accessed via any domain; if we
        // want to restrict the cross-domain access
        // explicitly, we have to mention that domain in this header
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); // , OPTIONS, HEAD
    }
}
