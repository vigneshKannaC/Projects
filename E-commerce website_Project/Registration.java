package com.zoho.ecommerce;
class Registration{

	public void signUp() throws Exception {
		System.out.print("Enter your role(user/seller) :");
		String role=Validation.reader.readLine();		
		System.out.print("Enter your name :");
		String name=Validation.reader.readLine();
		Website.sc.nextLine();
		System.out.print("Enter your mobile number : ");
		long mobile_no=new Validation().isMobile(Validation.reader.readLine());
		System.out.print("Enter your gender :");
		String gender=Validation.reader.readLine();
		System.out.print("Enter your age :");
		int age=new Validation().isNumeric(Validation.reader.readLine());
		String password=new Validation().checkPassword();
		if(role.equals("user")) {
			if(!new ManageMember().validateMember("users",mobile_no)) {
				System.out.print("Enter your address : ");
				String address=Validation.reader.readLine();
				new ManageMember().addUser(name,mobile_no,password,age,gender,address);
				System.out.println("Registration successful!");
			}
			else System.out.println("You have already registed!Try to Log In!");
		}
		else if(role.equals("seller")){
			if(!new ManageMember().validateMember("vendors",mobile_no)) {
				new ManageMember().addMember("vendors",name,mobile_no,password,age,gender);
				System.out.println("Registration successful!");
			}
			else System.out.println("You have already registed!Try to Log In!");
		}
		else if(role.equals("admin")){
			if(!new ManageMember().validateMember("admins",mobile_no)) {
				new ManageMember().addMember("admins",name,mobile_no,password,age,gender);
				System.out.println("Registration successful!");
			}
			else System.out.println("You have already registed!Try to Log In!");
		}
		else{
			System.out.println("Enter the valid role!");
		}
	}
	
	public void logIn() throws Exception {
		System.out.print("Enter your mobile number : ");
		Long mobile=new Validation().isMobile(Validation.reader.readLine());
		String password=new Validation().checkPassword();
		if(new ManageMember().validateMember("users",mobile)){
			int id=new ManageMember().validateMember("users",password);
			if(id!=-1){
				if(new Menu().userMenu(new ManageMember().getMember("users",id))){
					System.out.println("Log out successful!Thanks for coming!");
				}
			}
			else System.out.println("password is invalid!");
		}
		else if(new ManageMember().validateMember("vendors",mobile)){
			int id=new ManageMember().validateMember("vendors",password);
			if(id!=-1){
				if(new Menu().vendorMenu(new ManageMember().getMember("vendors",id))){
					System.out.println("Log out successful!Thanks for coming!");
				}
			}
			else System.out.println("password is invalid!");
		}
		else if(new ManageMember().validateMember("admins",mobile)){
			int id=new ManageMember().validateMember("admins",password);
			if(id!=-1){
				if(new Menu().adminMenu(new ManageMember().getMember("admins",id))){
					System.out.println("Log out successful!Thanks for coming!");
				}
			}
			else System.out.println("password is invalid!");			
		}
		else{
			System.out.println("mobile number is invalid!");
		}
	}
}