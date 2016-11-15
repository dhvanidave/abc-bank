package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class CheckingAccountTest {

	@Test
	public void interestEarned() {
		CheckingAccount a = new CheckingAccount();
		
		a.deposit( 500d, new Date() );
		assertEquals( 500d * 0.001, a.interestEarned(), 0.01 );
		
		a.deposit( 500d, new Date() );
		assertEquals( 1000d * 0.001, a.interestEarned(), 0.01 );
	}
}
