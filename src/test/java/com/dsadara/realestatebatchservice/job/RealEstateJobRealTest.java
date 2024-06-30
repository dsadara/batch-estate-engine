package com.dsadara.realestatebatchservice.job;

import com.dsadara.realestatebatchservice.domain.RealEstateRepository;
import com.dsadara.realestatebatchservice.domain.RentRepository;
import com.dsadara.realestatebatchservice.domain.SaleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("local-h2")
public class RealEstateJobRealTest {

    @Autowired
    private Environment env;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private RealEstateRepository realEstateRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private RentRepository rentRepository;

    @Test
    public void jobLauncher_run_checkSaleOneToOneMapped() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("baseUrl", env.getProperty("openapi.request.url.APT_TRADE"))
                .addString("serviceKey", env.getProperty("openapi.request.serviceKey"))
                .addString("bjdCode", "11110")
                .addString("realEstateType", "아파트매매")
                .toJobParameters();

        // when
        jobLauncherTestUtils.launchJob(jobParameters);
        // then
    }


}
