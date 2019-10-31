package com.abuob.tickets.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@ImportResource({"classpath*:embeddedDatabaseContext.xml"})
public class EmbeddedDatabaseConfiguration {
}
