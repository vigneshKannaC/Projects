package com.zoho.banking;
class IndianBank{
	public static void main(String args[]) throws Exception {
		TableCreation.createTables();
		TableInsertion.insertData();
		boolean flag=true;
         	while(flag){
		 	try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tINDIAN BANK");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.Log In\n\t\t\t\t\t2.Sign Up\n\t\t\t\t\t3.Create new Account\n\t\t\t\t\t4.Exit");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.print("Enter Your Choice: ");
				int num=Validation.isNumeric(Validation.reader.readLine());
				if(num<5 && num>0)
				{
					MainChoice choice=MainChoice.values()[num-1];
					switch(choice){
						case LOG_IN:	
							new LogIn().logIn();
							break;						
						case LOG_OUT:	
							new SignUp().signUp();
							break;						
						case CREATE_ACCOUNT: 
							new NewAccountCreation().createAccount();
							break;						
						case EXIT:
							  flag=false;
							  Database.getInstance().closeDB();
					}
				}
				else{
					System.out.println("invalid choice!");
				}
		 	}			
		 	catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
