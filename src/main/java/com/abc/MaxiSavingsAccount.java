package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

public class MaxiSavingsAccount extends AbstractAccount implements Account {

	public MaxiSavingsAccount() {
		this.accountNumber = generateAccountNumber();
		this.transactions = new TreeSet<Transaction>();
	}
	
//	public double interestEarned() {
//		double amount = getTransactionsAmount();
//		
//		if (amount <= 1000)
//			return amount * 0.02;
//		if (amount <= 2000)
//			return ( 1000d * 0.02 ) + ( (amount - 1000d) * 0.05 );
//		return ( 1000d * 0.02 ) + ( 1000d * 0.05 ) + ( ( amount - 2000d ) * 0.1 );
//	}
	
	public double interestEarned() {
		return calculateInterest( new Date() );
	}
	
	protected double calculateInterest( Date date ) {
		double amount = getTransactionsAmount();
		return containsWithdrawalsWithinLast10Days( new Date() ) ? ( amount * .001 ) : ( amount * .05 );
	}
	
	protected boolean containsWithdrawalsWithinLast10Days( Date date ) {
		long minDate = getDate10DaysPrior( date ).getTime();
		for( Transaction t : transactions ) {
			if( t.getTransactionDate().getTime() >= minDate && t.getType().equals( Transaction.WITHDRAWAL ) ) {
				return true;
			}
		}
		return false;
	}
	
	protected Date getDate10DaysPrior( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime( date );
		cal.add( Calendar.DAY_OF_MONTH, -10 );
		return cal.getTime();
	}
	
	
	public double getInterest() {
		return interestEarned();
	}
}
