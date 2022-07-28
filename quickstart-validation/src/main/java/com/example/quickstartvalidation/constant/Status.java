package com.example.quickstartvalidation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Status implements EnumAvailable<Integer> {

	DESTROY(3, ""),
	READY(2, ""),
	NEW(1, "");

	private final Integer status;
	private final String desc;


	@Override
	public boolean isExist(Integer value) {
		for (Status value1 : Status.values()) {
			if (Objects.equals(value1.getStatus(), value)) {
				return true;
			}
		}
		return false;
	}
}
