package ru.artemaa.shaurma.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "shuarma.email")
@Component
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000;

    public String getImapUrl() {
        return String.format("imaps://%s:%s@%s/%s",
                username,
                password,
                host,
                mailbox);
    }
}
