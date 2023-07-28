package com.zoho.banking;
import java.io.IOException;
class AdminAccess extends ManagerAccess implements AdminMenus {
    private Staff staff;
     AdminAccess(Staff staff) {
        this.staff = staff;
    }
	
    public void showProfile() {
	showStaff(staff.getRoleId(),0,false);
    }
    
    public void addClerk() {
    	addMember(3, "clerk", 0);
    }

    public void addCashier() {
    	addMember(4, "cashier", 0);
    }
    public void addManager() {
    	addMember(2, "manager", 0);
    }
    
    public void executeMenu() {
        try {
            while (true) {
                System.out.println("\n\t\t\tAdmin Menu");
                System.out.println("\t\t\t----- ----");
                System.out.println("\t\t\t1. Show Profile");
                System.out.println("\t\t\t2. Access Member");
                System.out.println("\t\t\t3. Add Clerk");
                System.out.println("\t\t\t4. Add Cashier");
                System.out.println("\t\t\t5. Add Manager");
                System.out.println("\t\t\t6. Trasfer Staff");
                System.out.println("\t\t\t7. Log Out");
                System.out.print("Enter your choice between (1-7): ");
                int choice = Validation.isNumeric(Validation.reader.readLine());
                if(choice>0 && choice<8){
	                AdminChoice adminChoice = AdminChoice.values()[choice - 1];
	                switch (adminChoice) {
	                    case SHOW_PROFILE:
	                        showProfile();
	                        break;
	                    case ACCESS_MEMBER:
	                        accessMember(0);
	                        break;
	                    case ADD_CLERK:
	                        addClerk();
	                        break;
	                    case ADD_CASHIER:
	                        addCashier();
	                        break;
	                    case ADD_MANAGER:
	                        addManager();
	                        break;
	                    case TRANSFER_MEMBER:
                   	 	transferMember(0,true);
                   	 	break;
	                    case LOG_OUT:
	                    	System.out.println("Log out successful!Thanks for coming!");
	                        return;
	                    default:
	                        System.out.print("Enter a valid number between 1-6!");
	                }
                }else{
                	System.out.print("Invalid choice!");
                }
            }
        } catch (IOException e) {
            	e.printStackTrace();
        }
    }
}
