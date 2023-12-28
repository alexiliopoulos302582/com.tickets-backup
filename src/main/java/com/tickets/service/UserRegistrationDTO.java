package com.tickets.service;

import com.tickets.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Getter
@Setter
public class UserRegistrationDTO {
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String phonenumber;
    private String email;
    private String enabled;
    private String newPassword;
    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String username,
                               String password,
                               String fullname,
                               String phonenumber,
                               String email,
                               String enabled) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.enabled = enabled;
    }

    public UserRegistrationDTO(Optional<User> user) {
    }

    public UserRegistrationDTO(UserDetails userDetails) {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
