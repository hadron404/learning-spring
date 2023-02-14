package com.example.statemachine.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
@ToString
@Setter
public class OrderRefundCost {

	private RefundCash byGoods;

	private RefundCash byFreight;

	private RefundCash byPrepaid;

	private static final RefundCash ZERO = new RefundCash(new BigDecimal(0));

	public OrderRefundCost(RefundCash byGoods, RefundCash byFreight) {
		this(byGoods, byFreight, ZERO);
	}

	public OrderRefundCost() {
		this(ZERO, ZERO, ZERO);
	}

	public boolean isRefundCashGreaterZero() {
		return this.byGoods.value.compareTo(ZERO.value) > 0
			|| this.byFreight.value.compareTo(ZERO.value) > 0
			|| this.byPrepaid.value.compareTo(ZERO.value) > 0;
	}

	public record RefundCash(BigDecimal value) {
		public RefundCash {
			value = value.setScale(4, RoundingMode.HALF_UP);
		}
	}
}
