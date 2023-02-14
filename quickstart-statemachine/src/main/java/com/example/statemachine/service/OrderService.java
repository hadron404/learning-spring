package com.example.statemachine.service;

import com.example.statemachine.domain.Order;
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

	@Override
	public Order.OrderId create(CreateOrderCommand command) {
		stateMachine.startReactively().subscribe();
		Mono<Message<OrderEvent>> create = Mono.just(MessageBuilder.withPayload(OrderEvent.CREATE).build());
		stateMachine.sendEvent(create).subscribe();
		return null;
	}

	@Override
	public void pick(PickOrderCommand command) {

	}

	@Override
	public void submit(SubmitOrderCommand command) {

	}

	@Override
	public void financialSubmit(FinancialOrderCommand command) {

	}

	@Override
	public void financialReject(FinancialOrderCommand command) {

	}

	@Override
	public void complete(CompleteOrderCommand command) {

	}
}
