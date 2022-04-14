
package de.example.api;

import java.io.InputStream;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bootique.jetty.servlet.ServletEnvironment;

/**
 * REST endpoint using JAX-RS API
 *
 * @author Thomas Fritsche
 * @since 31.03.2022
 */
@Path("/")
public class RESTEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(RESTEndpoint.class);

    @Inject
    private ServletEnvironment servletEnv;

    /**
     * Static resource: GraphiQL client.
     * <p>
     * Note: This solution can possibly be replaced by a static resource servlet from jetty (bootique).
     * 
     * @see "https://bootique.io/docs/2.x/bootique-jetty-docs/#serving-static-files"
     * @see "https://stackoverflow.com/questions/8642920/how-to-serve-static-content-with-jax-rs"
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response staticResources() {
        // "classpath://WEB-INF/graphiql/index.html"
        // "classpath:bin/main/WEB-INF/graphiql/index.html"
        // "file:///D:/dev/work/workspace_java/secutrial-tools/GQL-API-Demo2/lib/bin/main/WEB-INF/graphiql/index.html"
        // InputStream resource = context.getResourceAsStream(String.format("/WEB-INF/graphiql/index.html"));
        String path = servletEnv.context().get().getRealPath("classpath://WEB-INF/graphiql/index.html");
        LOG.debug("# " + path);
        InputStream resource = servletEnv.context().get().getResourceAsStream("classpath:/WEB-INF/graphiql/index.html");
        LOG.debug("# " + resource);
        // String url = servletEnv.request().map(HttpServletRequest::getRequestURI).orElse("unknown");
        return Objects.isNull(resource) ? Response.status(HttpStatus.NOT_FOUND_404).build() : Response.ok().entity(resource).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{path: ^static\\/.*}")
    public Response staticResources(@PathParam("path") final String path) {
        // redirect path from baseURI to the api console
        // URI redirectedURL = UriBuilder.fromPath("/static/graphiql/index.html").build();
        // return Response.seeOther(redirectedURL).build();
        InputStream resource = servletEnv.context().get().getResourceAsStream(String.format("/WEB-INF/graphiql/%s", path));
        return Objects.isNull(resource) ? Response.status(HttpStatus.NOT_FOUND_404).build() : Response.ok().entity(resource).build();
    }
}
