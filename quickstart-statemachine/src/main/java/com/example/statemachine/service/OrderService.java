package com.example.statemachine.service;

import com.example.statemachine.domain.Order;
import com.example.statemachine.persistence.OrderPersistenceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService implements OrderUseCase {
	private final StateMachine<OrderState, OrderEvent> stateMachine;
	private final StateMachinePersister<OrderState, OrderEvent, Order> orderMemoryPersister;
	private final OrderPersistenceAdapter orderPersistenceAdapter;

	@Override
	public void transit(OrderCommand command) {
		if (command.id() == null) {
			this.transitIfOrderIsNotExist(command);
		} else {
			this.transitIfOrderExisted(command);
		}
	}

	private void transitIfOrderIsNotExist(OrderCommand command) {
		stateMachine.startReactively().subscribe();
		Mono<Message<OrderEvent>> msg =
			Mono.just(
				MessageBuilder
					.withPayload(OrderEvent.CREATE)
					.setHeader("command", command)
					.build()
			);
		stateMachine.sendEvent(msg).subscribe();
	}

	private void transitIfOrderExisted(OrderCommand command) {
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
		stateMachine.sendEvent(msg).subscribe();
	}
}
