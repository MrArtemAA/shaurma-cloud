package ru.artemaa.shaurma.messaging;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.artemaa.shaurma.Order;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {
    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("shaurmacloud.order.queue");
    }

    @Bean
    public MappingJackson2MessageConverter jmsMessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate();
    }

    @Bean
    public MessageConverter amqpMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    KafkaTemplate<String, Order> orderKafkaTemplate() {
        return new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(new HashMap<>())
        );
    }
}
