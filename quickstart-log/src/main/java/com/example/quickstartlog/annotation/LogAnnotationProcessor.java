package com.example.quickstartlog.annotation;

import com.example.quickstartlog.utils.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 基于注解的aop
 *
 * @author zhouqiang
 */
@Aspect
@Component
public class LogAnnotationProcessor {
    /**
     * 基于注解的切点表达式
     * "@annotation(logMethodAnno)"
     * "@annotation(com.example.demo.annotation.LogMethod)"
     */
    private final String annotationPoint = "@annotation(logMethodAnno)";

    @Pointcut(value = annotationPoint, argNames = "logMethodAnno")
    public void execute(LogMethod logMethodAnno) {

    }

    @Before(value = "execute(logMethodAnno)", argNames = "joinPoint,logMethodAnno")
    public void process(JoinPoint joinPoint, LogMethod logMethodAnno) {
        if (isParamLogUnnecessary(logMethodAnno, ParameterType.IN)) {
            return;
        }
        LogUtils.logInParameter(joinPoint);
    }

    /**
     * 判断参数是否不需要打印
     * @param logMethodAnno 方法注解
     * @param type 不需要打印的
     */
    private boolean isParamLogUnnecessary(LogMethod logMethodAnno, ParameterType type) {
        return !Arrays.asList(logMethodAnno.value()).contains(type);
    }


    @AfterReturning(value = "execute(logMethodAnno)", returning = "methodResult",
            argNames = "joinPoint,methodResult,logMethodAnno")
    public void process(JoinPoint joinPoint, Object methodResult, LogMethod logMethodAnno) {
        if (isParamLogUnnecessary(logMethodAnno, ParameterType.OUT)) {
            return;
        }
        LogUtils.logOutParameter(joinPoint, methodResult);
    }
}
