package com.example.statemachine.config;

import com.example.statemachine.domain.Order;
import com.example.statemachine.service.OrderState;
import com.example.statemachine.service.OrderUseCase;
import org.springframework.statemachine.annotation.EventHeader;
import org.springframework.statemachine.annotation.WithStateMachine;

@SuppressWarnings("unused")
@WithStateMachine(name = OrderStatemachine.MACHINE_ID)
public class OrderActionConfig {

	@StatesOnTransition(source = OrderState.UN_CREATE, target = OrderState.WAITING_PICK)
	public void receive(
		@EventHeader("command") OrderUseCase.OrderCommand command) {
		System.out.println("执行创建订单" + command);
	}

	@StatesOnTransition(source = OrderState.WAITING_PICK, target = OrderState.HANDLING)
	public void receive(
		@EventHeader("command") OrderUseCase.OrderCommand command,
		@EventHeader("order") Order order) {
		System.out.println("执行领取订单，传递参数：" + command);
		System.out.println("执行领取订单，传递参数：" + order);
	}
}
