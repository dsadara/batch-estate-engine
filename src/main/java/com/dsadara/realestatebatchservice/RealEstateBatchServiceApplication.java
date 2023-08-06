package com.dsadara.realestatebatchservice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class RealEstateBatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateBatchServiceApplication.class, args);
    }

}
