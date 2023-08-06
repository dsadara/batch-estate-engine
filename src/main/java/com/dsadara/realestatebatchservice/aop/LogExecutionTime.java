package com.dsadara.realestatebatchservice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)    // 에노테이션 유지 기간 -> 런타임 시간
public @interface LogExecutionTime {
}
