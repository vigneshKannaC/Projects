package com.zoho.banking;
import java.io.IOException;
import java.sql.SQLException;
class CashierAccess extends CustomerAccess implements CashierMenus {
    private Staff staff;
    CustomerDBManagement customerDBManagement= new CustomerDBManagement();
     CashierAccess(){
    	
    }
     CashierAccess(Staff staff) {  	
        this.staff = staff;
    }
    	
    public void showProfile() {
    	new ClerkAccess().showStaff(staff.getRoleId(),staff.getBranchId(),true);
    }
       
    public void depositAmount() {
        updateMoney(true);
    }
    
    public void withdrawAmount() {
        updateMoney(false);
    }
    
     void updateMoney(boolean deposite){
        try{
            System.out.print("Enter Account number : ");
            String acc_no=Validation.getAccNo();
            System.out.print("Enter IFSC code : ");
            String ifsc=Validation.getISFC();
            int branch_id=customerDBManagement.getBranchId(acc_no);
            if(customerDBManagement.isValidCustomer(branch_id,ifsc)){
                    System.out.print("Enter the amount : ");
                    double amount=Validation.isNumeric(Validation.reader.readLine());
                    boolean savings=false;
                    if(customerDBManagement.getScheme(acc_no).equals("savings")) savings=true;
                    if(deposite && updateDeposite(acc_no,amount)){
                    	updateTransaction(customerDBManagement.getCustomerId(acc_no),amount,true);
                        	System.out.println("Amount was added successfully!");
                    }
                    else if(!deposite && updateWithdraw(acc_no,amount,savings)){
                    		updateTransaction(customerDBManagement.getCustomerId(acc_no),amount,false);
                        	System.out.println("Amount was taken successfully!");
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
    

    public void executeMenu() {
        try {
            while (true) {
                System.out.println("\n\t\t\tCashier Menu");
                System.out.println("\t\t\t------- ----");
                System.out.println("\t\t\t1. Show Profile");
                System.out.println("\t\t\t2. Deposit Amount");
                System.out.println("\t\t\t3. Withdraw Amount");
                System.out.println("\t\t\t4. Log Out");
                System.out.print("Enter your choice between (1-4): ");
                int choice = Validation.isNumeric(Validation.reader.readLine());
                if(choice>0 && choice<5){
	                CashierChoice cashierChoice = CashierChoice.values()[choice - 1];
	                switch (cashierChoice) {
	                    case SHOW_PROFILE:
	                        showProfile();
	                        break;
	                    case DEPOSIT_AMOUNT:
	                        depositAmount();
	                        break;
	                    case WITHDRAW_AMOUNT:
	                        withdrawAmount();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
