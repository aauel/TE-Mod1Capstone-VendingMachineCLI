package com.techelevator.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

	private final String type;
	private final BigDecimal amount;
	private final BigDecimal balance;
	private final LocalDateTime timestamp;
	
	public Transaction(String type, BigDecimal amount, BigDecimal currentBalance) {
		this.type = type;
		this.amount = amount;
		this.balance = currentBalance;
		this.timestamp = LocalDateTime.now();
	}

	public String getType() {
		return type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public LocalDateTime gettimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		String stringDate = timestamp.format(formatter);
		return String.format("%s %s $%.2f $%.2f", stringDate, type, amount, balance);
	}
	
}
