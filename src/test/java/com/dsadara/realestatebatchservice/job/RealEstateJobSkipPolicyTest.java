package com.dsadara.realestatebatchservice.job;


import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest
@SpringBatchTest
public class RealEstateJobSkipPolicyTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean(name = "createEmptyItemReader")
    private ItemReader<RealEstateDto> mockItemReader;

    @Test
    public void assertPrecondition_readMethodCalledAtMost300() throws Exception {
        // given
        Mockito.when(mockItemReader.read())
                .thenReturn(new RealEstateDto(), new RealEstateDto(), null)
                .thenThrow(new Exception("test Exception"));
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", "http://SampleUrl1.co.kr")
                .addString("serviceKey", "SampleServiceKey1")
                .addString("bjdCode", "11110")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        // then
        Mockito.verify(mockItemReader, Mockito.atMost(300)).read();
        Assertions.assertEquals(ExitStatus.STOPPED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void checkStepFailCountIs50() throws Exception {
        // given
        Mockito.when(mockItemReader.read())
                .thenReturn(new RealEstateDto(), new RealEstateDto(), null)
                .thenThrow(new Exception("test Exception"));
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", "http://SampleUrl1.co.kr")
                .addString("serviceKey", "SampleServiceKey1")
                .addString("bjdCode", "11110")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps");

        // then
        Assertions.assertTrue(failedSteps == 50);
        Assertions.assertEquals(ExitStatus.STOPPED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    @Test
    public void checkStepFailCountIsUnder50() throws Exception {
        // given
        AtomicInteger callCount = new AtomicInteger(0);
        Mockito.when(mockItemReader.read())
                .thenAnswer(new Answer<RealEstateDto>() {
                    @Override
                    public RealEstateDto answer(InvocationOnMock invocation) throws Throwable {
                        int count = callCount.incrementAndGet();
                        // 49번째 step까지는 실패
                        if (count <= 49) {
                            throw new Exception("test Exception");
                        }
                        // 50번째 step은 성공
                        if (count == 50) {
                            return new RealEstateDto();
                        }
                        // 이후에 null 반환하여 종료
                        return null;
                    }
                });
        JobParameters parameters = new JobParametersBuilder()
                .addString("baseUrl", "http://SampleUrl1.co.kr")
                .addString("serviceKey", "SampleServiceKey1")
                .addString("bjdCode", "11110")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        int failedSteps = jobExecution.getExecutionContext().getInt("failedSteps");

        // then
        Assertions.assertTrue(failedSteps == 49);
        Assertions.assertEquals(ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }

    private static void jobResultPrinter(JobExecution jobExecution) {
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