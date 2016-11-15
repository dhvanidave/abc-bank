package com.abc;

import java.util.TreeSet;

public class SavingsAccount extends AbstractAccount implements Account {
	
	public SavingsAccount() {
		this.accountNumber = generateAccountNumber();
		this.transactions = new TreeSet<Transaction>();
	}
	
	public double interestEarned() {
		double amount = getTransactionsAmount();
		if (amount <= 1000)
			return amount * 0.001;
		else
			return ( 1000d * 0.001 ) + ( (amount - 1000) * 0.002 );
	}
	
	public double getInterest() {
		return interestEarned();
	}
}
