package com.dsadara.realestatebatchservice.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class SlaveStepFailureLimitListener extends StepExecutionListenerSupport {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.FAILED) {
            JobExecution jobExecution = stepExecution.getJobExecution();
            int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps", 0);
            jobExecution.getExecutionContext().put("failedSteps", ++failedSteps);
            if (failedSteps >= 50) {
                handleFailureLimitExceed();
            }
        }
        return stepExecution.getExitStatus();
    }

    public void handleFailureLimitExceed() {
    }
}
