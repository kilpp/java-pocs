package com.gk.pulsar_poc.service;

import com.gk.pulsar_poc.model.DemoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private static final String TOPIC = "persistent://public/default/demo-topic";

    private final PulsarTemplate<DemoMessage> pulsarTemplate;

    public MessageProducer(PulsarTemplate<DemoMessage> pulsarTemplate) {
        this.pulsarTemplate = pulsarTemplate;
    }

    public void sendMessage(DemoMessage message) {
        logger.info("Sending message to Pulsar: {}", message);

        pulsarTemplate.send(TOPIC, message)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    logger.error("Failed to send message: {}", message, ex);
                } else {
                    logger.info("Message sent successfully with ID: {}", result.getMessageId());
                }
            });
    }

    public void sendMessageWithKey(DemoMessage message, String key) {
        logger.info("Sending message with key '{}' to Pulsar: {}", key, message);

        pulsarTemplate.newMessage(message)
            .withTopic(TOPIC)
            .withMessageCustomizer(messageBuilder -> messageBuilder.key(key))
            .sendAsync()
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    logger.error("Failed to send message with key: {}", message, ex);
                } else {
                    logger.info("Message with key sent successfully. Message ID: {}", result.getMessageId());
                }
            });
    }
}

