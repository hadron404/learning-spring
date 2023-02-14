package com.example.statemachine.domain;

import jdk.jfr.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Flow {

	private FlowId id;

	private Operator operator;

	private Event event;

	public Flow(Operator operator, Event event) {
		this.operator = operator;
		this.event = event;
	}

	public record FlowId(Integer value) {
	}


}
