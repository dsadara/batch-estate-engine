package com.dsadara.realestatebatchservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
	private final CustomStopwatch stopwatch;

	public LogAspect(CustomStopwatch customStopwatch) {
		this.stopwatch = customStopwatch;
	}

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		stopwatch.start(joinPoint.getSignature().getName());

		Object proceed = joinPoint.proceed();    // @LogExcutionTime이 붙여진 타겟 메소드를 실행함

		stopwatch.stop(joinPoint.getSignature().getName());
		return proceed;
	}
}
