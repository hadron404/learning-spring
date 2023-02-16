package com.example.statemachine.service;


import com.example.statemachine.domain.Operator;
import com.example.statemachine.domain.Order;

public interface OrderUseCase {

	void transit(OrderCommand command);

	record OrderCommand(Order.OrderId id, OrderEvent event, Operator operator) {
		public OrderCommand(OrderEvent event, Operator operator) {
			this(null, event, operator);
		}
	}
}
