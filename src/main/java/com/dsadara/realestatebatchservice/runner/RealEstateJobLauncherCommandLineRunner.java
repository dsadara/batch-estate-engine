package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

public class RealEstateJobLauncherCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;
    @Autowired
    private GenerateApiQueryParam generateApiQueryParam;
    @Value("${openapi.request.url.aptRent}")
    private String baseUrl;
    @Value("${openapi.request.serviceKey}")
    private String serviceKey;

    @Override
    public void run(String... args) throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

}
