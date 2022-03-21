
package de.example.api.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Nonnull;

import com.github.javafaker.Faker;

import de.example.api.dto.AddressBuilder;
import de.example.api.dto.PersonalData;
import de.example.api.dto.User;
import de.example.api.dto.UserDTO.UserRole;
import de.example.api.spqr.GQLExecutionContext;
import de.example.api.spqr.annotation.APIAuth;
import graphql.execution.DataFetcherResult;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;

/**
 * Service provider for GraphQL (root) resolvers (queries, mutations, subscriptions).
 *
 * @author Thomas Fritsche
 * @since 04.03.2022
 */
public class UserService {

    /** Fake data provider */
    final static Faker data = new Faker();

    /** Provide a ROOT Resolver */
    @GraphQLQuery(name = "getServertime")
    public Timestamp resolveServertime() {
        return new Timestamp(new Date().getTime());
    }

    /** Provide a ROOT resolver */
    @GraphQLQuery(name = "getUser")
    public DataFetcherResult<User> resolveUser(@GraphQLRootContext final GQLExecutionContext context) {
        User user1 = User.of(data.name().fullName(), "TODO", new Timestamp(data.date().birthday().getTime()), data.chuckNorris().fact(),
                             PersonalData.of(new AddressBuilder().data(data.address()).build(),
                                             data.business().creditCardNumber(),
                                             data.business().creditCardType(),
                                             data.business().creditCardExpiry()));

        return DataFetcherResult.<User> newResult()
                                .data(user1)
                                // .errors(toGraphQLErrors(result.allMessages()))
                                .build();
    }

    /** Provide a resolver for {@link User}.greeting */
    @APIAuth(requiredUserRole = UserRole.ADMIN)
    @GraphQLQuery(name = "greeting")
    public String resolveGreeting(@Nonnull @GraphQLContext User user, @GraphQLRootContext final GQLExecutionContext context) {
        // Here we may call another (external) service API ...
        return "Welcome " + user.getName() + "!";
    }
}