package com.dsadara.realestatebatchservice.launcher;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class RealEstateJobLauncherTest {

    @InjectMocks
    private RealEstateJobLauncher realEstateJobLauncher;

    @Mock
    private GenerateApiQueryParam generateApiQueryParam;
    @Mock
    private Environment env;
    @Mock
    private JobLauncher jobLauncher;
    @Mock
    private Job realEstateJob;
    @Captor
    private ArgumentCaptor<JobParameters> jobParametersCaptor;

    private final RealEstateType realEstateType = RealEstateType.APT_RENT;
    private final List<String> bjdCodeList = Arrays.asList("11110", "11111");
    private final String requestUrl = "https://api.example.com/data";
    private final String serviceKey = "test-service-key";
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUpMock() {
        when(env.getProperty("openapi.request.url." + realEstateType.name())).thenReturn(requestUrl);
        when(env.getProperty("openapi.request.serviceKey")).thenReturn(serviceKey);
        when(generateApiQueryParam.getBjdCodeList()).thenReturn(bjdCodeList);
    }

    @BeforeEach
    void setUpLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(RealEstateJobLauncher.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void testLaunchJobWithCorrectJobParameters() throws Exception {
        // given, when
        realEstateJobLauncher.launchJob(realEstateType);

        // then
        verify(jobLauncher, times(bjdCodeList.size())).run(eq(realEstateJob), jobParametersCaptor.capture());

        List<JobParameters> capturedJobParametersList = jobParametersCaptor.getAllValues();
        Assertions.assertEquals(2, capturedJobParametersList.size());

        for (int i = 0; i < bjdCodeList.size(); i++) {
            JobParameters params = capturedJobParametersList.get(i);
            Assertions.assertEquals(requestUrl, params.getString("baseUrl"));
            Assertions.assertEquals(serviceKey, params.getString("serviceKey"));
            Assertions.assertEquals(bjdCodeList.get(i), params.getString("bjdCode"));
            Assertions.assertEquals(realEstateType.name(), params.getString("realEstateType"));
            Assertions.assertNotNull(params.getLong("time"));
        }
    }

    @Test
    void testLaunchJob() throws Exception {
        // given, when
        realEstateJobLauncher.launchJob(RealEstateType.APT_RENT);

        // then
        verify(jobLauncher, times(2)).run(any(), any(JobParameters.class));
    }

    @Test
    void testExecuteWithRetryAllAttemptsFail() throws Exception {
        // Given
        doThrow(new RuntimeException("Test Exception")).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // When
        realEstateJobLauncher.executeWithRetry(RealEstateType.APT_RENT, 2);

        // Then
        assertThat(listAppender.list).extracting(ILoggingEvent::getMessage)
                .contains("[{}] {} 번 재시도 후에도 데이터 호출 실패.");
        assertThat(listAppender.list).extracting(ILoggingEvent::getMessage)
                .contains("[{}] {} 번째 재실행 시작");
    }

    @Test
    void testExecuteWithRetryWithOneMaxAttempts() throws Exception {
        // Given
        doThrow(new RuntimeException("Test Exception"))
                .when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // When
        realEstateJobLauncher.executeWithRetry(RealEstateType.APT_RENT, 1);

        // Then
        assertThat(listAppender.list).extracting(ILoggingEvent::getMessage)
                .contains("[{}] {} 번 재시도 후에도 데이터 호출 실패.");

    }

    @Test
    void testExecuteWithRetryWithZeroMaxAttempts() throws Exception {
        // Given
        doThrow(new RuntimeException("Test Exception"))
                .when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // When
        realEstateJobLauncher.executeWithRetry(RealEstateType.APT_RENT, 0);

        // Then
        assertThat(listAppender.list).extracting(ILoggingEvent::getMessage)
                .doesNotContain("[{}] {} 번 재시도 후에도 데이터 호출 실패.");
    }

}