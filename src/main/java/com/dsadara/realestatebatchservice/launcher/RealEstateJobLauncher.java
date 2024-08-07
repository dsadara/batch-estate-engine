package com.dsadara.realestatebatchservice.launcher;

import com.dsadara.realestatebatchservice.service.GenerateApiQueryParam;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Profile("!rds-mariadb-migration")
@Slf4j
@RequiredArgsConstructor
@Component
public class RealEstateJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job realEstateJob;
    private final Environment env;
    private final GenerateApiQueryParam generateApiQueryParam;

    public void launchJob(RealEstateType realEstateType) throws Exception {
        String requestUrl = env.getProperty("openapi.request.url." + realEstateType.name());
        String serviceKey = env.getProperty("openapi.request.serviceKey");
        for (String bjdCode : generateApiQueryParam.getBjdCodeList()) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("baseUrl", requestUrl)
                    .addString("serviceKey", serviceKey)
                    .addString("bjdCode", bjdCode)
                    .addString("realEstateType", realEstateType.name())
                    .toJobParameters();
            jobLauncher.run(realEstateJob, jobParameters);
        }
    }

    public void executeWithRetry(RealEstateType realEstateType, int maxAttempts) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                launchJob(realEstateType);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxAttempts) {
                    log.error("[{}][{} 번 재시도 후에도 데이터 호출 실패][실패 이유:{}]", realEstateType.getKrName(), maxAttempts, e.getMessage());
                } else {
                    log.error("[{}][{} 번째 재실행 시작][실패 이유:{}]", realEstateType.getKrName(), attempt, e.getMessage());
                }
            }
        }
    }

}