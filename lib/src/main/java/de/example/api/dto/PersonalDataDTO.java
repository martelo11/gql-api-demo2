package de.example.api.dto;

import org.immutables.value.Value;

import com.github.javafaker.Crypto;

import de.example.api.spqr.GQLSchemaProvider.CryptoTypeAdapter;
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

    /**
     * There is a registered type adapter for Crypto, so it will be converted to an Integer and exposed as Integer. If {@link PersonalData} will also
     * be used as an input type in the GrapqhQL schema, the type adapter is also called for the conversion.
     * 
     * @see CryptoTypeAdapter
     */
    @InjectGraphQLQuery
    public Crypto getCrypto();
}
