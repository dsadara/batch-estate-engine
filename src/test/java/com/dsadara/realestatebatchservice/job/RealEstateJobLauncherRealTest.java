package com.dsadara.realestatebatchservice.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RealEstateJobLauncherRealTest {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;

    @Test
    @DisplayName("두 매개변수가 JobParameters에 잘 들어갔는지 확인")
    void launchJob() throws Exception {
        // given, when
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        String bjdCode = "11110";
        String contractYMD = "201501";
        JobExecution jobExecution = realEstateJobLauncher.launchJob(baseUrl, serviceKey, bjdCode, contractYMD);

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assertions.assertEquals(baseUrl, jobExecution.getJobParameters().getString("baseUrl"));
        Assertions.assertEquals(serviceKey, jobExecution.getJobParameters().getString("serviceKey"));
        Assertions.assertEquals(bjdCode, jobExecution.getJobParameters().getString("bjdCode"));
        Assertions.assertEquals(contractYMD, jobExecution.getJobParameters().getString("contractYMD"));
    }
}