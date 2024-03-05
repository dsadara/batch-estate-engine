package com.dsadara.realestatebatchservice.launcher;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job realEstateJob;

    public JobExecution launchJob(JobParameters parameters) throws Exception {
        return jobLauncher.run(realEstateJob, parameters);
    }

}