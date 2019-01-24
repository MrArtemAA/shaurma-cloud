package ru.artemaa.shaurma.kitchen.messaging.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.kitchen.KitchenUI;

@Slf4j
@Component
public class OrderReceiver {
    private final KitchenUI kitchenUI;

    public OrderReceiver(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @KafkaListener(topics = "shaurmacloud.order.topic")
    public void receiverOrder(Order order) {
        kitchenUI.displayOrder(order);
    }

    @KafkaListener(topics = "shaurmacloud.order.topic")
    public void receiverOrder(Order order, ConsumerRecord<String, Order> record) {
        log.info("Partition {}, timestamp {}", record.partition(), record.timestamp());
        kitchenUI.displayOrder(order);
    }

    @KafkaListener(topics = "shaurmacloud.order.topic")
    public void receiverOrder(Order order, Message<Order> message) {
        MessageHeaders headers = message.getHeaders();
        log.info("Partition {}, timestamp {}",
                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP)
        );
        kitchenUI.displayOrder(order);
    }

}
