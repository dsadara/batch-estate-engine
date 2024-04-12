package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job realEstateJob;
    private final Environment env;
    private final GenerateApiQueryParam generateApiQueryParam;

    public void launchJob(RealEstateType realEstateType) throws Exception {
        String requestUrl = env.getProperty("openapi.request.url." + realEstateType.name());
        String serviceKey = env.getProperty("openapi.request.serviceKey");
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("baseUrl", requestUrl)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", realEstateType.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(realEstateJob, jobParameters);
        }
    }

}