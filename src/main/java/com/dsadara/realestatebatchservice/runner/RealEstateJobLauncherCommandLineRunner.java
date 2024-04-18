package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

@Slf4j
public class RealEstateJobLauncherCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;
    @Value("${retryPolicy.maxAttempts}")
    private int maxAttempts;

    @Override
    public void run(String... args) {
        launchJobsForAllTypes();
    }

    private void launchJobsForAllTypes() {
        for (RealEstateType realEstateType : RealEstateType.values()) {
            realEstateJobLauncher.executeWithRetry(realEstateType, maxAttempts);
        }
    }

}