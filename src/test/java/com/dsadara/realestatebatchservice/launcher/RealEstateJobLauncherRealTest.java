package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest
class RealEstateJobLauncherRealTest {

    @InjectMocks
    private RealEstateJobLauncher realEstateJobLauncher;

    @Mock
    private GenerateApiQueryParam generateApiQueryParam;
    @Mock
    private Environment env;
    @Mock
    private JobLauncher jobLauncher;

    @Test
    void launchJob() throws Exception {
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