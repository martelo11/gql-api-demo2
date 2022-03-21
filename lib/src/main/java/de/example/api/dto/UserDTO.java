package de.example.api.dto;

import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import de.example.api.spqr.GQLSchemaProvider.UserRoleBasedVisibility;
import de.example.api.spqr.annotation.APIDTO;
import de.example.api.spqr.annotation.InjectAPIAuth;
import de.example.api.spqr.annotation.InjectGraphQLQuery;

/**
 * User DTO.
 * 
 * @author Thomas Fritsche
 * @since 09.03.2022
 */
@APIDTO
@Value.Immutable
// @JsonDeserialize(as = User.class)
public interface UserDTO {

    @InjectGraphQLQuery
    public String getName();

    @InjectGraphQLQuery
    public String getPassword();

    @InjectGraphQLQuery
    public Timestamp getBirthday();

    @InjectAPIAuth(requiredUserRole = UserRole.ADMIN)
    @InjectGraphQLQuery(description = "Description for GQL schema")
    public @Nullable String getText();

    @InjectGraphQLQuery(name = "personal")
    public PersonalDataDTO getPersonalData();

    /**
     * Example for role based GraphQL schema generation
     * 
     * @see UserRoleBasedVisibility
     */
    public enum UserRole {
        ADMIN,
        USER;
    }

    /**
     * Creates an extra Builder, that may be used to build the DTO from another object/dataloader.
     */
    // @Factory
    // public static User user(final @Nonnull Object data) {
    // return User.of("TODO", "TODO", null, null, null);
    // }

}
