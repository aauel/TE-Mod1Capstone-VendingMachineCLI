package com.techelevator.model.item;

import java.math.BigDecimal;

public class GumItem extends Item {

	public GumItem(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public String getMessage() {
		return "Chew Chew, Yum!";

	}



}
