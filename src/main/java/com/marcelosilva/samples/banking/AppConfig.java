package com.marcelosilva.samples.banking;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAspectJAutoProxy
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = "com.marcelosilva.samples.banking")
public class AppConfig {

}
