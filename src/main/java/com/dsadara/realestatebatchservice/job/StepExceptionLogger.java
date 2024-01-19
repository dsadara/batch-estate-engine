package com.dsadara.realestatebatchservice.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepExceptionLogger implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Step 시작 전: " + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Step 종료 후: " + stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }
}
