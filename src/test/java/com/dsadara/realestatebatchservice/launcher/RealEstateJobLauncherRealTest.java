package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class RealEstateJobLauncherRealTest {

    @Mock
    private GenerateApiQueryParam generateApiQueryParam;
    @Mock
    private Environment env;
    @Mock
    private JobLauncher jobLauncher;
    @Mock
    private Job realEstateJob;
    @InjectMocks
    private RealEstateJobLauncher realEstateJobLauncher;
    @Captor
    private ArgumentCaptor<JobParameters> jobParametersCaptor;

    @Test
    void testLaunchJobWithCorrectJobParameters() throws Exception {
        // given
        RealEstateType realEstateType = RealEstateType.APT_RENT;
        List<String> bjdCodeList = Arrays.asList("11110", "11111");
        String requestUrl = "https://api.example.com/data";
        String serviceKey = "test-service-key";
        when(env.getProperty("openapi.request.url." + realEstateType.name())).thenReturn(requestUrl);
        when(env.getProperty("openapi.request.serviceKey")).thenReturn(serviceKey);
        when(generateApiQueryParam.getBjdCodeList()).thenReturn(bjdCodeList);

        // when
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
        // given
        when(env.getProperty("openapi.request.url." + RealEstateType.APT_RENT.name())).thenReturn("http://example.com");
        when(env.getProperty("openapi.request.serviceKey")).thenReturn("serviceKey123");
        when(generateApiQueryParam.getBjdCodeList()).thenReturn(Arrays.asList("11110", "11111"));

        // when
        realEstateJobLauncher.launchJob(RealEstateType.APT_RENT);

        // then
        verify(jobLauncher, times(2)).run(any(), any(JobParameters.class));
    }

}