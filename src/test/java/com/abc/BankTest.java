package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BankTest {

	private Bank bank;
    
    @Before
	public void setUp() {
		this.bank = new Bank();
	}
    
    @Test
	public void openAccount() {
		Customer customer = new Customer( "John", "Doe", "111-11-1111" );
		
		customer = bank.openAccount( customer, new CheckingAccount() );
		assertEquals( 1, customer.getAccounts().size() );
		
		customer = bank.openAccount( customer, new SavingsAccount() );
		assertEquals( 2, customer.getAccounts().size() );
	}
    
    @Test
	public void validate() {
		Customer customer = new Customer( "John", "Doe", "123-45-6789" );
		customer.addAccount( new CheckingAccount() );
		
		Account savings = new SavingsAccount();
		try {
			bank.validate( customer, savings, -500d );
		} catch( ServiceException e ) {
			assertEquals( "Customer does not exist", e.getMessage() );
		}
		
		bank.addCustomer( customer );
		try {
			bank.validate( customer, savings, -500d );
		} catch( ServiceException e ) {
			assertEquals( "Invalid account", e.getMessage() );
		}
		
		try {
			bank.validate( customer, customer.getAccounts().get( 0 ), -500d );
			fail();
		} catch( ServiceException e ) {
			assertEquals( "Amount has to be greater than zero", e.getMessage() );
		}
		
		try {
			bank.validate( customer, customer.getAccounts().get( 0 ), 500d );
		} catch( ServiceException e ) {
			fail();
		}
	}
    
    @Test
	public void deposit() {
    	CheckingAccount a = new CheckingAccount();
		Customer customer = new Customer( "John", "Doe", "111-11-1111" );
		customer.addAccount( a );
		bank.addCustomer(customer);
		
		assertEquals( 0d, a.getBalance(), 0.01 );
		
		try {
			bank.deposit( customer, a, 1000d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		assertEquals( 1000d, a.getTransactionsAmount(), 0.01 );
		
		try {
			bank.deposit( customer, a, 500d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		assertEquals( 1500d, a.getTransactionsAmount(), 0.01 );
	}
    
    @Test
	public void withdraw() {
    	SavingsAccount a = new SavingsAccount();
		Customer customer = new Customer( "John", "Doe", "111-11-1111" );
		customer.addAccount( a );
		bank.addCustomer(customer);
		
		assertEquals( 0d, a.getBalance(), 0.01 );
		
		try {
			bank.withdraw( customer, a, 1000d, getDate() );
			fail();
		} catch (ServiceException e) {
			assertEquals( "Insufficient funds", e.getMessage() );
		}

		try {
			bank.deposit( customer, a, 500d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		assertEquals( 500d, a.getTransactionsAmount(), 0.01 );
		
		try {
			bank.withdraw( customer, a, 100d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		assertEquals( 400d, a.getTransactionsAmount(), 0.01 );
		
		try {
			bank.withdraw( customer, a, 125d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		assertEquals( 275d, a.getTransactionsAmount(), 0.01 );
	}
    
    @Test
    public void getStatement() throws ServiceException {
    	Account savings = new SavingsAccount();
    	Account checking = new CheckingAccount();
		
    	Customer customer = new Customer( "John", "Doe", "111-11-1111" );
		customer.addAccount( savings );
		customer.addAccount( checking );
		bank.addCustomer(customer);
		
		bank.deposit( customer, savings, 500d, getDate() );
		bank.deposit( customer, checking, 1000d, getDate() );
		bank.transfer( checking, savings, 100d, getDate() );
		bank.withdraw( customer, savings, 100d, getDate() );
		
		String summary = bank.getStatement( customer );
		String expected = "Statement for " + customer.getName() + "\n" +
						"Account: " + savings.getAccountNumber() + "\n" +
						"Transactions:" + "\n" +
						"Date: 11/13/2016, Type: DEPOSIT, Amount: $500.00" + "\n" + 
						"Date: 11/13/2016, Type: DEPOSIT, Amount: $100.00" + "\n" + 
						"Date: 11/13/2016, Type: WITHDRAWAL, Amount: $100.00" + "\n" + 
						"Balance: $500.50" + "\n\n" + 
						"Account: " + checking.getAccountNumber() + "\n" + 
						"Transactions:" + "\n" +
						"Date: 11/13/2016, Type: DEPOSIT, Amount: $1,000.00" + "\n" + 
						"Date: 11/13/2016, Type: WITHDRAWAL, Amount: $100.00" + "\n" + 
						"Balance: $900.90" + "\n\n";
				
		assertEquals( expected, summary );
    }
    
	@Test
	public void transfer() {
		CheckingAccount checking = new CheckingAccount();
		SavingsAccount savings = new SavingsAccount();
		savings.deposit( 1000, getDate() );
		
		Customer customer = new Customer( "John", "Doe", "123-45-6789" );
		customer.addAccount( checking );
		customer.addAccount( savings );
		
		bank.addCustomer( customer );

		try {
			bank.transfer( checking, checking, 2000d, getDate() );
		} catch (ServiceException e) {
			assertEquals( "Cannot transfer between same account", e.getMessage() );
		}
		
		checking.deposit( 1000d, getDate() );
		try {
			bank.transfer( checking, savings, 2000d, getDate() );
		} catch (ServiceException e) {
			assertEquals( "Insufficient funds", e.getMessage() );
		}

		try {
			bank.transfer( checking, savings, 500d, getDate() );
		} catch (ServiceException e) {
			fail();
		}
		
		assertEquals( 500d, checking.getTransactionsAmount(), 0.01 );
		assertEquals( 1500d, savings.getTransactionsAmount(), 0.01 );
	}
	
	@Test
	public void totalInterestPaid() throws ServiceException {
		CheckingAccount checking = new CheckingAccount();
		checking.deposit( 2500d, getDate() );
		checking.withdraw( 300d, getDate() );
		checking.deposit( 500d, getDate() );
		
		SavingsAccount savings = new SavingsAccount();
		savings.deposit( 1500, getDate() );
		
		Customer customer = new Customer( "John", "Doe", "123-45-6789" );
		customer.addAccount( checking );
		customer.addAccount( savings );
		
		bank.addCustomer( customer );
		
		checking = new CheckingAccount();
		checking.deposit( 1300d, getDate() );
		
		customer = new Customer( "John", "Doe", "123-45-6789" );
		customer.addAccount( checking );
		bank.addCustomer( customer );
		
		double interest = ( 2500d - 300d + 500d ) * .001;
		interest += ( 1000 * .001 ) + ( 500 * .002 );
		interest += 1300 * .001;
				
		assertEquals( interest, bank.totalInterestPaid(), 0.01 );
	}
	
	private Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.MONTH, Calendar.NOVEMBER );
		cal.set( Calendar.DAY_OF_MONTH, 13 );
		cal.set( Calendar.YEAR, 2016 );
		return cal.getTime();
	}

    @Test
    public void customerSummary() {
		Customer customer = new Customer( "John", "Doe", "123-45-6789" );
		customer.addAccount( new CheckingAccount() );
		customer.addAccount( new SavingsAccount() );
		bank.addCustomer( customer );

		customer = new Customer( "Jane", "Doe", "111-11-1111" );
		customer.addAccount( new CheckingAccount() );
		bank.addCustomer( customer );
		
		String expected = "Customer Summary:\n" +
						"John Doe ( 2 account(s) )\n" +
						"Jane Doe ( 1 account(s) )";
        assertEquals( expected, bank.customerSummary() );
    }
}
