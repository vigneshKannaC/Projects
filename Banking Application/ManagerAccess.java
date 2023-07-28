package com.zoho.banking;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
class ManagerAccess extends ClerkAccess implements ManagerMenus {
    private Staff staff;
    ManagerDBManagement managerDBManagement=new ManagerDBManagement();
     ManagerAccess() {
    }
     ManagerAccess(Staff staff) {
         this.staff = staff;
    }
	
     public void showProfile() {
		showStaff(staff.getRoleId(),staff.getBranchId(),false);
	}
    
    public void accessMember(int branch_id) {
        try {      
            System.out.println("\n\t\t1.Access Customer\t\t2.Access Clerk\t\t3.Access Cashier\t\t4.Back\n");
            System.out.print("Enter your choice: ");
            int choice = Validation.isNumeric(Validation.reader.readLine());
            switch (choice) {
                case 1:
                    showCustomers(5, branch_id, false);
                    break;
                case 2:
                    showStaff(3, branch_id, false);
                    changeStatus(3, branch_id, false);
                    break;
                case 3:
                    showStaff(4, branch_id, false);
                    changeStatus(4, branch_id, false);
                    break;
                case 4:
                    return;
                default:
                    System.out.print("Enter a valid number between 1-4!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClerk() {
    	addMember(3, "clerk", staff.getBranchId());
    }

    public void addCashier() {
    	addMember(4, "cashier", staff.getBranchId());
    }
    
     void addMember(int role_id,String member,int branch_id){
        try{
           System.out.print("Enter name : ");
           String name=Validation.reader.readLine();
           System.out.print("Enter mobile number : ");
           long mobile_no=Validation.isMobile(Validation.reader.readLine());
           System.out.print("Enter gender : ");
           String gender=Validation.reader.readLine();
           System.out.print("Enter DOB(yyyy-mm-dd): ");
           String dob=Validation.reader.readLine();
           int branch=branch_id;
           if(branch==0){
              System.out.print("Enter branch name: ");
              branch=managerDBManagement.getBranchId(Validation.reader.readLine());
           }
           String status="active";
           if(role_id==5) status="inactive";
           if(!managerDBManagement.isExists(mobile_no,branch)){
        	      Date date=Date.valueOf(dob);
        	      rs=managerDBManagement.putMember(role_id,name,mobile_no,gender,date,status,branch);
        	      rs.next();
        	      System.out.println(member+" is added successfully! and id="+rs.getInt("id"));
           }
           else{
              System.out.println(member+" already available in this branch!");
           }
        }
        catch(IOException | SQLException e){
        	e.printStackTrace();
         }

     }
    
    public void transferMember(int managerBranchId,boolean isAdmin){
    	 System.out.print("Enter user id : ");
    	 try {
			int userId=managerDBManagement.validateMember(Validation.isNumeric(Validation.reader.readLine()));
			if(userId!=-1){
				System.out.print("Enter new branch name : ");
				int newBranchId=managerDBManagement.getBranchId(Validation.reader.readLine());
				int staffBranchId=managerDBManagement.getBranchId(userId);
				if(staffBranchId == newBranchId){
					System.out.println("You are already being in that branch!");
				}
				else{
					if(isAdmin || managerBranchId == staffBranchId){
						managerDBManagement.changeBranch(userId,newBranchId);
						System.out.println("Staff is transferred successfully!");
					}
					else{
						System.out.println("Branch not available!");
					}
				}
			}
			else{
				System.out.println("User not found!");
			}
			 
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public void executeMenu() {
        try {
            while (true) {
                System.out.println("\n\t\t\tManager Menu");
                System.out.println("\t\t\t------- ----");
                System.out.println("\t\t\t1. Show Profile");
                System.out.println("\t\t\t2. Access Member");
                System.out.println("\t\t\t3. Add Clerk");
                System.out.println("\t\t\t4. Add Cashier");
                System.out.println("\t\t\t5. Trasfer Staff");
                System.out.println("\t\t\t6. Log Out");
                System.out.print("Enter your choice between (1-6): ");
                int choice = Validation.isNumeric(Validation.reader.readLine());
                if(choice>0 && choice<7){
	                ManagerChoice managerChoice = ManagerChoice.values()[choice - 1];
	                switch (managerChoice) {
	                    case SHOW_PROFILE:
	                        showProfile();
	                        break;
	                    case ACCESS_MEMBER:
	                        accessMember(staff.getBranchId());
	                        break;
	                    case ADD_CLERK:
	                        addClerk();
	                        break;
	                    case ADD_CASHIER:
	                        addCashier();
	                        break;
	                    case TRANSFER_MEMBER:
	                    	 transferMember(staff.getBranchId(),false);
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
