package com.dsadara.realestatebatchservice.listener;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBatchTest
@SpringBootTest
class FailedStepCounterTest {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;
    @MockBean
    private FailedStepCounter failedStepCounter;

    @Test
    void afterJobTest() throws Exception {
        // given
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", "http://SampleUrl1.co.kr")
                .addString("serviceKey", "SampleServiceKey1")
                .addString("bjdCode", "11110")
                .toJobParameters();

        // when
        JobExecution jobExecution = realEstateJobLauncher.launchJob(parameters);

        // then
        verify(failedStepCounter, times(1)).afterJob(jobExecution);
    }
}