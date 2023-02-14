package com.example.statemachine.config;

import com.example.statemachine.domain.Order;
import com.example.statemachine.service.OrderEvent;
import com.example.statemachine.service.OrderState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class PersistConfig {
	private final OrderStatemachinePersist orderStatemachinePersist;

	public PersistConfig(OrderStatemachinePersist orderStatemachinePersist) {
		this.orderStatemachinePersist = orderStatemachinePersist;
	}

	@Bean
	public StateMachinePersister<OrderState, OrderEvent, Order> orderStateOrderEventOrderStateMachinePersister() {
		return new DefaultStateMachinePersister<>(orderStatemachinePersist);
	}
}
