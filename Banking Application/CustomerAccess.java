package com.zoho.banking;
import java.sql.SQLException;
import java.io.IOException;

class CustomerAccess extends AccountDetails implements CustomerMenus {
    CustomerDBManagement customerDBManagement = new CustomerDBManagement();	
    private Customer customer;
    
     CustomerAccess(){
    	
    }

     CustomerAccess(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void showProfile() {
        showCustomer(customer.getUserId());
        showAccounts(customer.getUserId());
    }

    @Override
     public void transferAmount() {     
            String acc_no = chooseAccount(customer.getUserId());
            try {
			amountTransfer(customerDBManagement.getCustomerId(acc_no), acc_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    void amountTransfer(int c_id,String account_no){
        try{
            System.out.print("Enter benificer Account number : ");
            String benificier_acc_no=Validation.getAccNo(); 
            if(account_no.equals(benificier_acc_no)){
            	System.out.print("Both account numbers are same!");
            	return;
            }
            System.out.print("Enter ifsc code : ");
            String ifsc=Validation.getISFC();
            int branch_id=customerDBManagement.getBranchId(benificier_acc_no);
            if(customerDBManagement.isValidCustomer(branch_id,ifsc)){
                int benificier_id=customerDBManagement.getCustomerId(benificier_acc_no);
                System.out.print("Enter the amount : ");
                double amount=Validation.isNumeric(Validation.reader.readLine());
                boolean savings=false;
                if(customerDBManagement.getScheme(account_no).equals("savings")) savings=true;
                if(updateWithdraw(account_no,amount,savings)){
                	updateTransaction(c_id,benificier_id,amount);
                	updateDeposite(benificier_acc_no,amount);
                	System.out.println("Amount transaction successful!");
                }       
            }
            else{
                System.out.println("User with given account number and ifsc is not available!");
            }
        }
        catch(SQLException | IOException e){
            e.printStackTrace();
        }
    }
    
     void updateTransaction(int customer_id,double amount,boolean deposite){
	    	if(deposite){
	    		customerDBManagement.deposite(customer_id,amount);
	    	}
	    	else{
	    		customerDBManagement.withdraw(customer_id,amount);
	    	}
	}
    
    
    

  //overloading
     void updateTransaction(int customer_id,int benificier_id,double amount){
   		customerDBManagement.makeTransaction(customer_id,benificier_id,amount);
   }
   
    boolean updateWithdraw(String acc_no,double amount,boolean savings) throws SQLException {
     	double balance=customerDBManagement.getBalance(acc_no);
        if(balance >= amount+3000 && savings){
        	customerDBManagement.setBalance(amount,acc_no);  
            return true;
        }
        else if(balance >= amount+10000 && !savings){
        	customerDBManagement.setBalance(amount,acc_no);  
            return true;
        }
        else{
            System.out.println("Insufficient balance");
            return false;
        }
    }
    
     boolean updateDeposite(String acc_no,double amount) throws SQLException {
	    int rows=customerDBManagement.updateDeposite(amount,acc_no);
	    if(rows>0){
	        return true;
	    }
	    return false;
    }
    @Override
     public void checkBalance(){      
            String acc_no = chooseAccount(customer.getUserId());
            try {
			System.out.println("Your balance is " + customerDBManagement.getBalance(acc_no));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    @Override
    public void showAccountHistory() {
            showAccounts(customer.getUserId());
            showTransactions(customer.getUserId());
    }
    
    public void executeMenu() {
        try {
            while (true) {
                System.out.println("\n\t\t\tCustomer Menu");
                System.out.println("\t\t\t-------- ----");
                System.out.println("\t\t\t1. Show Profile");
                System.out.println("\t\t\t2. Transfer Amount");
                System.out.println("\t\t\t3. Check Balance");
                System.out.println("\t\t\t4. Show Account History");
                System.out.println("\t\t\t5. Log Out");
                System.out.print("Enter your choice between (1-5): ");
                int choice = Validation.isNumeric(Validation.reader.readLine());
                if(choice>0 && choice<6){
	                CustomerChoice customerChoice = CustomerChoice.values()[choice - 1];
	                switch (customerChoice) {
	                    case SHOW_PROFILE:
	                        showProfile();
	                        break;
	                    case TRANSFER_AMOUNT:
	                        transferAmount();
	                        break;
	                    case CHECK_BALANCE:
	                        checkBalance();
	                        break;
	                    case SHOW_ACCOUNT_HISTORY:
	                        showAccountHistory();
	                        break;
	                    case LOG_OUT:
	                    	System.out.println("Log out successful!Thanks for coming!");
	                        return;
	                }
                }
                else{
                	System.out.print("Invalid choice!");
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
