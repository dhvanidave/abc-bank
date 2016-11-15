package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
	private String lastName;
	private String socialSecurityNumber;
    
	private List<Account> accounts;

    public Customer( String firstName, String lastName, String socialSecurityNumber ) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.socialSecurityNumber = socialSecurityNumber;
    	this.accounts = new ArrayList<Account>();
    }
    
    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
    public String getName() {
        return firstName + " " + lastName;
    }
    public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}
	
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public boolean contains( Account account ) {
    	for( Account a : this.accounts ) {
    		if( account == a ) return true;
    	}
    	
    	return false;
    }
    
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        
        return total;
    }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((socialSecurityNumber == null) ? 0 : socialSecurityNumber.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;

		if (socialSecurityNumber == null) {
			if (other.socialSecurityNumber != null)
				return false;
		} else if (!socialSecurityNumber.equals(other.socialSecurityNumber))
			return false;
		return true;
	}
}
