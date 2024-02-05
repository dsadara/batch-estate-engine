package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.config.CreateRealEstateJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final CreateRealEstateJobConfig realEstateJobConfig;

    public JobExecution launchJob(String baseUrl, String serviceKey, String bjdCode, String contractYMD) throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", baseUrl)
                .addString("serviceKey", serviceKey)
                .addString("bjdCode", bjdCode)
                .addString("contractYMD", contractYMD)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        return jobLauncher.run(realEstateJobConfig.createRealEstateJob(), parameters);
    }
}