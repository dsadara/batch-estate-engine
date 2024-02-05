package com.dsadara.realestatebatchservice.listener;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
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

import static org.assertj.core.api.Assertions.assertThat;
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
    private Job testJob;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUpJob() throws Exception {
        when(itemReader.read()).thenThrow(new RuntimeException("Test Exception"));
        StepExceptionLogger stepExceptionLogger = new StepExceptionLogger();

        Step testStep = stepBuilderFactory.get("testStep")
                .<String, String>chunk(1)
                .reader(itemReader)
                .writer(list -> {
                })
                .listener(stepExceptionLogger)
                .build();

        testJob = jobBuilderFactory.get("testJob")
                .start(testStep)
                .build();
    }

    @BeforeEach
    void setUpLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(StepExceptionLogger.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void testStepFailure() throws Exception {
        // given, when
        SimpleJobLauncher testJobLauncher = new SimpleJobLauncher();
        testJobLauncher.setJobRepository(jobRepository);
        testJobLauncher.afterPropertiesSet();
        JobExecution jobExecution = testJobLauncher.run(testJob, new JobParameters());
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

        // then
        Assertions.assertFalse(stepExecution.getFailureExceptions().isEmpty());
        Assertions.assertTrue(stepExecution.getFailureExceptions().get(0).getMessage().contains("Test Exception"));
    }

    @Test
    public void testStepFailureLogging() throws Exception {
        // given, when
        SimpleJobLauncher testJobLauncher = new SimpleJobLauncher();
        testJobLauncher.setJobRepository(jobRepository);
        testJobLauncher.afterPropertiesSet();
        testJobLauncher.run(testJob, new JobParameters());

        // then
        assertThat(listAppender.list).extracting(ILoggingEvent::getMessage)
                .contains("[Step 에러 발생][법정동 코드 {}][계약 연월일 {}] ErrorMessage : {}");
    }
}