package ru.artemaa.shaurma.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.artemaa.shaurma.entity.jpa.Order;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jmsTemplate;
    private final Destination orderQueue;

    public JmsOrderMessagingService(JmsTemplate jmsTemplate, Destination orderQueue) {
        this.jmsTemplate = jmsTemplate;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(Order order) {
        /*jmsTemplate.send(
                orderQueue,
                session -> session.createObjectMessage(order)
        );*/

        jmsTemplate.convertAndSend(
                orderQueue,
                order,
                this::addOrderSource
        );
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
