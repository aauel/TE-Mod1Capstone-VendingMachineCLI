package com.techelevator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.model.VendingMachine;
import com.techelevator.view.Menu;

public class VendingMachineCLI {
	
	private static final String INVENTORY_FILE_NAME = "VendingMachine.txt";
	
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Items";
	private static final String MAIN_MENU_OPTION_PURCHASE_ITEMS = "Purchase Items";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_OWNER_MENU = "Owner Menu";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE_ITEMS,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_OWNER_MENU };
	
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	
	private static final String OWNER_MENU_OPTION_GET_SALES_REPORT = "Get Sales Report";
	private static final String OWNER_MENU_OPTION_EXIT = "Return to main menu";
	private static final String[] OWNER_MENU_OPTIONS = { OWNER_MENU_OPTION_GET_SALES_REPORT, OWNER_MENU_OPTION_EXIT };

	private Menu menu;
	private VendingMachine vendingMachine;
	private Scanner input;
	
	/**
	 * Creates new Menu object to process menu options, then passes to VendingMachineCLI constructor.
	 * @param args
	 */
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli;
		cli = new VendingMachineCLI(menu);
		cli.run();
	}
	
	/**
	 * Creates new Vending Machine and initializes new Scanner for user I/O.
	 * @param menu
	 */
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		vendingMachine = new VendingMachine();
		input = new Scanner(System.in); 
	}

	/**
	 * 	When not able to read inventory file to initalize items in vending machine, 
	 * prints message that the machine is out of order and quits program.
	 * 
	 * Otherwise, proceeds with Vending Machine procedures.
	 */
	public void run() {
		try {
			vendingMachine.fillSlotsWithItems(INVENTORY_FILE_NAME);
		} catch (FileNotFoundException e) {
			System.out.println("This vending machine is out of order. We apologize for the inconvenience.");
		}
		System.out.println("Welcome to Vendo-Matic 600!");
		boolean sessionComplete = false;
		while (sessionComplete == false) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			/*		1) Display Items
			 		2) Purchase Items
					3) Exit
					4) ** HIDDEN** Owner Menu
			 */
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayItems();
				
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE_ITEMS)) {
				purchaseItems();
				
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thanks for using Vendo-Matic 600 by Umbrella Corp.\nHave a nice day!");
				sessionComplete = true;

			} else if (choice.equals(MAIN_MENU_OPTION_OWNER_MENU)) {
				ownerOptions();
			}
		}
	}

	/* Prints to console a list of each slot in the machine, with the price, item and current quantity.
	 * If item is sold out, the name will be replaced with SOLD OUT
	 *  	#     Price     Name                  Qty
			=======================
			A1   $3.05   Potato Crisps       5
			....
	 */
	private void displayItems() {
		System.out.println(vendingMachine.printInventory());
		System.out.println("-------------------------------------------------------------");
	}
	
	/* Displays new menu and handles each option.
	 			1) Feed Money
				2) Select Product
				3) Finish Transaction
	 */
	private void purchaseItems() {
		System.out.println("-------------------------------------------------------------");
		boolean transactionComplete = false;
		while (transactionComplete == false) {
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			System.out.println("-------------------------------------------------------------");
			if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				feedMoney();
			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				productSelection();
			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				System.out.println(vendingMachine.finishTransaction());
				transactionComplete = true;
				System.out.println("-------------------------------------------------------------");
			}
		}
	}
	
	/*
	 * Processes user input to "deposit money" to the machine.
	 * Handles exception if user input cannot be parsed to a BigDecimal.
	 */
	private void feedMoney() {
		System.out.println("How much would you like to deposit?");
		String userInput = input.nextLine();
		try {
			BigDecimal amountToDeposit = new BigDecimal(userInput);
			System.out.println(vendingMachine.depositMoney(amountToDeposit));
		} catch (NumberFormatException e) {
			System.out.println("Please enter a numerical, whole dollar amount");
		}
		System.out.println("-------------------------------------------------------------");
	}
	
	/*
	 * Accepts user input indicating the slot ID for the item to purchase.
	 */
	private void productSelection() {
		System.out.println(vendingMachine.printInventory());
		System.out.println("Enter the number of your selection: ");
		String slotID = input.nextLine();
		System.out.println(vendingMachine.purchaseItem(slotID.toUpperCase()));
		System.out.println("-------------------------------------------------------------");
	}
	
	/* Displays owner menu and handles each option.
			1) Get Sales Report
			2) Return to main menu
	*/
	private void ownerOptions() {
		String ownerChoice = (String) menu.getChoiceFromOptions(OWNER_MENU_OPTIONS);
		if (ownerChoice.equals(OWNER_MENU_OPTION_GET_SALES_REPORT)) {
			try {
				System.out.println(vendingMachine.createSalesReport());
			} catch (FileNotFoundException e) {
				System.out.println("Not able to create new sales report.");
			}
		} else {
			// returns to Main Menu
		}
	}
	
}
