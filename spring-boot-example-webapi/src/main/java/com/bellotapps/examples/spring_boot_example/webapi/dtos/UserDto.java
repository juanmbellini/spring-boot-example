package com.bellotapps.examples.spring_boot_example.webapi.dto;

import com.bellotapps.examples.spring_boot_example.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Juan Marcos Bellini on 15/8/17.
 * Questions at jbellini@bellotsapps.com
 */
public class UserDto {

    @JsonProperty
    private long id;

    @JsonProperty
    private String fullName;

    private String birthDate;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private Dummy dummyEnum = Dummy.DUMMY1;

    @SuppressWarnings("unused")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDto() {
        // For Jax-RS
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.birthDate = user.getBirthDate().toString();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
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

    enum Dummy {
        DUMMY1 {
            @Override
            public String toString() {
                return "Foorro";
            }
        },
        DUMMY2 {
            @Override
            public String toString() {
                return "Foorro la concha de tu madre";
            }
        };
    }

}
