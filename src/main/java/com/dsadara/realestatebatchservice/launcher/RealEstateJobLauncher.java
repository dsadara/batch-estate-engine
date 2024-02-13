package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.config.CreateRealEstateJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final CreateRealEstateJobConfig realEstateJobConfig;

    public JobExecution launchJob(JobParameters parameters) throws Exception {
        return jobLauncher.run(realEstateJobConfig.createRealEstateJob(), parameters);
    }
}