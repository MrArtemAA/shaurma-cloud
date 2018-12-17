package ru.artemaa.shaurma.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.artemaa.shaurma.Order;

@Service
public class RabbitOrderMessagingService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order) {
        /*MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = messageConverter.toMessage(order, messageProperties);
        rabbitTemplate.send("shaurmacloud.order", message);*/
        rabbitTemplate.convertAndSend(order, this::postProcess);
    }

    private Message postProcess(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
