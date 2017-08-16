package com.bellotapps.examples.spring_boot_example.webapi.dto;

import com.bellotapps.examples.spring_boot_example.models.User;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Juan Marcos Bellini on 15/8/17.
 * Questions at jbellini@bellotsapps.com
 */
@XmlAccessorType
@XmlRootElement(name = "")
public class UserDto {


    @XmlElement
    private long id;

    @XmlElement
    private String fullName;

    @XmlElement
    private String birthDate;

    @XmlElement
    private String username;

    @XmlElement
    private String email;

    @XmlElement
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
}
