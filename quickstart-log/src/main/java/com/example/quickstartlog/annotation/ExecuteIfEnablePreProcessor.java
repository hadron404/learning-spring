package com.example.quickstartlog.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecuteIfEnablePreProcessor {
    private final String annotationPoint = "@annotation(executeIfEnable)";

    @Pointcut(value = annotationPoint, argNames = "executeIfEnable")
    public void execute(ExecuteIfEnable executeIfEnable) {

    }

    @Around(value = "execute(executeIfEnable)", argNames = "pjp,executeIfEnable")
    public Object process(ProceedingJoinPoint pjp, ExecuteIfEnable executeIfEnable) throws Throwable {
		// here is valid code
		// executeIfEnable.value(); get param from anno
        return pjp.proceed();
    }


}
