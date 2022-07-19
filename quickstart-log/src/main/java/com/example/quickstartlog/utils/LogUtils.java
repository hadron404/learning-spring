package com.example.quickstartlog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author zhouqiang
 */
@Slf4j
public class LogUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static void logInParameter(JoinPoint joinPoint) {
		String inArgs = Arrays.stream(joinPoint.getArgs())
			.map(Object::toString)
			.collect(Collectors.joining(","));
		if (joinPoint.getSignature() == null) {
			return;
		}
		try {
			log.info("Source【】，Method name【{}】，Parameter【{}】",
				joinPoint.getSignature().getName(),
				OBJECT_MAPPER.writeValueAsString(inArgs));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public static void logOutParameter(JoinPoint joinPoint, Object methodResult) {
		log.info("Source【】，Method name【{}】，Parameter【{}】",
			joinPoint.getSignature().getName(),
			methodResult.toString());
	}
}
