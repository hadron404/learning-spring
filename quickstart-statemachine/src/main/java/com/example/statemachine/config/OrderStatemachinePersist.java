package com.example.statemachine.config;

import com.example.statemachine.domain.Order;
import com.example.statemachine.service.OrderEvent;
import com.example.statemachine.service.OrderState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class OrderStatemachinePersist implements StateMachinePersist<OrderState, OrderEvent, Order> {
	@Override
	public void write(StateMachineContext<OrderState, OrderEvent> context, Order contextObj) {

	}

	@Override
	public StateMachineContext<OrderState, OrderEvent> read(Order context) {
		return new DefaultStateMachineContext<>(OrderState.valueOf(context.getState().toString()),
			null, null, null, null, OrderStatemachine.MACHINE_ID);
	}
}
