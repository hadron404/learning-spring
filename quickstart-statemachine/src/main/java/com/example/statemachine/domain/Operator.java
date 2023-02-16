package com.example.statemachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Operator {
	private OperatorId operatorId;
	private String name;

	public Operator(Integer operatorId, String name) {
		this(new OperatorId(operatorId), name);
	}

	public record OperatorId(Integer value) {
	}

}
