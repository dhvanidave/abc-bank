package com.abc;

import java.util.Date;

public class Transaction implements Comparable<Transaction> {
	public static final String DEPOSIT = "DEPOSIT";
	public static final String WITHDRAWAL = "WITHDRAWAL";
	
    public final double amount;
    private final Date transactionDate;
	private long nano;
    private String type;

    public Transaction( double amount, Date date ) {
        this.amount = amount;
        setType();
        this.transactionDate = date;
        this.nano = System.nanoTime();
    }
    
    public Date getTransactionDate() {
		return transactionDate;
	}

    public String getType() {
    	return this.type;
    }
    
	private void setType() {
		this.type = this.amount < 0 ? WITHDRAWAL : DEPOSIT;
	}

	public int compareTo(Transaction o) {
		return new Long( this.nano ).compareTo( o.nano );
	}

}
