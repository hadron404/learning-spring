package com.example.statemachine.service;

import com.example.statemachine.domain.Order;
import com.example.statemachine.persistence.OrderPersistenceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService implements OrderUseCase {
	// private final StateMachine<OrderState, OrderEvent> stateMachine;

	private final StateMachineFactory<OrderState, OrderEvent> factory;
	private final StateMachinePersister<OrderState, OrderEvent, Order> orderMemoryPersister;
	private final OrderPersistenceAdapter orderPersistenceAdapter;

	@Override
	public Flux<StateMachineEventResult<OrderState, OrderEvent>> transit(OrderCommand command) {
		if (command.id() == null) {
			return this.transitIfOrderIsNotExist(command);
		} else {
			return this.transitIfOrderExisted(command);
		}
	}

	private Flux<StateMachineEventResult<OrderState, OrderEvent>> transitIfOrderIsNotExist(OrderCommand command) {
		StateMachine<OrderState, OrderEvent> stateMachine = factory.getStateMachine("orderStatemachine");
		Mono<Message<OrderEvent>> msg = Mono.just(command)
			.map((cmd) -> MessageBuilder
				.withPayload(OrderEvent.CREATE)
				.setHeader("command", cmd)
				.build()
			);
		return stateMachine.sendEvent(msg);
	}

	private Flux<StateMachineEventResult<OrderState, OrderEvent>> transitIfOrderExisted(OrderCommand command) {
		StateMachine<OrderState, OrderEvent> stateMachine = factory.getStateMachine("orderStatemachine");
		Order order = orderPersistenceAdapter.loadOrder(command.id());
		try {
			orderMemoryPersister.restore(stateMachine, order);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Mono<Message<OrderEvent>> msg = Mono.just(
			MessageBuilder
				.withPayload(command.event())
				.setHeader("command", command)
				.setHeader("order", order)
				.build()
		);
		return stateMachine.sendEvent(msg);
	}
}
