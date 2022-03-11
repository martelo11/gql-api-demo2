package de.example.api.dto;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.example.api.spqr.annotation.APIAuth;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * User DTO.
 * 
 * @author Thomas Fritsche
 * @since 09.03.2022
 */
@Generated(from = "UserDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class User implements UserDTO {
  private final String name;
  private final String password;
  private final Timestamp birthday;
  private final @Nullable String text;

  private User(
      String name,
      String password,
      Timestamp birthday,
      @Nullable String text) {
    this.name = Objects.requireNonNull(name, "name");
    this.password = Objects.requireNonNull(password, "password");
    this.birthday = Objects.requireNonNull(birthday, "birthday");
    this.text = text;
  }

  private User(User.Builder builder) {
    this.name = builder.name;
    this.password = builder.password;
    this.birthday = builder.birthday;
    this.text = builder.text;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @GraphQLQuery(deprecationReason = "", description = "", name = "")
  @Override
  public String getName() {
    return name;
  }

  /**
   * @return The value of the {@code password} attribute
   */
  @GraphQLQuery(deprecationReason = "", description = "", name = "")
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * @return The value of the {@code birthday} attribute
   */
  @GraphQLQuery(deprecationReason = "", description = "", name = "")
  @Override
  public Timestamp getBirthday() {
    return birthday;
  }

  /**
   * @return The value of the {@code text} attribute
   */
  @GraphQLQuery(deprecationReason = "", description = "Description for GQL schema", name = "")@APIAuth(requiredUserRole = UserDTO.UserRole.ADMIN)
  @Override
  public @Nullable String getText() {
    return text;
  }

  /**
   * This instance is equal to all instances of {@code User} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof User
        && equalTo((User) another);
  }

  private boolean equalTo(User another) {
    return name.equals(another.name)
        && password.equals(another.password)
        && birthday.equals(another.birthday)
        && Objects.equals(text, another.text);
  }

  /**
   * Computes a hash code from attributes: {@code name}, {@code password}, {@code birthday}, {@code text}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + name.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + birthday.hashCode();
    h += (h << 5) + Objects.hashCode(text);
    return h;
  }

  /**
   * Prints the immutable value {@code User} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("User")
        .omitNullValues()
        .add("name", name)
        .add("password", password)
        .add("birthday", birthday)
        .add("text", text)
        .toString();
  }

  /**
   * Construct a new immutable {@code User} instance.
   * @param name The value for the {@code name} attribute
   * @param password The value for the {@code password} attribute
   * @param birthday The value for the {@code birthday} attribute
   * @param text The value for the {@code text} attribute
   * @return An immutable User instance
   */
  public static User of(String name, String password, Timestamp birthday, @Nullable String text) {
    return new User(name, password, birthday, text);
  }

  /**
   * Creates a builder for {@link User User}.
   * <pre>
   * User.builder()
   *    .name(String) // required {@link UserDTO#getName() name}
   *    .password(String) // required {@link UserDTO#getPassword() password}
   *    .birthday(java.sql.Timestamp) // required {@link UserDTO#getBirthday() birthday}
   *    .text(String | null) // nullable {@link UserDTO#getText() text}
   *    .build();
   * </pre>
   * @return A new User builder
   */
  public static User.Builder builder() {
    return new User.Builder();
  }

  /**
   * Builds instances of type {@link User User}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "UserDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_NAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private static final long INIT_BIT_BIRTHDAY = 0x4L;
    private long initBits = 0x7L;

    private @Nullable String name;
    private @Nullable String password;
    private @Nullable Timestamp birthday;
    private @Nullable String text;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code UserDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(UserDTO instance) {
      Objects.requireNonNull(instance, "instance");
      name(instance.getName());
      password(instance.getPassword());
      birthday(instance.getBirthday());
      @Nullable String textValue = instance.getText();
      if (textValue != null) {
        text(textValue);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link UserDTO#getName() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link UserDTO#getPassword() password} attribute.
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder password(String password) {
      this.password = Objects.requireNonNull(password, "password");
      initBits &= ~INIT_BIT_PASSWORD;
      return this;
    }

    /**
     * Initializes the value for the {@link UserDTO#getBirthday() birthday} attribute.
     * @param birthday The value for birthday 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder birthday(Timestamp birthday) {
      this.birthday = Objects.requireNonNull(birthday, "birthday");
      initBits &= ~INIT_BIT_BIRTHDAY;
      return this;
    }

    /**
     * Initializes the value for the {@link UserDTO#getText() text} attribute.
     * @param text The value for text (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder text(@Nullable String text) {
      this.text = text;
      return this;
    }

    /**
     * Builds a new {@link User User}.
     * @return An immutable instance of User
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public User build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new User(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      if ((initBits & INIT_BIT_BIRTHDAY) != 0) attributes.add("birthday");
      return "Cannot build User, some of required attributes are not set " + attributes;
    }
  }
}
