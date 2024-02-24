package com.dsadara.realestatebatchservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class StepExceptionLogger implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        List<Throwable> failureExceptions = stepExecution.getFailureExceptions();
        if (!failureExceptions.isEmpty()) {
            for (Throwable failureException : failureExceptions) {
                log.error("[Step 에러 발생][법정동 코드 {}][계약 연월일 {}] ErrorMessage : {}",
                        "11110", "201501", failureException.getMessage());
            }
        }
        return stepExecution.getExitStatus();
    }

}
