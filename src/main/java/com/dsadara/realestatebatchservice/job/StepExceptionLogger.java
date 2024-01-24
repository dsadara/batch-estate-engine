package com.dsadara.realestatebatchservice.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


@Slf4j
public class StepExceptionLogger implements StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.FAILED) {
            Throwable exception = stepExecution.getFailureExceptions().get(0);
            logger.error("[Step 에러 발생][법정동 코드 {}][계약 연월일 {}] ErrorMessage : {}",
                    "11110", "201501", exception.getMessage());
        }
        return stepExecution.getExitStatus();
    }
}
