package com.example.statemachine.service;


import com.example.statemachine.domain.Operator;
import com.example.statemachine.domain.Order;
import org.springframework.statemachine.StateMachineEventResult;
import reactor.core.publisher.Flux;

public interface OrderUseCase {

	Flux<StateMachineEventResult<OrderState, OrderEvent>> transit(OrderCommand command);

	record OrderCommand(Order.OrderId id, OrderEvent event, Operator operator) {
		public OrderCommand(OrderEvent event, Operator operator) {
			this(null, event, operator);
		}
	}
}
