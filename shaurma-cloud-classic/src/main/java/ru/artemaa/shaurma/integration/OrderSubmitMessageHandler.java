package ru.artemaa.shaurma.integration;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<Order> {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    public OrderSubmitMessageHandler(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @Override
    public Object handle(Order order, MessageHeaders messageHeaders) {
        restTemplate.postForObject(apiProperties.getUrl(), order, String.class);
        return null;
    }
}
