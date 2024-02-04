package com.dsadara.realestatebatchservice;

import com.dsadara.realestatebatchservice.job.RealEstateJobLauncherCommandLineRunner;
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

    @Profile("!test")
    @Bean
    public RealEstateJobLauncherCommandLineRunner realEstateJobLauncherCommandLineRunner() {
        return new RealEstateJobLauncherCommandLineRunner();
    }

}