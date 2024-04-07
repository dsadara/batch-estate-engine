package com.dsadara.realestatebatchservice.utils;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.util.ArrayList;
import java.util.List;

public class ResultPrinter {
    public static void jobResultPrinter(JobExecution jobExecution) {
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

        for (int i = 0; i < failedSteps.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + " 실패한 step: " + failedSteps.get(i).getStepName());
        }
        System.out.println("=======>" + " 실패한 step 개수: " + failedSteps.size() + "\n");
        for (int i = 0; i < completedSteps.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + " 성공한 step: " + completedSteps.get(i).getStepName());
        }
        System.out.println("=======>" + " 성공한 step 개수: " + completedSteps.size() + "\n");
        for (int i = 0; i < otherSteps.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + " 나머지 step: " + otherSteps.get(i).getStepName());
        }
        System.out.println("=======>" + " 나머지 step 개수: " + otherSteps.size() + "\n");
    }
}
