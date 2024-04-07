package com.dsadara.realestatebatchservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ResultPrinter {
    public static void printJobResultDetail(JobExecution jobExecution) {
        List<StepExecution> failedSteps = new ArrayList<>();
        List<StepExecution> completedSteps = new ArrayList<>();
        List<StepExecution> otherSteps = new ArrayList<>();

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if (stepExecution.getExitStatus().getExitCode().equals("FAILED")) {
                failedSteps.add(stepExecution);
            } else if (stepExecution.getExitStatus().getExitCode().equals("COMPLETED")) {
                completedSteps.add(stepExecution);
            } else {
                otherSteps.add(stepExecution);
            }
        }

        log.info("[실패한 step 결과 출력 시작]");
        for (int i = 0; i < failedSteps.size(); i++) {
            log.info("[" + (i + 1) + "]" + " 실패한 step: " + failedSteps.get(i).getStepName());
        }
        log.info("[성공한 step 결과 출력 시작]");
        for (int i = 0; i < completedSteps.size(); i++) {
            log.info("[" + (i + 1) + "]" + " 성공한 step: " + completedSteps.get(i).getStepName());
        }
        log.info("[나머지 step 결과 출력 시작]");
        for (int i = 0; i < otherSteps.size(); i++) {
            log.info("[" + (i + 1) + "]" + " 나머지 step: " + otherSteps.get(i).getStepName()
                    + " -> " + "ExitStatus:" + otherSteps.get(i).getExitStatus().getExitCode());
        }
    }

    public static void printJobResult(JobExecution jobExecution) {
        int failedStepCount = 0;
        int completedStepCount = 0;
        int otherStepCount = 0;

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if (stepExecution.getExitStatus().getExitCode().equals("FAILED")) {
                failedStepCount++;
            } else if (stepExecution.getExitStatus().getExitCode().equals("COMPLETED")) {
                completedStepCount++;
            } else {
                otherStepCount++;
            }
        }

        log.info("실패한 step 개수 -> " + failedStepCount);
        log.info("성공한 step 개수 -> " + completedStepCount);
        log.info("나머지 step 개수 -> " + otherStepCount);
    }
}
