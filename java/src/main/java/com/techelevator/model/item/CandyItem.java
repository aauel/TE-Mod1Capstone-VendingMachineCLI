package com.techelevator.model.item;

import java.math.BigDecimal;

public class CandyItem extends Item {

	public CandyItem(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public String getMessage() {
		return "Munch Munch, Yum!";
	}

}
