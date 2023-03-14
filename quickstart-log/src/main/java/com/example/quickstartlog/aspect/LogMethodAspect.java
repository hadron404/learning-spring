package com.example.quickstartlog.aspect;

import com.example.quickstartlog.utils.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 基于包结构、类、方法的aop
 * <p>
 * controller类请求参数日志拦截.
 *
 * @author zhouqiang
 */
@Aspect
@Component
public class LogMethodAspect {

	/**
	 * 切点表达式
	 */
	private final String controllerPoint = "execution(* com..*.controller.*.*(..))";

	@Pointcut(controllerPoint)
	public void execute() {

	}

	@Before("execute()")
	public void process(JoinPoint joinPoint) {
		LogUtils.logInParameter(joinPoint);
	}

	@AfterReturning(value = "execute()", returning = "methodResult")
	public void process(JoinPoint joinPoint, Object methodResult) {
		LogUtils.logOutParameter(joinPoint, methodResult);
	}
}
