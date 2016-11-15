package com.abc;

import java.util.Date;
import java.util.SortedSet;

public interface Account {
	
	public void deposit( double amount, Date date );
	
	public void withdraw( double amount, Date date ) throws ServiceException;
	
	public String getStatement();
	
	public double getBalance();
	
	public double interestEarned();
	
	public int getAccountNumber();
	
	public SortedSet<Transaction> getTransactions();
	
}
