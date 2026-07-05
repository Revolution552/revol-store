package com.backend.revol_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
@EnableScheduling
public class RevolStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(RevolStoreApplication.class, args);
    }
}
