package com.techelevator.model.item;

import java.math.BigDecimal;

public class ChipsItem extends Item {

	public ChipsItem(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public String getMessage() {
		return "Crunch Crunch, Yum!";
	}

}
