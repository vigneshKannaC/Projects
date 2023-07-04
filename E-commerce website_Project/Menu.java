package com.zoho.ecommerce;
class Menu{
	int choice;
	public boolean userMenu(User user)throws Exception{
			System.out.println("\tWelcome User\t---->\t"+user.getName());
			while(true){
				System.out.println("\n\t\t\tUser Menu");
				System.out.println("\t\t\t---- ----");
				System.out.println("\t\t\t1.Profile\n\t\t\t2.View Products\n\t\t\t3.Order History\n\t\t\t4.Cancel Order\n\t\t\t5.Return Order\n\t\t\t6.Update Address\n\t\t\t7.Log Out");
				System.out.print("Enter your choice between (1-7): ");
				choice=new Validation().isNumeric(Validation.reader.readLine());
				switch(choice){
					case 1: new Controller().getDetails("users",user.getId());
							break;
					case 2: new ManageProducts().viewProducts(user);
							break;
					case 3: new ManageProducts().ordersHistory(user);
							break;
					case 4: while(true){
								if(new UpdateOrders().updateOrder(true)){
									System.out.println("Your order is cancelled successfully!");
									break;
								}else{
									System.out.println("\t\t1.Continue\t\t2.Go back");
									System.out.print("Enter our choice : ");
									if(new Validation().isNumeric(Website.sc.next())==2) break;
								}
							}
							break;
					case 5: while(true){
								if(new UpdateOrders().updateOrder(false)){
									System.out.println("Your order is returned successfully!");
									break;
								}else{
									System.out.println("\t\t1.Continue\t\t2.Go back");
									System.out.print("Enter our choice : ");
									if(new Validation().isNumeric(Website.sc.next())==2) break;
								}
							}
							break;
					case 6: new ManageProducts().updateAddress(user);
							break;
					case 7: return true;
					default: System.out.print("Enter a valid number between 1-7!");
				}
			}
	}
	public boolean vendorMenu(Vendor vendor)throws Exception{
			System.out.println("\tWelcome Vendor\t--->\t"+vendor.getName());
			if(new ManageMember().isEligibleVendor(vendor).equals("approved"))
				while(true){
					System.out.println("\n\t\t\t\tVendor Menu");
					System.out.println("\t\t\t\t------ ----");
					System.out.println("\t\t\t1.Profile\n\t\t\t2.Add Products\n\t\t\t3.List Products\n\t\t\t4.Update Product\n\t\t\t5.Log Out");
					System.out.print("Enter your choice between (1-5): ");
					choice=new Validation().isNumeric(Validation.reader.readLine());
					switch(choice){
						case 1: new Controller().getDetails("vendors",vendor.getId());
								break;
						case 2: new UpdateProducts().addProducts(vendor);
								break;
						case 3: new UpdateProducts().listProducts(vendor);
								break;
						case 4: new UpdateProducts().updateProducts(vendor);
								break;
						case 5: return true;
						default: System.out.print("Enter a valid number between 1-5!");
					}
				}
			else if(new ManageMember().isEligibleVendor(vendor).equals("declined")){
				System.out.println("Sorry!You are declined by admin!");
			}
			else{
				System.out.println("Till now admin didn't give access for you!");
			}
			return false;
	}

	
	public boolean adminMenu(Admin admin)throws Exception{
			System.out.println("\tWelcome Admin\t--->\t"+admin.getName());
			while(true){
				System.out.println("\n\t\t\t\tAdmin Menu");
				System.out.println("\t\t\t\t----- ----");
				System.out.println("\t\t\t1.Profile\n\t\t\t2.Access vendors\n\t\t\t3.Log Out");
				System.out.print("Enter your choice between (1-3): ");
					choice=new Validation().isNumeric(Validation.reader.readLine());
					switch(choice){
						case 1: new Controller().getDetails("admins",admin.getId());
								break;
						case 2: new Controller().adminAccess();
								break;
						case 3: return true;
						default: System.out.print("Enter a valid number between 1-3!");
					}
			}	
	}
}
