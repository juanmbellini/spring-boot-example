package com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.entities;

import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.webapi.support.deserializers.Java8LocalDateClassicFormatDeserializer;
import com.bellotapps.examples.spring_boot_example.webapi.support.serializers.Java8LocalDateClassicFormatSerializer;
import com.bellotapps.examples.spring_boot_example.webapi.support.serializers.URISerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URI;
import java.time.LocalDate;

/**
 * Data transfer object for {@link User} class.
 */
public class UserDto {

    @JsonProperty
    private long id;

    @JsonProperty
    private String fullName;

    @JsonProperty
    @JsonSerialize(using = Java8LocalDateClassicFormatSerializer.class)
    @JsonDeserialize(using = Java8LocalDateClassicFormatDeserializer.class)
    private LocalDate birthDate;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @SuppressWarnings("unused")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = URISerializer.class)
    private URI locationUrl;

    public UserDto() {
        // For Jersey
    }

    /**
     * Constructor.
     *
     * @param user        The {@link User} from which the dto will be built.
     * @param locationUrl The location url (in {@link URI} format) of the given {@link User}.
     */
    public UserDto(User user, URI locationUrl) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.birthDate = user.getBirthDate();
        this.username = user.getUsername();
        this.email = user.getEmail();

        this.locationUrl = locationUrl;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public URI getLocationUrl() {
        return locationUrl;
    }
}
