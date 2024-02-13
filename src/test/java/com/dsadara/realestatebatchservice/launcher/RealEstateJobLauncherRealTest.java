package com.dsadara.realestatebatchservice.launcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("파티셔닝 사용으로 테스트 코드 변경 필요")
@SpringBootTest
class RealEstateJobLauncherRealTest {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;

    @Test
    @DisplayName("두 매개변수가 JobParameters에 잘 들어갔는지 확인")
    void launchJob() throws Exception {
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
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        Assertions.assertEquals(baseUrl, jobExecution.getJobParameters().getString("baseUrl"));
        Assertions.assertEquals(serviceKey, jobExecution.getJobParameters().getString("serviceKey"));
    }
}