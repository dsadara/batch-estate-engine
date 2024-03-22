package com.dsadara.realestatebatchservice.decider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
class SlaveStepFailureDeciderTest {

    @Autowired
    private SlaveStepFailureDecider slaveStepFailureDecider;
    @Mock
    private JobExecution jobExecution;

    @Test
    void decide_success() {
        // given
        Mockito.when(jobExecution.getExecutionContext()).thenReturn(new ExecutionContext());
        jobExecution.getExecutionContext().putInt("failedSteps", 49);

        // when
        FlowExecutionStatus status = slaveStepFailureDecider.decide(jobExecution, null);

        // then
        Assertions.assertEquals(FlowExecutionStatus.COMPLETED, status);
    }

    @Test
    void decide_fail() {
        // given
        Mockito.when(jobExecution.getExecutionContext()).thenReturn(new ExecutionContext());
        jobExecution.getExecutionContext().putInt("failedSteps", 50);

        // when
        FlowExecutionStatus status = slaveStepFailureDecider.decide(jobExecution, null);

        // then
        Assertions.assertEquals(FlowExecutionStatus.STOPPED, status);
    }
}