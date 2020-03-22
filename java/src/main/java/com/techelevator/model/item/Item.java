package com.techelevator.model.item;

import java.math.BigDecimal;

public abstract class Item {

	private String name;
	private BigDecimal price;

	public Item(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public abstract String getMessage();

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		String output = String.format("%-7s%-18s", getPrice(), getName());
		return output;
	}
}
