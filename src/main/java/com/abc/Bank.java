package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank {
	
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }
    
    public Customer getCustomer( Customer customer ) {
    	for( Customer c : customers ) {
    		if( c.getSocialSecurityNumber().equals( customer.getSocialSecurityNumber() ) ) {
    			return c;
    		}
    	}
    	return null;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public Customer openAccount(Customer customer, Account account ) {
		customer.addAccount( account );
		
		Customer existingCustomer = getCustomer( customer );
		if( existingCustomer == null ) {
			addCustomer( customer );
		}
		
		return customer;
	}
    
    
	public void deposit( Customer customer, Account account, double amount, Date date ) throws ServiceException {
		validate( customer, account, amount );
		account.deposit( amount, date );
	}
	
	public void withdraw( Customer customer, Account account, double amount, Date date ) throws ServiceException {
		validate( customer, account, amount );
		account.withdraw( Math.abs( amount ), date );
	}

	protected void validate( Customer customer, Account account, double amount ) throws ServiceException {
		if( !this.customers.contains( customer ) )
			throw new ServiceException( "Customer does not exist" );
		
		if( !customer.contains( account ) ) 
			throw new ServiceException( "Invalid account" );
		
		if( amount <= 0 )
			throw new ServiceException( "Amount has to be greater than zero" );
	}
	
	public void transfer( Account fromAccount, Account toAccount, double amount, Date date ) throws ServiceException {
		if( fromAccount == toAccount )
			throw new ServiceException( "Cannot transfer between same account" );
		
		if( fromAccount.getBalance() < amount )
			throw new ServiceException( "Insufficient funds" );
		
		fromAccount.withdraw( amount, date );
		toAccount.deposit( amount, date );
		
	}
	
	public String getStatement( Customer customer ) throws ServiceException {
		if( !this.customers.contains( customer ) )
			throw new ServiceException( "Customer does not exist" );

		StringBuilder sb = new StringBuilder();
		sb.append( "Statement for " + customer.getName() + "\n" );
		for( Account a : customer.getAccounts() ) {
			sb.append( a.getStatement() );
		}
		return sb.toString();
	}
	
    public String customerSummary() {
        String summary = "Customer Summary:";
        for (Customer c : customers)
            summary += "\n" + c.getName() + " ( " + c.getAccounts().size() + " account(s) )";
        return summary;
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

}
