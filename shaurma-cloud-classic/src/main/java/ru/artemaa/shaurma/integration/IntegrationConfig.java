package ru.artemaa.shaurma.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class IntegrationConfig {
    @Bean
    public IntegrationFlow orderEmailFlow(
            EmailProperties emailProperties,
            EmailToOrderTransformer transformer,
            OrderSubmitMessageHandler messageHandler
    ) {
        return IntegrationFlows
                .from(
                        Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate()))
                )
                .transform(transformer)
                .handle(messageHandler)
                .get();

    }
}
