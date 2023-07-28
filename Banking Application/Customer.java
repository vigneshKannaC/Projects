package com.zoho.banking;
class Customer {
    private int id;
    private int userId;
    private String name;
    private long aadharNumber;
    private String accountNumber;
    private String branch;
    
    Customer(int id, int userId, String name, long aadharNumber, String accountNumber, String branch) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.aadharNumber = aadharNumber;
        this.accountNumber = accountNumber;
        this.branch = branch;
    }
    
     int getId() {
        return id;
    }

     int getUserId() {
        return userId;
    }

     String getName() {
        return name;
    }

     long getAadhar_no() {
        return aadharNumber;
    }

     String getAccount_no() {
        return accountNumber;
    }

     String getBranch() {
        return branch;
    }
 
}
