package com.example.statemachine.service;

import com.example.statemachine.domain.Operator;
import com.example.statemachine.domain.Order;
import com.example.statemachine.persistence.OrderPersistenceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
		orderUseCase.transit(new OrderUseCase.OrderCommand(OrderEvent.CREATE, operator));
	}

	@Test
	void givenWaitingPickOrder_whenPick_thenExecutePickAndOrderStateChangeToHandling() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.WAITING_PICK)
		);
		orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.PICK, operator));
	}

	@Test
	void givenHandlingOrder_whenPick_thenNothingHappen() {
		when(orderPersistenceAdapter.loadOrder(any())).thenReturn(
			new Order(new Order.OrderId(1), OrderState.HANDLING)
		);
		orderUseCase.transit(new OrderUseCase.OrderCommand(new Order.OrderId(1), OrderEvent.PICK, operator));
	}
}
