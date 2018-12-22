package ru.artemaa.shaurma.integration;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<Order> {
    @Override
    protected AbstractIntegrationMessageBuilder<Order> doTransform(Message message) throws Exception {
        return null;
    }
}
