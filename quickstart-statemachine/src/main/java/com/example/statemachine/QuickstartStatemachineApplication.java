package com.example.statemachine;

import com.example.statemachine.domain.Order;
import com.example.statemachine.service.OrderEvent;
import com.example.statemachine.service.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import reactor.core.publisher.Mono;

@SpringBootApplication
@AllArgsConstructor
public class QuickstartStatemachineApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(QuickstartStatemachineApplication.class, args);
	}

	private final StateMachine<OrderState, OrderEvent> stateMachine;

	private final StateMachinePersister<OrderState, OrderEvent, Order> orderMemoryPersister;

	@Override
	public void run(String... args) throws Exception {
		// stateMachine.startReactively().subscribe();
		Order order = new Order(new Order.OrderId(1), OrderState.HANDLING);
		orderMemoryPersister.restore(stateMachine, order);
		Mono<Message<OrderEvent>> create = Mono.just(MessageBuilder.withPayload(OrderEvent.CREATE).build());
		Mono<Message<OrderEvent>> pick = Mono.just(MessageBuilder.withPayload(OrderEvent.PICK).build());
		Mono<Message<OrderEvent>> submit = Mono.just(MessageBuilder.withPayload(OrderEvent.SUBMIT).build());
		Mono<Message<OrderEvent>> finish = Mono.just(MessageBuilder.withPayload(OrderEvent.FINISH).build());
		// stateMachine.sendEvent(create).subscribe();
		// stateMachine.sendEvent(pick).subscribe();
		stateMachine.sendEvent(submit).subscribe();
		// stateMachine.sendEvent(finish).subscribe();
	}
}
