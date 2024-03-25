package com.dsadara.realestatebatchservice.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlaveStepFailureLimitListener extends StepExecutionListenerSupport {

    @Autowired
    private JobOperator jobOperator;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.FAILED) {
            JobExecution jobExecution = stepExecution.getJobExecution();
            int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps", 0);
            jobExecution.getExecutionContext().put("failedSteps", ++failedSteps);
            if (failedSteps >= 50) {
                try {
                    return handleFailureLimitExceed(stepExecution);
                } catch (NoSuchJobExecutionException | JobExecutionNotRunningException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return stepExecution.getExitStatus();
    }

    public ExitStatus handleFailureLimitExceed(StepExecution stepExecution) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        jobOperator.stop(stepExecution.getJobExecutionId());
        return ExitStatus.FAILED;
    }

}
