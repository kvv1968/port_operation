package com.example.port_operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortOperationApplication {
    private static final Logger logger = LoggerFactory.getLogger(PortOperationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PortOperationApplication.class, args);
    }

}
