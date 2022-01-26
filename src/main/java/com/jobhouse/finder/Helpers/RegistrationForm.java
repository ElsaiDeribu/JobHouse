package com.jobhouse.finder.Helpers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.jobhouse.finder.Tables.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {
    @NotNull
    @NotBlank(message = "Name is required")
    private String fullName;

    @NotNull
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull
    @Email(message = "proper email is required")
    @NotBlank(message = "Email is required")
    private String email;
    private String password;
    private boolean enabled;

    // public User toUser(PasswordEncoder encoder) {
    // User user = new User();
    // user.setUsername(this.username);
    // user.setPassword(encoder.encode(this.password));
    // user.setFullName(this.fullName);
    // user.setEmail(this.email);
    // user.setEnabled(enabled);
    // return user;
    // }
}
