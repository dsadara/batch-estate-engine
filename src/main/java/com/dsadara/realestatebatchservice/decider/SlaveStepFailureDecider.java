package com.dsadara.realestatebatchservice.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class SlaveStepFailureDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps", 0);
        if (50 <= failedSteps) {
            return FlowExecutionStatus.STOPPED;
        }
        return FlowExecutionStatus.COMPLETED;
    }
}
