package com.example.quickstartvalidation.constranit.validator;

import com.example.quickstartvalidation.constant.EnumAvailable;
import com.example.quickstartvalidation.constranit.Exist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ExistForEnum implements ConstraintValidator<Exist, Integer> {
	private Class<? extends Enum<?>>[] enums;

	@Override
	public void initialize(Exist constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		enums = constraintAnnotation.target();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return Arrays.stream(enums).anyMatch((aEnum) -> this.isExistInEnum(value, aEnum));
	}

	private boolean isExistInEnum(Integer value, Class<? extends Enum<?>> aEnum) {
		EnumAvailable<Integer>[] enumAvailableArray = (EnumAvailable<Integer>[]) aEnum.getEnumConstants();
		if (enumAvailableArray == null || enumAvailableArray.length == 0) {
			return false;
		}
		return enumAvailableArray[0].isExist(value);
	}
}
