package com.abc;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.SortedSet;

public abstract class AbstractAccount {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyy" );
	
	public void deposit( double amount, Date date ) {
		transactions.add( new Transaction( amount, date ));
	}

	public void withdraw( double amount, Date date ) throws ServiceException {
		if( getBalance() < amount ) {
			throw new ServiceException( "Insufficient funds" );
		}
		transactions.add( new Transaction( -amount, date ) );
	}
	
	public double getBalance() {
		return getTransactionsAmount() + getInterest();
	}
	
	protected double getTransactionsAmount() {
		double amount = 0d;
		for (Transaction t : transactions)
			amount += t.amount;
		
		return amount;
	}
	
	public abstract double getInterest();
	
	
	protected int generateAccountNumber() {
		return new Random().nextInt( Integer.MAX_VALUE ) + 1;
	}
	
	public String getStatement() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Account: " + this.accountNumber + "\n" );
        sb.append( getStatementDetails() );
        sb.append( "Balance: " + getAmountAsString( getBalance() ) + "\n\n" );
        return sb.toString();
	}
	
	protected String getStatementDetails() {
		if( transactions.size() == 0 ) {
			return "No Activity";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append( "Transactions:" + "\n" );
		for( Transaction t : transactions )
			sb.append( "Date: " + getDateString( t.getTransactionDate() ) + ", Type: " + t.getType() + ", Amount: " + getAmountAsString( Math.abs( t.amount ) ) + "\n" );
		
		return sb.toString();
	}
	
	private String getDateString( Date date ) {
    	return dateFormat.format( date );
    }
	
	private String getAmountAsString( double amount ) {
		return NumberFormat.getCurrencyInstance().format( amount );
	}
	
	protected int accountNumber;
	public int getAccountNumber() { return accountNumber; };
	public void setAccountNumber( int accountNumber ) { this.accountNumber = accountNumber; }

	protected SortedSet<Transaction> transactions;
	public SortedSet<Transaction> getTransactions() { return transactions; }
	public void setTransactions(SortedSet<Transaction> transactions) { this.transactions = transactions; }

	

}
