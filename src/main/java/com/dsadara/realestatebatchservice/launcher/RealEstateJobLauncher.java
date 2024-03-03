package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.config.RealEstateJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final RealEstateJobConfig realEstateJobConfig;

    public JobExecution launchJob(JobParameters parameters) throws Exception {
        return jobLauncher.run(realEstateJobConfig.createRealEstateJob(), parameters);
    }

}