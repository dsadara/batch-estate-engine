package com.dsadara.realestatebatchservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class RealEstateApiReaderContextInitializer implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put("bjdCode", "11110");
        stepExecution.getExecutionContext().put("contractYMD", "201501");
        log.info("샘플 query parameter 전달");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
