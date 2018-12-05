package ru.artemaa.shaurma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.artemaa.shaurma.data.jpa.UserRepository;

@SpringBootApplication
@EnableConfigurationProperties
public class ShaurmaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaurmaCloudApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner dataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(User.builder()
                    .username("user1")
                    .password(passwordEncoder.encode("user1"))
                    .fullname("user name 1")
                    .city("Moscow")
                    .build()
            );
            userRepository.save(User.builder()
                    .username("user2")
                    .password(passwordEncoder.encode("user2"))
                    .fullname("user name 2")
                    .city("Toronto")
                    .build()
            );
        };
    }

}
