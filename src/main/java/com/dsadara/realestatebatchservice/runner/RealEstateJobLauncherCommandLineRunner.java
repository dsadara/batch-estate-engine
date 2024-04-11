package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
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
    private String baseUrl_aptRent;
    @Value("${openapi.request.url.aptTrade}")
    private String baseUrl_aptTrade;
    @Value("${openapi.request.url.rowhouseRent}")
    private String baseUrl_rowhouseRent;
    @Value("${openapi.request.url.officetelRent}")
    private String baseUrl_officetelRent;
    @Value("${openapi.request.url.detachedhouseRent}")
    private String baseUrl_detachedHouseRent;
    @Value("${openapi.request.serviceKey}")
    private String serviceKey;

    @Override
    public void run(String... args) throws Exception {
        launchAptRentJob();
        launchAptTradeJob();
        launchRowHouseRentJob();
        launchOfficetelRentJob();
        launchDetachedHouseRent();
    }

    private void launchAptRentJob() throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl_aptRent)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", RealEstateType.APT_RENT.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

    private void launchAptTradeJob() throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl_aptTrade)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", RealEstateType.APT_TRADE.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

    private void launchRowHouseRentJob() throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl_rowhouseRent)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", RealEstateType.ROWHOUSE_RENT.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

    private void launchOfficetelRentJob() throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl_officetelRent)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", RealEstateType.EFFICENCYAPT_RENT.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

    private void launchDetachedHouseRent() throws Exception {
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("baseUrl", baseUrl_detachedHouseRent)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", RealEstateType.DETACHEDHOUSE_RENT.name())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            realEstateJobLauncher.launchJob(parameters);
        }
    }

}
