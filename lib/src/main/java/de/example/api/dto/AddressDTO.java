package de.example.api.dto;

import javax.annotation.Nonnull;

import org.immutables.builder.Builder.Factory;
import org.immutables.value.Value;

import de.example.api.spqr.annotation.APIDTO;
import de.example.api.spqr.annotation.InjectGraphQLQuery;

/**
 * @author tf
 * @since 21.03.2022
 */
@APIDTO
@Value.Immutable
public interface AddressDTO {

    @InjectGraphQLQuery
    public String getFullName();

    @InjectGraphQLQuery
    public String getStreetName();

    @InjectGraphQLQuery
    public String getCityName();

    @InjectGraphQLQuery
    public String getCountry();

    /**
     * Creates an extra Builder, that may be used to build the DTO from another source.
     */
    @Factory
    public static Address address(final @Nonnull com.github.javafaker.Address data) {
        return Address.of(data.firstName() + " " + data.lastName(), data.streetAddress() + " " + data.streetAddressNumber(), data.cityName(),
                          data.country());
    }

}