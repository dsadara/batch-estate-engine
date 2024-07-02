package com.dsadara.realestatebatchservice.test.utils;

import org.springframework.util.StringUtils;

public class StringValidator {

    public static boolean checkNumeric(String num) {
        if (!StringUtils.hasText(num)) {
            return false;
        }
        num = num.replace(",", "");
        num = num.trim();
        for (char digit : num.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return true;
    }

    public static String trimNumeric(String num) {
        return num.replace(",", "").trim();
    }

}
