package com.techelevator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.model.item.CandyItem;
import com.techelevator.model.item.ChipsItem;
import com.techelevator.model.item.DrinkItem;
import com.techelevator.model.item.GumItem;
import com.techelevator.model.item.Item;

public class VendingMachine {
	
	private static final List<String> SLOT_IDS = 
											Arrays.asList( 	new String[] { "A1", "A2", "A3", "A4",
																						   "B1", "B2", "B3", "B4",
																						   "C1", "C2", "C3", "C4",
																						   "D1", "D2", "D3", "D4"
																						   });
	private static final int STARTING_ITEM_QUANTITY = 5;
	private static final BigDecimal MAX_BALANCE_ALLOWED = new BigDecimal(100);
	private static final String LOG_FILE_NAME = "Log.txt";
	private static final String SALES_FILE_NAME = "SalesReport.txt";
	
	private Map<String, Slot> slots;   // String = slot identifier, Slot = { List<Item> slotItems}
	private Map<String, Integer> itemSales;   // for generating the sales report
	private Log transactionLog;
	private Changer changer;
	private BigDecimal currentBalance;
	private BigDecimal totalSales;

	public VendingMachine() {
		slots = new LinkedHashMap<String, Slot>();
		// create empty slots for each ID
		for (String id : SLOT_IDS) {
			slots.put(id, null);
		}
		itemSales = new LinkedHashMap<String, Integer>();
		transactionLog = new Log();
		changer = new Changer();
		currentBalance = BigDecimal.ZERO;
		totalSales = BigDecimal.ZERO;
	}

	/**
	 * Add Items to each slot according to inventory provided in file.
	 * Each slot should be filled initially with quantity 5 of that item.
	 * @param String filePath
	 * 			FORMAT:  one line for each slot  "ID|itemName|price|itemType"
	 * @throws FileNotFoundException if not able to access inventory file
	 */
	public void fillSlotsWithItems(String filePath) throws FileNotFoundException {
		// get inventory from file provided (ex. VendingMachine.txt)
		try (Scanner scanner = new Scanner(new File(filePath))) {
			while (scanner.hasNextLine()) {
				// each line represents a slot and item properties
				String line = scanner.nextLine();				// (ex.  A1|Potato Crisps|3.05|Chip)
				String[] slotAndItem = line.split("\\|");    // ex. ["A1", "Potato Crisps", "3.05", "Chip"]
				
				// handle creating items according to type, with name and price
				Item newItem = createItem(slotAndItem);  
				Slot newSlot = new Slot();	
				newSlot.addItems(newItem, STARTING_ITEM_QUANTITY);   // add 5 of Item to slot
				String currentSlotID = slotAndItem[0];      	// ex. "A1"
				slots.put(currentSlotID, newSlot);					// add new Slot to vending machine map with ID
			}
		}
	}
	
	/**
	 * Generates an instance of Item object according to type (Chip, Drink, Candy, Gum)
	 * @param String[] itemProperties [slotID, name, price, type]
	 * @return Item newItem
	 */
	private Item createItem(String[] itemProperties) {
		String itemName = itemProperties[1];
		BigDecimal itemPrice = new BigDecimal(itemProperties[2]);
		String itemType = itemProperties[3];
		
		Item newItem = null;
		if (itemType.equals("Chip")) {
			newItem = new ChipsItem(itemName, itemPrice);
		} else if (itemType.equals("Drink")) {
			newItem = new DrinkItem(itemName, itemPrice);
		} else if (itemType.equals("Candy")) {
			newItem = new CandyItem(itemName, itemPrice);
		} else if (itemType.equals("Gum")) {
			newItem = new GumItem(itemName, itemPrice);
		}
		// add item to itemSales map with initial quantity of 0, and return Item
		itemSales.put(newItem.getName(), 0);
		return newItem;
	}

	/**
	 * Generate multi-line String consisting of the current inventory for each
	 * slot of the machine, including slot #, item price, name and quantity.
	 * @return String printString
	 */
	public String printInventory() {
		String printString = String.format("\n%-4s%-8s%-20s%s\n", "#", "Price", "Name","Qty");
		printString += "===================================\n";
		for (String slotId : slots.keySet()) {
			Slot s = slots.get(slotId);
			printString += (slotId + "  " + s.toString() + "\n");
		}
		return printString;
	}

	/**
	 * Accepts a BigDecimal parameter representing the amount of money to be
	 * deposited, and validates the amount is a whole dollar value between $1 and $100.
	 * @param BigDecimal amountToDeposit
	 * @return String returnMessage provides error details or confirmation of deposit.
	 */
	public String depositMoney(BigDecimal amountToDeposit) {
		double dAmount = amountToDeposit.doubleValue(); // converting to double for easier comparisons
		String returnMessage = "";
		if (dAmount % 1 > 0) {
			returnMessage = "Please enter a whole dollar amount";			
		} else if (dAmount <= 0) {
			returnMessage = "Please enter a positive dollar amount";
		} else if (currentBalance.add(amountToDeposit).compareTo(MAX_BALANCE_ALLOWED) > 0) {
			returnMessage = "Max balance allowed is $100.";
		} else {
			returnMessage = handleValidDeposit(amountToDeposit);
		}
		return returnMessage;
	}
	
