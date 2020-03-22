package com.techelevator.model;

import java.math.BigDecimal;
import java.util.Stack;

import com.techelevator.model.item.Item;

public class Slot {

	private Stack<Item> slotItems;    // Item = { String name, BigDecimal price }
	
	public Slot() {
		slotItems = new Stack<Item>();   // implementing as a Stack to easily remove one with purchase
	}
//
//	public Stack<Item> getSlotItems() {
//		return slotItems;
//	}
	public String getItemNameInSlot() {
		// return "SOLD OUT" if there are 0 items remaining
		return isSlotEmpty() ? "SOLD OUT" : slotItems.get(0).getName();
	}
	public BigDecimal getItemPriceInSlot() {
		// return ZERO if there are 0 items remaining
		return isSlotEmpty() ? BigDecimal.ZERO : slotItems.get(0).getPrice();
	}
	public String getItemMessageInSlot() {
		// return "" if there are 0 items remaining
		return isSlotEmpty() ? "" : slotItems.get(0).getMessage();
	}
	public int getSlotItemQuantity() {
		return slotItems.size();
	}
	public boolean isSlotEmpty() {
		return slotItems.size() == 0;
	}
	public void addItems(Item newItem, int quantity) {
		for (int i = 0; i < quantity; i++) {
			slotItems.add(newItem);
		}
	}
	public Item removeItem() {
		return slotItems.pop();
	}
	
	@Override
	public String toString() {
		return String.format("$%-7.2f%-20s%d", getItemPriceInSlot(), getItemNameInSlot(), getSlotItemQuantity());
	}
	
}
