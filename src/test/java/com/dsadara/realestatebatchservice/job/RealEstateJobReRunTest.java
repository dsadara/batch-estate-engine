package com.dsadara.realestatebatchservice.job;


import com.dsadara.realestatebatchservice.dto.RealEstateDto;
import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.utils.PartialFailRealEstateDtoSimulation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBatchTest
@SpringBootTest
public class RealEstateJobReRunTest {

    static String BASE_URL_1 = "http://rerun.co.kr";
    static String SERVICE_KEY_1 = "ReRunServiceKey1";
    static String BJD_CODE_1 = "11110";
    static String BASE_URL_2 = "http://rerun.co.kr";
    static String SERVICE_KEY_2 = "ReRunServiceKey2";
    static String BJD_CODE_2 = "11111";

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @MockBean(name = "createEmptyItemReader")
    private ItemReader<RealEstateDto> mockItemReader;


    @DisplayName("재실행 방지 기능 테스트 - 동일한 JobParameters 객체")
    @Test
    public void reRunPreventWithSameJobParameters() throws Exception {
        // given
        JobParameters parameters = new JobParametersBuilder().addString("baseUrl", BASE_URL_1).addString("serviceKey", SERVICE_KEY_1).addString("bjdCode", BJD_CODE_1).toJobParameters();

        // when
        JobExecution jobExecution1 = realEstateJobLauncher.launchJob(parameters);

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution1.getExitStatus());
        Assertions.assertThrowsExactly(JobInstanceAlreadyCompleteException.class, () -> realEstateJobLauncher.launchJob(parameters));
    }

    @DisplayName("재실행 방지 기능 테스트 - 내용은 같지만 다른 JobParameters 객체")
    @Test
    public void reRunPreventWithSameJobParameters2() throws Exception {
        // given
        JobParameters parameters1 = new JobParametersBuilder().addString("baseUrl", BASE_URL_2).addString("serviceKey", SERVICE_KEY_2).addString("bjdCode", BJD_CODE_2).toJobParameters();

        JobParameters parameters2 = new JobParametersBuilder().addString("baseUrl", BASE_URL_2).addString("serviceKey", SERVICE_KEY_2).addString("bjdCode", BJD_CODE_2).toJobParameters();

        // when
        JobExecution jobExecution1 = realEstateJobLauncher.launchJob(parameters1);

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution1.getExitStatus());
        Assertions.assertThrowsExactly(JobInstanceAlreadyCompleteException.class, () -> realEstateJobLauncher.launchJob(parameters2));
    }

    @DisplayName("고유값인 시간을 추가하면 재실행 가능")
    @Test
    public void reRunPreventWithSameJobParameters3() throws Exception {
        // given
        JobParameters parameters1 = new JobParametersBuilder().addString("baseUrl", BASE_URL_2).addString("serviceKey", SERVICE_KEY_2).addString("bjdCode", BJD_CODE_2).addLong("time", System.currentTimeMillis()).toJobParameters();

        JobParameters parameters2 = new JobParametersBuilder().addString("baseUrl", BASE_URL_2).addString("serviceKey", SERVICE_KEY_2).addString("bjdCode", BJD_CODE_2).addLong("time", System.currentTimeMillis() + 1).toJobParameters();

        // when
        JobExecution jobExecution1 = realEstateJobLauncher.launchJob(parameters1);
        JobExecution jobExecution2 = realEstateJobLauncher.launchJob(parameters2);

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution1.getExitStatus());
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution2.getExitStatus());
        Assertions.assertNotEquals(parameters1, parameters2);
    }

    @DisplayName("실패한 Job 재실행 테스트")
    @Test
    public void FailedJobReRunTest() throws Exception {
        // given
        JobParameters parameters = new JobParametersBuilder().addString("baseUrl", "http://FailedReRun.co.kr").addString("serviceKey", "FailedReRunKey").addString("bjdCode", "11113").toJobParameters();

        // 첫번쨰 실행은 실패로 만들기
        Mockito.when(mockItemReader.read()).thenAnswer(new PartialFailRealEstateDtoSimulation());
        JobExecution jobExecution1 = jobLauncherTestUtils.launchJob(parameters);

        // 두번째는 정상적으로 실행하도록 설정
        Mockito.reset(mockItemReader);
        Mockito.when(mockItemReader.read()).thenReturn(new RealEstateDto(), new RealEstateDto(), null);

        // when
        JobExecution jobExecution2 = jobLauncherTestUtils.launchJob(parameters);

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution2.getExitStatus());
    }

}
