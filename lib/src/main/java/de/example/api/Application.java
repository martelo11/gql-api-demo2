
package de.example.api;

import de.example.api.spqr.GQLSchemaProvider;
import io.bootique.BaseModule;
import io.bootique.Bootique;
import io.bootique.di.Binder;
import io.bootique.jersey.JerseyModule;
import io.bootique.jetty.JettyModule;

/**
 * The main class that registers all components and starts the REST application
 * 
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
public class Application extends BaseModule {

    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .module(Application.class)
                // Explicit loading of jetty server module (needed to be recognised when running as shadow jar)
                .module(JettyModule.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {
        // register resources (endpoint)
        JerseyModule.extend(binder).addResource(GQLEndpoint.class).addResource(RESTEndpoint.class);
        // call GraphQL schema generation
        GQLSchemaProvider.get().generate();
    }
}