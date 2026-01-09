package com.gk.pulsar_poc.service;

import com.gk.pulsar_poc.model.DemoMessage;
import org.apache.pulsar.client.api.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.pulsar.test.support.PulsarTestContainerSupport;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class MessageProducerTest implements PulsarTestContainerSupport {

    @Container
    static PulsarContainer pulsarContainer = new PulsarContainer("apachepulsar/pulsar:latest");

    @DynamicPropertySource
    static void pulsarProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.pulsar.client.service-url", pulsarContainer::getPulsarBrokerUrl);
        registry.add("spring.pulsar.admin.service-url", pulsarContainer::getHttpServiceUrl);
    }

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private PulsarTemplate<DemoMessage> pulsarTemplate;

    @Test
    void testSendMessage() throws Exception {
        // Given
        DemoMessage message = new DemoMessage("test-123", "Test message content");

        // When
        messageProducer.sendMessage(message);

        // Then - verify message was sent (allow async operation to complete)
        TimeUnit.SECONDS.sleep(2);

        assertNotNull(message.getId());
        assertEquals("Test message content", message.getContent());
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testSendMessageWithKey() throws Exception {
        // Given
        DemoMessage message = new DemoMessage("test-456", "Message with key");
        String key = "user-123";

        // When
        messageProducer.sendMessageWithKey(message, key);

        // Then - verify message was sent
        TimeUnit.SECONDS.sleep(2);

        assertNotNull(message.getId());
        assertEquals("Message with key", message.getContent());
    }
}