	/**
	 * Helper method to depositMoney() to add deposit to current balance, and log transaction.
	 * Return message includes amount deposited, as well as new balance if the previous balance
	 * was over zero.
	 * @param BigDecimal amountToDeposit passed from depositMoney()
	 * @return String returnMessage
	 */
	private String handleValidDeposit(BigDecimal amountToDeposit) {
		boolean previousBalanceZero  = currentBalance.equals(BigDecimal.ZERO);
		String returnMessage = "";
		// add deposit amount to the current balance, then log deposit transaction
		currentBalance = currentBalance.add(amountToDeposit);
		transactionLog.addDepositTransaction(amountToDeposit, currentBalance);
		// generate successful deposit message
		returnMessage = String.format("You deposited $%.2f\n", amountToDeposit);
		// if there was a previous balance, include new balance in message
		if (previousBalanceZero == false) {
			returnMessage += String.format("Your new balance is $%.2f\n", currentBalance);
		}
		return returnMessage;
	}

	/**
	 * @return true if currentBalance is greater than zero, otherwise false
	 */
	public boolean balanceIsPositive() {
		return currentBalance.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * Validates the slot number as it was entered by the user to confirm it is a valid slot ID.
	 * If valid, and the slot is not empty, and the price is not greater than the current balance,
	 * then the Item is removed from the chosen Slot, and passed to handleValidPurchase().
	 * @param String slotID
	 * @return String returnMessage provides error details or confirmation of purchase.
	 */
	public String purchaseItem(String slotID) {
		String returnMessage = "";
		if (SLOT_IDS.contains(slotID) == false) {
			returnMessage = "Please enter a valid slot number for your selection.";
		} else {
			Slot chosenSlot = slots.get(slotID);
			if (chosenSlot.isSlotEmpty()) {
				returnMessage = "The item you selected is sold out.";
			} else {
				BigDecimal price = chosenSlot.getItemPriceInSlot();
				if (currentBalance.compareTo(price) < 0) {
					returnMessage = "You don't have sufficient funds. Please deposit money.";
				} else {
					Item chosenItem = chosenSlot.removeItem();
					updateItemsSales(chosenItem);
					returnMessage = handleValidPurchase(slotID, chosenItem, price);
				}
			}
		}
		return returnMessage;
	}
	
	/**
	 * Increments the quantity sold in the itemSales map, by itemName, and also
	 * adds itemPrice to the totalSales amount.
	 * @param Item chosenItem
	 */
	private void updateItemsSales(Item chosenItem) {
		String itemName = chosenItem.getName();
		BigDecimal itemPrice = chosenItem.getPrice();
		int qtySold = itemSales.get(itemName);
		itemSales.put(itemName, qtySold + 1);
		totalSales = totalSales.add(itemPrice);
	}
	
	/**
	 * Helper method to purchaseItem() to adjust balance, add a log of
	 * the purchase to transactions, and supply purchase confirmation message
	 * @param String slotID for the transaction log
	 * @param Item chosenItem for item name and message
	 * @param BigDecimal price to adjust balances
	 * @return String returnMessage
	 */
	private String handleValidPurchase(String slotID, Item chosenItem, BigDecimal price) {
		// adjust balance to account for purchase
		currentBalance = currentBalance.subtract(price);
		// create transaction log
		String itemName = chosenItem.getName();
		transactionLog.addPurchaseTransaction(itemName, slotID, price, currentBalance);
		// provide purchase confirmation and updated balance
		String returnMessage = "";
		returnMessage = String.format("You selected %s!\n%s\n", itemName, chosenItem.getMessage());
		returnMessage += String.format("Your new balance is $%.2f", currentBalance);
		return returnMessage;
	}

	/**
	 * Gets change from the currentBalance, adds to the transaction log,
	 * and returns a message with the change by denomination.
	 */
	public String finishTransaction() {
		String returnMessage = "";
		if (currentBalance.equals(BigDecimal.ZERO)) {
			returnMessage = "Balance was $0.  No change was dispensed";
		} else {
			returnMessage = getChange(currentBalance);
		}
		if (balanceIsPositive()) {
			transactionLog.addChangerTransaction(currentBalance);
			currentBalance = BigDecimal.ZERO;
		}
		// append the current log of transactions to the log file
		transactionLog.printTransactionsToFile(LOG_FILE_NAME);
		return returnMessage;
	}

	/*
	 * Generate message telling user how much they've received in change, specifying the
	 * number of each denomination type. 
	 * 		Ex.  "Your change is: 3 dollar(s), 1 dime(s), 1 nickel.
	 *		   		Your balance is now $0"
	 */
	private String getChange(BigDecimal balance) {
		String returnMessage = "";
		Map<String, Integer> changeMap = changer.getChangeByDenomination(balance);
		
		// build string of non-zero denomination counts
		String changeCounts = "";
		for (String denom : changeMap.keySet()) {
			Integer denomCount = changeMap.get(denom);
			if (denomCount > 0) {
				if (changeCounts.length() > 0) changeCounts += ", ";     	// add comma and space if non-empty string
				changeCounts += denomCount + " " + denom;
			}
		}
		returnMessage = "Your change is: "+changeCounts + ".\nYour balance is now $0";
		return returnMessage;
	}
	
	/**
	 * Prints to file a list of all items and the quantity sold, as well as the total sales amount.
	 * 			FORMAT:  one line for each item  "itemName|qtySold"
	 * @return String returnMessage with confirmation of updated report
	 * @throws FileNotFoundException if not able to print to salesReport.txt
	 */
	public String createSalesReport() throws FileNotFoundException {
		String returnMessage = "";
		try (PrintWriter salesWriter = new PrintWriter(SALES_FILE_NAME)) {
			for (String itemName : itemSales.keySet()) {
				salesWriter.println(itemName + "|" + itemSales.get(itemName));
			}
			salesWriter.println(String.format("\n**TOTAL SALES** $%.2f", totalSales));
		}
		returnMessage = "Sales report is now ready for viewing.";
		return returnMessage;
	}
}
