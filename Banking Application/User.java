package com.zoho.banking;
import java.util.Date;
 class User {
        private int id;
        private int customerId;
        private String name;
        private long mobileNumber;
        private Date dob;
        private String gender;
        private String branch;
        private long aadhar;
        private String address;
        private String scheme;
        private String customerStatus;
        private String status;
        
         User(int id, String name, long mobileNumber, Date dob, String gender, String branch) {
            this.id = id;
            this.name = name;
            this.mobileNumber = mobileNumber;
            this.dob = dob;
            this.gender = gender;
            this.branch = branch;
        }
        
         User(int customerId, String name, long mobileNumber, String gender, String customerStatus, String scheme) {
            this.customerId = customerId;
            this.name = name;
            this.mobileNumber = mobileNumber;
            this.gender = gender;
            this.customerStatus = customerStatus;
            this.scheme=scheme;
        }
        
         User(int id, String name, String gender, String status) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.status = status;
        }
        
         int getCustomerId(){
        	return customerId;
        }
        
         int getId() {
            return id;
        }
        
         String getName() {
            return name;
        }
        
         long getMobileNumber() {
            return mobileNumber;
        }
        
         Date getDob() {
            return dob;
        }
        
         String getGender() {
            return gender;
        }
        
         String getBranch() {
            return branch;
        }

		 long getAadhar() {
			return aadhar;
		}
		
		 String getAddress() {
			return address;
		}
		
		 String getCustomerStatus(){
			return customerStatus;
		}
		 String getStatus(){
			return status;
		}
		 String getScheme(){
			return scheme;
		}
}
