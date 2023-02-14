package com.example.statemachine.service;


import com.example.statemachine.domain.Operator;
import com.example.statemachine.domain.Order;
import com.example.statemachine.domain.OrderRefundCost;

public interface OrderUseCase {

	Order.OrderId create(CreateOrderCommand command);

	void pick(PickOrderCommand command);


	void submit(SubmitOrderCommand command);

	void financialSubmit(FinancialOrderCommand command);

	void financialReject(FinancialOrderCommand command);

	void complete(CompleteOrderCommand command);

	record CreateOrderCommand(Operator operator,
							  OrderRefundCost.RefundCash byGoods,
							  OrderRefundCost.RefundCash byFreight) {
		public CreateOrderCommand(Operator operator) {
			this(operator, null, null);
		}
	}


	record PickOrderCommand(Order.OrderId pcikOrderId, Operator operator) {

	}

	record SubmitOrderCommand(Order.OrderId submitOrderId, Operator operator) {

	}

	record FinancialOrderCommand(Order.OrderId orderId, Operator operator) {

	}

	record CompleteOrderCommand(Order.OrderId completeOrderId, Operator operator) {

	}
}
