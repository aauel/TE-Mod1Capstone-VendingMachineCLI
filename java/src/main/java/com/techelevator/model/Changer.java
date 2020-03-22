package com.techelevator.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.Map;

// calculate denomination counts from balance and place counts in map
public class Changer {
	
	private static final BigDecimal ONE_DOLLAR = BigDecimal.ONE;
	private static final BigDecimal TWENTY_FIVE_CENTS = new BigDecimal("0.25");
	private static final BigDecimal TEN_CENTS = new BigDecimal("0.10");
	private static final BigDecimal FIVE_CENTS = new BigDecimal("0.05");

	public Map<String, Integer> getChangeByDenomination(BigDecimal balance) {

		Map<String, Integer> changeMap = new LinkedHashMap<String, Integer>();
		BigDecimal[] countAndRemer;    // to hold results of balance.divideAndRemainder() for each denomination

		countAndRemer = balance.divideAndRemainder(ONE_DOLLAR, new MathContext(0));
		changeMap.put("dollar(s)", countAndRemer[0].intValue());		// add count to changeMap
		balance = countAndRemer[1];													// subtract whole dollars from 
		
		countAndRemer = balance.divideAndRemainder(TWENTY_FIVE_CENTS, new MathContext(0));
		changeMap.put("quarter(s)", countAndRemer[0].intValue());		// add count to changeMap
		balance = countAndRemer[1];														// remainder after subtracting whole quarters

		countAndRemer = balance.divideAndRemainder(TEN_CENTS, new MathContext(0));
		changeMap.put("dime(s)", countAndRemer[0].intValue());		// add count to changeMap
		balance = countAndRemer[1];													// remainder after subtracting whole quarters
		
		countAndRemer = balance.divideAndRemainder(FIVE_CENTS, new MathContext(0));		
		changeMap.put("nickel", countAndRemer[0].intValue());			// add count to changeMap
		
		return changeMap;
	}
}
