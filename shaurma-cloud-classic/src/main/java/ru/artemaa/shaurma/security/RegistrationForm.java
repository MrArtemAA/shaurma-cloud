package ru.artemaa.shaurma.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.artemaa.shaurma.entity.jpa.User;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullname(fullname)
                .street(street)
                .city(city)
                .phoneNumber(phone)
                .build();
    }
}
