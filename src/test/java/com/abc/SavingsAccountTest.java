package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class SavingsAccountTest {

	@Test
	public void interestEarned() {
		SavingsAccount a = new SavingsAccount();
		
		a.deposit( 900d, new Date() );
		assertEquals( 900d * 0.001, a.interestEarned(), 0.01 );
		
		a.deposit( 500d, new Date() );
		double interest = ( 1000 * 0.001 ) + ( ( 900d + 500d - 1000d ) * 0.002 );
		assertEquals( interest, a.interestEarned(), 0.01 );
	}
}
