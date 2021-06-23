package com.example.port_operation;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.port_operation"})
@EnableJpaRepositories(basePackages = "com.example.port_operation.repository.interfaces")
public class PortOperationApplication {
    private final Log log = LogFactory.getLog(PortOperationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PortOperationApplication.class, args);

    }


}
