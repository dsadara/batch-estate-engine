package com.dsadara.realestatebatchservice.exception;

public class SlaveStepFailureLimitExceededException extends RuntimeException {
    public SlaveStepFailureLimitExceededException() {
        super("slave step의 실패 횟수가 50개를 초과하여 job의 실행을 중단합니다.");
    }
}