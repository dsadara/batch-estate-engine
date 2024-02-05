package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

public class RealEstateJobLauncherCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;
    @Value("${openapi.request.url.aptRent}")
    private String baseUrl;
    @Value("${openapi.request.serviceKey}")
    private String serviceKey;

    @Override
    public void run(String... args) throws Exception {
        realEstateJobLauncher.launchJob(baseUrl, serviceKey, "11110", "201501");
    }

}
