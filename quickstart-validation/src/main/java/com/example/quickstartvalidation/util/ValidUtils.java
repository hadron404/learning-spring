package com.example.quickstartvalidation.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public class ValidUtils {
	public static <E> int validParameters(E validObject) {
		Set<ConstraintViolation<E>> result = Validation.buildDefaultValidatorFactory()
			.getValidator().validate(validObject);
		result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue())
			.forEach(System.out::println);
		return result.size();
	}

}
