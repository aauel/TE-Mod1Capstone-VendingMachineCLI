package com.techelevator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Log {
	
	private Queue<Transaction> transactions;

	public Log() {
		transactions = new LinkedList<Transaction>();
	}
	
	public void addDepositTransaction(BigDecimal amountDeposited, BigDecimal currentBalance) {
		String transactionType = "FEED MONEY";
		transactions.add(new Transaction(transactionType, amountDeposited, currentBalance));
	}
	public void addPurchaseTransaction(String itemName, String slotID, BigDecimal itemPrice, BigDecimal currentBalance) {
		String transactionType = itemName + " " + slotID;
		transactions.add(new Transaction(transactionType,  itemPrice, currentBalance));
	}
	public void addChangerTransaction(BigDecimal currentBalance) {
		String transactionType = "GIVE CHANGE";
		transactions.add(new Transaction(transactionType, currentBalance, BigDecimal.ZERO));
	}

	public void printTransactionsToFile(String logFileName) {
		File logFile = new File(logFileName);
		// if file doens't exist, create one
		if (logFile.exists() == false) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Couldn't create the log file.");
			}
		}
		// append transactions to running log file
		try (PrintWriter logWriter = new PrintWriter(new FileOutputStream(logFile, true))) {
			while (transactions.peek() != null) {
				logWriter.println(transactions.remove());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't print to the log file.");
		} 
	}
}
