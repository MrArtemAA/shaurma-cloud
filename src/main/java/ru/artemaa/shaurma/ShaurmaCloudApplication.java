package ru.artemaa.shaurma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;
import ru.artemaa.shaurma.data.jpa.UserRepository;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableConfigurationProperties
public class ShaurmaCloudApplication {

    private static final String PROFILE_DEV = "dev";

    public static void main(String[] args) {
        SpringApplication.run(ShaurmaCloudApplication.class, args);
    }

    @Bean
    @Profile(PROFILE_DEV)
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ShaurmaRepository shaurmaRepository,
            IngredientRepository ingredientRepository
    ) {
        return args -> {
            loadUsers(userRepository, passwordEncoder);
            loadShaurmas(shaurmaRepository, ingredientRepository);
        };
    }

    private void loadUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
    }

    private void loadShaurmas(ShaurmaRepository shaurmaRepository, IngredientRepository ingredientRepository) {
        shaurmaRepository.save(Shaurma.builder()
                .name("Shaurma 1")
                .ingredients(asList(
                        ingredientRepository.findById("RGPT").get(),
                        ingredientRepository.findById("CHKN").get()
                ))
                .build()
        );
        shaurmaRepository.save(Shaurma.builder()
                .name("Shaurma 2")
                .ingredients(asList(
                        ingredientRepository.findById("CHPT").get(),
                        ingredientRepository.findById("PORK").get(),
                        ingredientRepository.findById("CHED").get()
                ))
                .build()
        );
    }

}
