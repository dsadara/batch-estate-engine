package com.dsadara.realestatebatchservice.runner;

import com.dsadara.realestatebatchservice.launcher.RealEstateJobLauncher;
import com.dsadara.realestatebatchservice.type.RealEstateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Slf4j
public class RealEstateJobLauncherCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RealEstateJobLauncher realEstateJobLauncher;

    @Override
    public void run(String... args) {
        launchJobsForAllTypes();
    }

    private void launchJobsForAllTypes() {
        for (RealEstateType realEstateType : RealEstateType.values()) {
            executeWithRetry(realEstateType, 2);
        }
    }

    private void executeWithRetry(RealEstateType realEstateType, int maxAttempts) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                realEstateJobLauncher.launchJob(realEstateType);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxAttempts) {
                    log.error("[{}] {} 번 재시도 후에도 데이터 호출 실패.", realEstateType.getKrName(), maxAttempts);
                } else {
                    log.error("[{}] {} 번째 재실행 시작", realEstateType.getKrName(), attempt);
                }
            }
        }
    }
}