package ru.artemaa.shaurma.kitchen.messaging.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import ru.artemaa.shaurma.Order;
import ru.artemaa.shaurma.kitchen.messaging.OrderReceiver;

import javax.jms.JMSException;

public class JmsOrderReceiver implements OrderReceiver {
    private final JmsTemplate jmsTemplate;
    private final MessageConverter messageConverter;

    public JmsOrderReceiver(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public Order receiverOrder() throws JMSException {
        /*Message message = jmsTemplate.receive("shaurmacloud.order.queue");
        return (Order) messageConverter.fromMessage(message);*/
        return (Order) jmsTemplate.receiveAndConvert("shaurmacloud.order.queue");
    }
}
