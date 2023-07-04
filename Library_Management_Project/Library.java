package com.zoho.Library;
import java.util.Scanner;
import java.sql.SQLException;
public class Library
{
    static Scanner sc=new Scanner(System.in);
    public static void main(String ar[])throws Exception
    {
         new TableCreation().tableOrder();
         new TableInsertion().insertOrder();
         boolean flag=true;
         while(flag)
         {
			try
			{
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\tINDIAN LIBRARY");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.println("\n\t\t\t\t\t1.VIEW BOOKS\n\t\t\t\t\t2.ADD BOOK\n\t\t\t\t\t3.ADD MEMBER\n\t\t\t\t\t4.MEMBER'S LIST\n\t\t\t\t\t5.BORROW BOOK\n\t\t\t\t\t6.RETURN BOOK\n\t\t\t\t\t7.BOOK RESERVATION\n\t\t\t\t\t8.BORROWED HISTORY\n\t\t\t\t\t9.EXIT");
				System.out.println("---------------------------------------------------------------------------------------------------");
				System.out.print("Enter Your Choice:");
				Byte num=sc.nextByte();
				while(num>9 || num<1)
				{
					System.out.print("Enter Valid Choice!!!!!!:");
					num=sc.nextByte();
				}
				switch(num)
				{
					case 1: new ListBooks().booksList();
							break;
							
					case 2: new UpdateBook().addBooks();
							break;
							
					case 3: new Members().addMember();
							break;
					case 4: new Members().listMembers();
							break;
							
					case 5: new BookBorrow().borrowBook();
							break;
					
					case 6: new ReturnBook().bookReturn();
							break;

					case 7: new ReserveBook().bookReserve();
							break;

					case 8:	new BookBorrow().borrowedHistory();
        					break;
        						
					case 9:	flag=false;
						StatementSingleton.close();
						Database.getInstance().closeDB();
						break;
							
					default: System.out.println("Give Valid Input!!!!!!!!!");
						 break;
					
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
			catch(InvalidUserException e)
			{
				System.out.println(e.getMessage());
			}
			catch(DataNotFoundException e)
			{
				System.out.println(e.getMessage());
			}


	 }
    }
}
