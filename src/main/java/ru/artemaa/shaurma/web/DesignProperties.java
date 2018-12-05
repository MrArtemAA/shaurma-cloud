package ru.artemaa.shaurma.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shaurma.design")
@Data
public class DesignProperties {
    private int recentSize = 5;
}
