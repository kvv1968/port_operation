package com.example.port_operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.port_operation"})
@EnableJpaRepositories(basePackages = "com.example.port_operation.repository.interfaces")
public class PortOperationApplication {
    private static final Logger logger = LoggerFactory.getLogger(PortOperationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PortOperationApplication.class, args);

    }


}
