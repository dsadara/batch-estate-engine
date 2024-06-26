package com.dsadara.realestatebatchservice;

import com.dsadara.realestatebatchservice.runner.RealEstateJobLauncherCommandLineRunner;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableBatchProcessing
public class RealEstateBatchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateBatchServiceApplication.class, args);
    }

    @Profile({"local-mysql", "rds-mariadb"})
    @Bean
    public RealEstateJobLauncherCommandLineRunner realEstateJobLauncherCommandLineRunner() {
        return new RealEstateJobLauncherCommandLineRunner();
    }

}