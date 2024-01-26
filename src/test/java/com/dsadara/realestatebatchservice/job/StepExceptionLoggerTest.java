package com.dsadara.realestatebatchservice.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@SpringBatchTest
@SpringBootTest
@EnableBatchProcessing
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class StepExceptionLoggerTest {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @MockBean
    private ItemReader<String> itemReader;

    @BeforeEach
    void setUpMock() throws Exception {
        when(itemReader.read()).thenThrow(new RuntimeException("Test Exception"));
    }

    @Test
    public void testStepFailure() throws Exception {
        // given
        StepExceptionLogger stepExceptionLogger = new StepExceptionLogger();

        Step testStep = stepBuilderFactory.get("testStep")
                .<String, String>chunk(1)
                .reader(itemReader)
                .writer(list -> {
                })
                .listener(stepExceptionLogger)
                .build();

        Job testJob = jobBuilderFactory.get("testJob")
                .start(testStep)
                .build();

        SimpleJobLauncher testJobLauncher = new SimpleJobLauncher();
        testJobLauncher.setJobRepository(jobRepository);
        testJobLauncher.afterPropertiesSet();

        // when
        JobExecution jobExecution = testJobLauncher.run(testJob, new JobParameters());
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

        // then
        Assertions.assertFalse(stepExecution.getFailureExceptions().isEmpty());
        Assertions.assertTrue(stepExecution.getFailureExceptions().get(0).getMessage().contains("Test Exception"));
    }
}