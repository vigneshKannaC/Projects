package com.zoho.banking;
import java.util.Date;
 class Account {
	
	private int id;
	private String scheme;
	private String accountNumber;
	private double balance;
	private Date openedDate;

	Account(int id, String scheme, String accountNumber, double balance, Date openedDate) {
		this.id = id;
		this.scheme = scheme;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.openedDate = openedDate;
	}
	
	 int getId() {
		return id;
	}
	 String getScheme() {
		return scheme;
	}
	 String getAccountNumber() {
		return accountNumber;
	}
	 double getBalance() {
		return balance;
	}
	 Date getOpenedDate() {
		return openedDate;
	}
		
}
