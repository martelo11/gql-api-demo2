package de.example.api.dto;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * {@code UserBuilder} collects parameters and invokes the static factory method:
 * {@code de.example.api.dto.UserDTO.user(..)}.
 * Call the {@link #build()} method to get a result of type {@code de.example.api.dto.User}.
 * <p><em>{@code UserBuilder} is not thread-safe and generally should not be stored in a field or collection,
 * but instead used immediately to create instances.</em>
 */
@Generated(from = "UserDTO.user", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@NotThreadSafe
public final class UserBuilder {
  private static final long INIT_BIT_DATA = 0x1L;
  private long initBits = 0x1L;

  private @Nullable Object data;

  /**
   * Creates a {@code UserBuilder} factory builder.
   * <pre>
   * new UserBuilder()
   *    .data(Object) // required {@code data}
   *    .build();
   * </pre>
   */
  public UserBuilder() {
  }

  /**
   * Initializes the value for the {@code data} attribute.
   * @param data The value for data 
   * @return {@code this} builder for use in a chained invocation
   */
  @CanIgnoreReturnValue 
  public final UserBuilder data(Object data) {
    this.data = Objects.requireNonNull(data, "data");
    initBits &= ~INIT_BIT_DATA;
    return this;
  }

  /**
   * Invokes {@code de.example.api.dto.UserDTO.user(..)} using the collected parameters and returns the result of the invocation
   * @return A result of type {@code de.example.api.dto.User}
   * @throws java.lang.IllegalStateException if any required attributes are missing
   */
  public User build() {
    checkRequiredAttributes();
    return UserDTO.user(data);
  }

  private boolean dataIsSet() {
    return (initBits & INIT_BIT_DATA) == 0;
  }

  private void checkRequiredAttributes() {
    if (initBits != 0) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!dataIsSet()) attributes.add("data");
    return "Cannot build user, some of required attributes are not set " + attributes;
  }
}
