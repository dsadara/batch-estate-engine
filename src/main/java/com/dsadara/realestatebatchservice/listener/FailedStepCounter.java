package com.dsadara.realestatebatchservice.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
@Slf4j
@Component
public class FailedStepCounter extends JobExecutionListenerSupport {

    private static final int ALLOWED_FAILED_STEPS = 50;
    private int failedSteps = 0;

    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        failedSteps = (int) stepExecutions.stream()
                .filter(step -> step.getStatus() == BatchStatus.FAILED)
                .count();
    }
}