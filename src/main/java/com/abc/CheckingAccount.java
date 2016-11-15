package com.abc;

import java.util.TreeSet;

public class CheckingAccount extends AbstractAccount implements Account {

	public CheckingAccount() {
		this.accountNumber = generateAccountNumber();
		this.transactions = new TreeSet<Transaction>();
	}

	public double interestEarned() {
		double amount = getTransactionsAmount();
		return amount * 0.001;
	}
	
	public double getInterest() {
		return interestEarned();
	}
}
