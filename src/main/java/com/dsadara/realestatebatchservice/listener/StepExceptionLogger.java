package com.dsadara.realestatebatchservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class StepExceptionLogger implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        String errorMessage = stepExecution.getExitStatus().getExitDescription();
        String bjdCode = stepExecution.getJobExecution().getJobParameters().getString("bjdCode", "11110");
        String dealYearMonth = stepExecution.getExecutionContext().getString("dealYearMonth", "200001");

        if (exitCode.equals("FAILED")) {
            log.error("[Step 에러 발생][법정동 코드 {}][계약 연월일 {}] ErrorMessage : {}", bjdCode, dealYearMonth, errorMessage);
        }
        return stepExecution.getExitStatus();
    }

}
