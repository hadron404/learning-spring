package com.example.statemachine.service;

public enum OrderState {
	FINISHED,
	WAITING_FINISH,
	FINANCIAL_HANDLING,
	HANDLING,
	WAITING_PICK,
	UN_CREATE
}
