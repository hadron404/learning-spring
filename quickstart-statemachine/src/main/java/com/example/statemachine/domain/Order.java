package com.example.statemachine.domain;

import com.example.statemachine.service.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Order {

	@Setter
	private OrderId id;

	@Setter
	private OrderState state;

	@Setter
	private OrderRefundCost refundCost;

	private List<Flow> flows;

	public Order(OrderId id, OrderState state) {
		this.id = id;
		this.state = state;
	}

	public boolean isReturnCashOrder() {
		return this.refundCost.isRefundCashGreaterZero();
	}

	public record OrderId(Integer value) {
	}



}
