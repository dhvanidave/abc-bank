package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class MaxiSavingsAccountTest {

	private MaxiSavingsAccount a;
	
	@Before
	public void setUp() {
		this.a = new MaxiSavingsAccount();
	}
	
	@Test
	public void calculateInterest() throws ServiceException {
		a.deposit( 900d, getDate( 2016, Calendar.NOVEMBER, 2 ) );
		assertEquals( 900d * 0.05, a.calculateInterest( getDate( 2016, Calendar.NOVEMBER, 13 ) ), 0.01 );
		
		a.withdraw( 100d, getDate( 2016, Calendar.NOVEMBER, 2 ) );
		assertEquals( ( 900d - 100d ) * 0.05, a.calculateInterest( getDate( 2016, Calendar.NOVEMBER, 13 ) ), 0.01 );
		
		a.withdraw( 300d, getDate( 2016, Calendar.NOVEMBER, 4 ) );
		assertEquals( ( 900d - 100d - 300d ) * 0.001, a.calculateInterest( getDate( 2016, Calendar.NOVEMBER, 13 ) ), 0.01 );
	}
	
	@Test
	public void getDate10DaysPrior() {
		Date date =  a.getDate10DaysPrior( getDate( 2016, Calendar.NOVEMBER, 13 ) );
		
		SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyy" );
		assertEquals( "11/03/2016", dateFormat.format( date ) );
	}
	
	@Test
	public void containsWithdrawalsWithinLast10Days() throws ServiceException {
		assertFalse( a.containsWithdrawalsWithinLast10Days( getDate( 2016, Calendar.NOVEMBER, 4 ) ) );
		
		a.deposit( 1000d, getDate( 2016, Calendar.NOVEMBER, 2 ) );
		assertFalse( a.containsWithdrawalsWithinLast10Days( getDate( 2016, Calendar.NOVEMBER, 13 ) ) );
		
		a.withdraw( 100d, getDate( 2016, Calendar.NOVEMBER, 2 ) );
		assertFalse( a.containsWithdrawalsWithinLast10Days( getDate( 2016, Calendar.NOVEMBER, 13 ) ) );
		
		a.deposit( 100d, getDate( 2016, Calendar.NOVEMBER, 3 ) );
		assertFalse( a.containsWithdrawalsWithinLast10Days( getDate( 2016, Calendar.NOVEMBER, 13 ) ) );

		a.withdraw( 100d, getDate( 2016, Calendar.NOVEMBER, 3 ) );
		assertTrue( a.containsWithdrawalsWithinLast10Days( getDate( 2016, Calendar.NOVEMBER, 13 ) ) );
	}
	
	private Date getDate( int year, int month, int dayOfMonth ) {
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.YEAR, year );
		cal.set( Calendar.MONTH, month );
		cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );
		return cal.getTime();
	}
}
