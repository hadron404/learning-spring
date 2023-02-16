package com.example.statemachine.persistence;


import com.example.statemachine.domain.Order;

@PersistenceAdapter
public class OrderPersistenceAdapter {

	public Order loadOrder(Order.OrderId oderId) {
		return null;
	}
}
