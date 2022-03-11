# GraphQL API Demo2

Working GraphQL API server application to demonstrate a technologie stack for rapid development:

* [Java](https://www.oracle.com/java/)
* [SPQR](https://github.com/leangen/graphql-spqr) Rapid development of GraphQL APIs in Java
* [Immutables](https://immutables.github.io/) Java annotation processors to generate DTOs
* [Java Faker](https://github.com/joostvanwollingen/java-faker) Random Data Generator
* [Bootique Jersey](https://bootique.io/) A minimally opinionated framework for runnable Java apps, Integrates JAX-RS server as a servlet
* [Gradle](https://gradle.org/) Build & dependency management

### Implemented Showcases / Concepts

* *GQLSchemaProvider.java* Code-first approach - automatic GraphQl schema generation (also modifications of runtime processing).
* *GQLEndpoint.java* GraphQL API endpoint using JAX-RS API.
* *GQLTypeMapper.java* Custom type mapper for GraphQL output and input types.
* *DTO Package* Using Imutables lib to minimize boilerplate and write robust data transfer objects (DTOs), fluent builder, optional and mandatory fields.
	* Note: The use of GraphQL and Immutable annotations together needs annotation injection...
* *UserRole* + *UserRoleBasedVisibility* Implementation of a annotation based GraphQL schema fields visibility / restriction.
	* Two different schemas for two different roles - no need for authorization checks in the resolvers.
	* All Resolver or DTO fields can be simply excluded from shema (using annotations).
	* Generated schemas are logged to console on start.



* TODO
	* Authentication & Authorization
	* API Versioning with GraphQL (https://blog.logrocket.com/versioning-fields-graphql/)
	* Custom Exception Handling according to GraphQL Spec
	* Unit tests (https://bootique.io/docs/2.x/bootique-docs/)
	* Built-in GraphQL Client?

### Run in IDE

Import as gradle project

Run ```de.example.api.Application``` as Java Application with Arguments ```--server --config=demo.yml```

### Run Demo as JAR

Build 'fat' JAR with Shadow Gradle Plugin:

```
$ GQL-API-Demo2>gradlew clean shadowJar
```

Run Java JAR:
```
$ GQL-API-Demo2>java -jar lib\build\libs\shadow-1.0-all.jar --server --config=demo.yml
```

Available commands:
```
$ GQL-API-Demo2>java -jar lib\build\libs\shadow-1.0-all.jar --help
```

GraphQL Server API available at:
```
http://localhost:9000/graphql
```
### Development (Eclipse)

Testet with
```CMD
------------------------------------------------------------
Gradle 7.4.1
------------------------------------------------------------

Build time:   2022-03-09 15:04:47 UTC
Revision:     36dc52588e09b4b72f2010bc07599e0ee0434e2e

Kotlin:       1.5.31
Groovy:       3.0.9
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          11.0.11 (Oracle Corporation 11.0.11+9-LTS-194)
OS:           Windows 10 10.0 amd64
```

In order to use immutables, the AnnotationProcessor needs to be configured in IDE propperly:

* Eclipse IDE
	* Enable Annotation processing 
	* Set Factory Path to ´´´M2_REPO/org/immutables/value/2.7.5/value-2.7.5.jar´´´

### Known Issues

Issue | Solution
------ | ----------
WARNING: A provider com.foo.MyResource registered in SERVER runtime does not implement any provider interfaces applicable in the SERVER runtime. Due to constraint configuration problems the provider com.foo.MyResource  will be ignored. | see https://github.com/bootique/bootique-jersey/issues/61 Fixed for 3.0M in io.bootique.jersey.JerseyModule
ERROR [2022-03-08 19:14:23,470] bootique-http-28 o.g.j.m.i.WriterInterceptorExecutor: MessageBodyWriter not found for media type=application/json, type=class java.util.LinkedHashMap, genericType=class java.util.LinkedHashMap. | add to compile path: 'org.glassfish.jersey.media:jersey-media-json-jackson:2.35',