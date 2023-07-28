package com.zoho.banking;
interface MenuExecutor {
	
	static void customerMenu(Customer customer){	
	 	CustomerAccess customerAccess= new CustomerAccess(customer);
		customerAccess.executeMenu();
	}
	
    	static void staffMenu(Staff staff){	
		int role=staff.getRoleId();
		switch(role){ //1->('Admin') 2->('Manager') 3->('Clerk') 4->('Cashier')
			case 1: 
				AdminAccess adminAccess= new AdminAccess(staff);
				adminAccess.executeMenu();
				break;

			case 2:
				ManagerAccess managerAccess= new ManagerAccess(staff);
				managerAccess.executeMenu();
				break;

			case 3: 
				ClerkAccess clerkAccess= new ClerkAccess(staff);
				clerkAccess.executeMenu();
				break;

			case 4: 
				CashierAccess cashierAccess= new CashierAccess(staff);
				cashierAccess.executeMenu();
				break;
		}
	}
}
