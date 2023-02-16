package com.example.statemachine.config;

import com.example.statemachine.service.OrderEvent;
import com.example.statemachine.service.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@AllArgsConstructor
public class OrderStatemachine
	extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent> {

	protected static final String MACHINE_ID = "orderStatemachine";

	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config)
		throws Exception {
		config
			.withConfiguration()
			.machineId(MACHINE_ID)
			.autoStartup(true);
	}

	@Override
	public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> state)
		throws Exception {
		state
			.withStates()
			.initial(OrderState.UN_CREATE)
			.states(EnumSet.allOf(OrderState.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions)
		throws Exception {
		transitions
			.withExternal()
			.source(OrderState.UN_CREATE).target(OrderState.WAITING_PICK).event(OrderEvent.CREATE)
			.and()
			.withExternal()
			.source(OrderState.WAITING_PICK).target(OrderState.HANDLING).event(OrderEvent.PICK)
			.and()
			.withExternal()
			.source(OrderState.HANDLING).target(OrderState.WAITING_FINISH).event(OrderEvent.SUBMIT)
			.and()
			.withExternal()
			.source(OrderState.WAITING_FINISH).target(OrderState.FINISHED).event(OrderEvent.FINISH)
		;
	}

}
