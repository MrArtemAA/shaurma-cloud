package ru.artemaa.shaurma.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.Order;

@Component
public class RabbitOrderReceiver {
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    public Order receiveOrder() {
        /*Message message = rabbitTemplate.receive("shaurmacloud.order.queue");
        return isNull(message) ? null : (Order) messageConverter.fromMessage(message);*/

        //return (Order) rabbitTemplate.receiveAndConvert();

        return rabbitTemplate.receiveAndConvert(new ParameterizedTypeReference<Order>() {});
    }
}
