package com.zoho.banking;
import java.util.Date;

 class Transaction {
	private String from_accountNo;
	private String to_accountNo;
	private double amount;
	private Date transaction_date;

     Transaction(String from_accountNo, String to_accountNo, double amount, Date transaction_date) {
        this.from_accountNo = from_accountNo;
        this.to_accountNo = to_accountNo;
        this.amount = amount;
        this.transaction_date = transaction_date;
    }

     String getFrom_accountNo() {
        return from_accountNo;
    }
     String getTo_accountNo() {
        return to_accountNo;
    }
     double getAmount() {
        return amount;
    }
     Date getTransaction_date() {
        return transaction_date;
    }

}
