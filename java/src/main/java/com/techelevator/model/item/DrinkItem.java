package com.techelevator.model.item;

import java.math.BigDecimal;

public class DrinkItem extends Item {

	public DrinkItem(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public String getMessage() {
		return "Glug Glug, Yum!";

	}

}
