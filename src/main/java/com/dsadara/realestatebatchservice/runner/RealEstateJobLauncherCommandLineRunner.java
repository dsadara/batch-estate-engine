package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class RealEstateJobLauncherCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;

    @Override
    public void run(String... args) throws Exception {
        realEstateJobLauncher.launchJob(RealEstateType.APT_RENT);
        realEstateJobLauncher.launchJob(RealEstateType.APT_TRADE);
        realEstateJobLauncher.launchJob(RealEstateType.ROWHOUSE_RENT);
        realEstateJobLauncher.launchJob(RealEstateType.OFFICETEL_RENT);
        realEstateJobLauncher.launchJob(RealEstateType.DETACHEDHOUSE_RENT);
    }
}
