package com.dsadara.realestatebatchservice.service;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
@Getter
public class GenerateApiQueryParam {

    private final List<String> dealYearMonthsList;
    private final List<String> bjdCodeList;

    @Getter(AccessLevel.NONE)
    private final Map<String, String> bjdCodeMap;

    public GenerateApiQueryParam() throws FileNotFoundException {
        dealYearMonthsList = new LinkedList<>();
        bjdCodeMap = new LinkedHashMap<>();
        generateDealYearMonth();
        parseBjdCodeToMap();
        bjdCodeList = new LinkedList<>(bjdCodeMap.keySet());
    }

    private void generateDealYearMonth() {
        Period period = Period.between(LocalDate.of(2005, 1, 1), LocalDate.now());
        int periodMonths = period.getYears() * 12 + period.getMonths();
        LocalDate startDate = LocalDate.of(2005, 1, 1);
        dealYearMonthsList.add(String.format("%d%02d", startDate.getYear(), startDate.getMonthValue()));
        for (int i = 0; i < periodMonths; i++) {
            startDate = startDate.plusMonths(1);
            dealYearMonthsList.add(String.format("%d%02d", startDate.getYear(), startDate.getMonthValue()));
        }
    }

    private void parseBjdCodeToMap() throws FileNotFoundException {
        ClassPathResource resource = new ClassPathResource("bjdcode.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(resource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter("\t");

        String bjdCode;
        String siGunGu;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            bjdCode = scanner.next().substring(0, 5);
            siGunGu = scanner.next();
            if (siGunGu.contains(" ")) {
                String[] s = siGunGu.split(" ");
                siGunGu = s[0] + " " + s[1];
                bjdCodeMap.put(bjdCode, siGunGu);
            }
            scanner.nextLine();
        }
        scanner.close();
    }

}
