package com.gk.pulsar_poc.config;

import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.SchemaResolver;

@Configuration
public class PulsarConfig {

    @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {
        return (schemaResolver) -> {
            schemaResolver.addCustomSchemaMapping(
                com.gk.pulsar_poc.model.DemoMessage.class,
                Schema.JSON(com.gk.pulsar_poc.model.DemoMessage.class)
            );
        };
    }
}

