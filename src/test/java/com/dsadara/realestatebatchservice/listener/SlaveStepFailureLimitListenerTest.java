package com.dsadara.realestatebatchservice.listener;

import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
@SpringBatchTest
class SlaveStepFailureLimitListenerTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @MockBean(name = "createEmptyItemReader")
    private ItemReader<RealEstateDto> mockItemReader;
    @SpyBean
    private SlaveStepFailureLimitListener slaveStepFailureLimitListener;

    @Test
    void afterStep() throws Exception {
        // given
        Mockito.when(mockItemReader.read())
                .thenReturn(new RealEstateDto(), new RealEstateDto(), null)
                .thenThrow(new Exception("test Exception"));
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", "http://SlaveStepFailure.co.kr")
                .addString("serviceKey", "SlaveStepFailureKey1")
                .addString("bjdCode", "11110")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps", 0);

        // given
        Mockito.verify(slaveStepFailureLimitListener, Mockito.atMost(300)).handleFailureLimitExceed();
        Assertions.assertTrue(failedSteps <= 300);
    }
}