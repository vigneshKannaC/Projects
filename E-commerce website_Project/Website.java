package com.zoho.ecommerce;
import java.util.Scanner;
class Website{
	static Scanner sc=new Scanner(System.in);
	public static void main(String args[]) throws Exception {
		new TableCreation().tableOrder();
		new TableInsertion().insertOrder();
		new UpdateOrders().updateDate();
		boolean flag=true;
         	while(flag){
         		try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tAMAZON");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.Log In\n\t\t\t\t\t2.Sign Up\n\t\t\t\t\t3.Exit");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.print("Enter Your Choice: ");
				Byte num=sc.nextByte();
				while(num>3 || num<1)
				{
					System.out.print("Enter Valid Choice: ");
					num=sc.nextByte();
				}
				
				if(num==1){
					new Registration().logIn();
				}
				else if(num==2){
					new Registration().signUp();
				}
				else{
					flag=false;
					StatementSingleton.close();
					Database.getInstance().closeDB();
				}
         		}
         		catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
