package com.awarex.config;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;

@Configuration
public class JerseyClientConfig {

    @Bean
    public Client jerseyClient() {
        ClientConfig config = new ClientConfig();
        config.register(JacksonFeature.class);
        
        return JerseyClientBuilder.newClient(config);
    }
}
