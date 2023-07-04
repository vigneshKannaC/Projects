package com.zoho.Library;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.ResultSet;
class ReserveBook{
	ResultSet rs;
	public void bookReserve() throws Exception{
			System.out.println("\t\t1.Reserve Book\t\t2.Reserved Books List");
			System.out.print("Enter 1 or 2 : ");
			Library.sc.nextLine();
			int num=new Validation().isNumeric(Library.sc.nextLine());
			if(num==1){
				bookReservation();
			}
			else{
				String check="select * from reservations";
				rs=StatementSingleton.getStatement().executeQuery(check);
				System.out.println(String.format("\t%-15s %-15s %-15s %-20s","Reserved Id","Book Id","Borrower Id","Borrowed Date"));
				while(rs.next()){
					System.out.println(String.format("\t%-15s %-15s %-15s %-20s",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4)));
				}
			}
	}
	private void bookReservation() throws Exception{
		int b_id=0;
		while(true){
     			System.out.print("Enter Book Id : ");
     			b_id=Library.sc.nextInt();
     			String validateMember="select * from book_details where id="+b_id;
     			rs=StatementSingleton.getStatement().executeQuery(validateMember);
     			if(!rs.next()) System.out.println("Book id not found!");
     			else break;
     		}
		int m_id=0;
     		while(true){
     			System.out.print("Enter Member Id : ");
     			m_id=Library.sc.nextInt();
     			String validateMember="select * from borrowers where borrower_id="+m_id;
     			rs=StatementSingleton.getStatement().executeQuery(validateMember);
     			if(!rs.next()) System.out.println("Member id not found!Ensure you are a excisting member or add member.");
     			else break;
     		}
		String check="select * from book_details where id="+b_id+" and quantity=0";
		rs=StatementSingleton.getStatement().executeQuery(check);
		if(rs.next()){			
			StatementSingleton.getStatement().executeUpdate("update book_details set reserved_count=(reserved_count+1)");
			StatementSingleton.getStatement().executeUpdate("insert into reservations(book_id,borrower_id) values("+b_id+","+m_id+")");
			System.out.println("Book is reserved!");
		}
		else{
			System.out.println("Book is available.Check again!");
		}
	}		
}
