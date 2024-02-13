package com.dsadara.realestatebatchservice.listener;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("파티셔닝 사용으로 테스트 코드 변경 필요")
@SpringBootTest
class RealEstateApiReaderContextInitializerTest {
    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;

    @Test
    public void assertStepExecutionContext() throws Exception {
        // given
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
        String serviceKey = "KNxUoxDnwzkyp3fb8dOjCWatfWm6VdGxJHzwOlvkSAcOcm%2B6%2BgIsOrcZ8Wr8hU0qzcmNE2tSjG7HUQBIA%2FqkYg%3D%3D";
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", baseUrl)
                .addString("serviceKey", serviceKey)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // when
        JobExecution jobExecution = realEstateJobLauncher.launchJob(parameters);

        // then
        for (StepExecution step : jobExecution.getStepExecutions()) {
            Assertions.assertEquals(step.getExecutionContext().getString("bjdCode"), "11110");
            Assertions.assertEquals(step.getExecutionContext().getString("contractYMD"), "201501");
        }

    }
}