package ru.artemaa.shaurma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ShaurmaCloudCassandraApplication {

    private static final String PROFILE_DEV = "dev";

    public static void main(String[] args) {
        SpringApplication.run(ShaurmaCloudCassandraApplication.class, args);
    }

}

