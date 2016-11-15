package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	
	@Test
	public void getType() {
		Transaction t = new Transaction( 1000d, new Date() );
		assertEquals( Transaction.DEPOSIT, t.getType() );
		
		t = new Transaction( -1000d, new Date() );
		assertEquals( Transaction.WITHDRAWAL, t.getType() );
	}
}
