package com.dsadara.realestatebatchservice.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final CreateRealEstateJobConfig realEstateJobConfig;

    public JobExecution launchJob(
            @Value("${openapi.request.url.aptRent}") String baseUrl,
            @Value("${openapi.request.serviceKey}") String serviceKey) throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", baseUrl)
                .addString("serviceKey", serviceKey)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        return jobLauncher.run(realEstateJobConfig.createRealEstateJob(), parameters);
    }
}