package de.example.api.dto;

import org.immutables.value.Value;

import de.example.api.spqr.annotation.APIDTO;
import de.example.api.spqr.annotation.InjectGraphQLQuery;

/**
 * @author tf
 * @since 21.03.2022
 */
@APIDTO
@Value.Immutable
// @JsonDeserialize(as = PersonalData.class)
public interface PersonalDataDTO {

    @InjectGraphQLQuery
    public Address getAddress();

    @InjectGraphQLQuery
    public String getCreditCardNumber();

    @InjectGraphQLQuery
    public String getCreditCardType();

    @InjectGraphQLQuery
    public String getCreditCardExpiry();

}
