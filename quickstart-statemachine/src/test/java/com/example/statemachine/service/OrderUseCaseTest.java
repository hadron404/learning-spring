package com.example.statemachine.service;

import com.example.statemachine.domain.Operator;
import com.example.statemachine.domain.Order;
import com.example.statemachine.persistence.OrderPersistenceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachineEventResult;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderUseCaseTest {
	@Autowired
	private OrderUseCase orderUseCase;

	@MockBean
	private OrderPersistenceAdapter orderPersistenceAdapter;

	private final Operator operator = new Operator(0, "1");

	@Test
	void givenOrderInfo_whenCreate_thenExecuteCreateNewOrderAndOrderStateChangeToWaitingPick() {
		StepVerifier
			.create(orderUseCase.transit(new OrderUseCase.OrderCommand(OrderEvent.CREATE, operator)))
			.expectNextMatches(next -> {
				boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
				boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.WAITING_PICK;
				return isAcceptEvent && isChangeStateCurrently;
			})
			.expectComplete()
			.verify();
	}

	@Test
	void givenWaitingPickOrder_whenPick_thenExecutePickAndOrderStateChangeToHandling() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.WAITING_PICK)
		);
		StepVerifier
			.create(orderUseCase.transit(
				new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.PICK, operator)))
			.expectNextMatches(next -> {
				boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
				boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.HANDLING;
				return isAcceptEvent && isChangeStateCurrently;
			})
			.expectComplete()
			.verify();
	}

	@Test
	void givenHandlingOrder_whenPick_thenNothingHappen() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.HANDLING)
		);
		StepVerifier
			.create(orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.PICK, operator)))
			.expectNextMatches(next -> next.getResultType() == StateMachineEventResult.ResultType.DENIED)
			.expectComplete()
			.verify();
	}

	@Test
	void givenHandlingOrder_whenSubmit_thenExecuteSubmitAndOrderStateChangeToWaitingFinish() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.HANDLING)
		);
		StepVerifier
			.create(orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.SUBMIT, operator)))
			.expectNextMatches(next -> {
				boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
				boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.WAITING_FINISH;
				return isAcceptEvent && isChangeStateCurrently;
			})
			.expectComplete()
			.verify();
	}

	@Test
	void givenWaitingFinishOrder_whenFinish_thenExecuteFinishAndOrderStateChangeToFinished() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.WAITING_FINISH)
		);
		StepVerifier
			.create(orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.FINISH, operator)))
			.expectNextMatches(next -> {
				boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
				boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.FINISHED;
				return isAcceptEvent && isChangeStateCurrently;
			})
			.expectComplete()
			.verify();
	}

	@Test
	void givenSomeDifferentStateOrder_WhenSynchronizedExecute_thenAnyOrderChangeStateCurrently() {
		// for (int i = 0; i < 10; i++) {
		// 	Thread thread = new Thread(
		// 		() -> {
		// 			when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
		// 				new Order(new Order.OrderId(1), OrderState.WAITING_FINISH)
		// 			);
		// 			StepVerifier
		// 				.create(orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.FINISH, operator)))
		// 				.expectNextMatches(next -> {
		// 					boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
		// 					boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.FINISHED;
		// 					return isAcceptEvent && isChangeStateCurrently;
		// 				})
		// 				.expectComplete()
		// 				.verify();
		// 		}
		// 	);
		// 	thread.start();
		// }

		// IntStream.range(0, 10)
		// 	.parallel()
		// 	.forEach((o) -> {
		// 		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
		// 			new Order(new Order.OrderId(1), OrderState.WAITING_FINISH)
		// 		);
		// 		StepVerifier
		// 			.create(orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.FINISH, operator)))
		// 			.expectNextMatches(next -> {
		// 				boolean isAcceptEvent = next.getResultType() == StateMachineEventResult.ResultType.ACCEPTED;
		// 				boolean isChangeStateCurrently = next.getRegion().getState().getId() == OrderState.FINISHED;
		// 				return isAcceptEvent && isChangeStateCurrently;
		// 			})
		// 			.expectComplete()
		// 			.verify();
		// 	});
	}
}

