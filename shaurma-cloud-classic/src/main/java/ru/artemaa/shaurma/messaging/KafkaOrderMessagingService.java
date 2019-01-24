package ru.artemaa.shaurma.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.artemaa.shaurma.entity.jpa.Order;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        //kafkaTemplate.send("shaurmacloud.order.topic", order);
        kafkaTemplate.sendDefault(order);
    }
}
