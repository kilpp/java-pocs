package com.gk.pulsar_poc.listener;

import com.gk.pulsar_poc.model.DemoMessage;
import org.apache.pulsar.client.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @PulsarListener(
        topics = "persistent://public/default/demo-topic",
        subscriptionName = "demo-subscription"
    )
    public void consumeMessage(DemoMessage message) {
        logger.info("Received message: {}", message);
        // Process the message here
        processMessage(message);
    }

    @PulsarListener(
        topics = "persistent://public/default/demo-topic-with-metadata",
        subscriptionName = "demo-subscription-metadata"
    )
    public void consumeMessageWithMetadata(Message<DemoMessage> message) {
        logger.info("Received message with metadata:");
        logger.info("  Message ID: {}", message.getMessageId());
        logger.info("  Key: {}", message.getKey());
        logger.info("  Publish Time: {}", message.getPublishTime());
        logger.info("  Topic: {}", message.getTopicName());
        logger.info("  Payload: {}", message.getValue());

        // Process the message
        processMessage(message.getValue());
    }

    private void processMessage(DemoMessage message) {
        // Add your business logic here
        logger.debug("Processing message with ID: {}", message.getId());

        // Simulate some processing
        try {
            Thread.sleep(100);
            logger.info("Message processed successfully: {}", message.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error processing message", e);
        }
    }
}

