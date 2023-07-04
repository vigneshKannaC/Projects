package com.zoho.Library;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
class BookBorrow{
     	ResultSet rs,rs1;
     	public static Connection con=Database.getConnection();
     	public  void borrowBook()throws Exception{
     		System.out.print("Do u want to take reserved book?(y-yes/n-no) :");
     		String reserved=Library.sc.next();
     		int m_id=0;
     		while(true){
     			System.out.print("Member Id : ");
     			m_id=new Validation().isNumeric(Library.sc.next());
     			String validateMember="select * from borrowers where borrower_id="+m_id;
     			rs=StatementSingleton.getStatement().executeQuery(validateMember);
     			if(!rs.next()) System.out.println("Member id not found!Ensure you are a excisting member or add member.");
     			else break;
     		}
     		int b_id=0;
		while(true){
     			System.out.print("Taken Book Id : ");
     			b_id=new Validation().isNumeric(Library.sc.next());
     			String validateMember="select * from book_details where id="+b_id;
     			rs1=StatementSingleton.getStatement().executeQuery(validateMember);
     			if(!rs1.next()) System.out.println("Book id not found!");
     			else break;
     		}
     		if(updateBorrower(m_id,b_id,reserved)){	
     			StatementSingleton.getStatement().executeUpdate("insert into borrowed_history(book_id,borrower_id) values("+b_id+","+m_id+")");
     			if(reserved.equals("y")){
     				StatementSingleton.getStatement().executeUpdate("update book_details set reserved_count=(reserved_count-1) where id="+b_id);
     				StatementSingleton.getStatement().executeUpdate("DELETE FROM reservations WHERE reserved_id=(SELECT reserved_id FROM reservations WHERE book_id = "+b_id+" limit 1);");
     				System.out.println("Reserved Book is borrowed successfully"); 
     			}
     			else System.out.println("Book is borrowed successfully"); 			
     		}		
     	}
     	
     	private  boolean updateBorrower(int m_id,int b_id,String reserved) throws Exception{
     		String getBookCount="select count from borrowers where borrower_id="+m_id;
     		rs=StatementSingleton.getStatement().executeQuery(getBookCount);
     		
     		rs.next();
     		if(rs.getInt(1)>=5){
     			System.out.println("Already you taken 5 books so you can't take more books.");
     			return false;
     		}
     		String getCount;
     		if(reserved.equals("n")) {
     			getCount="select quantity from book_details where id="+b_id+" and quantity > reserved_count";
     		}
     		else {	
     			getCount="select quantity from book_details where id="+b_id;
     		}
     		rs.close();
     		rs1=StatementSingleton.getStatement().executeQuery(getCount);
     		
     		if(rs1.next() && rs1.getInt(1)>0){
     			String updateCount="update borrowers set count=(count+1) where borrower_id="+m_id;
     			StatementSingleton.getStatement().executeUpdate(updateCount);
     			String validateCount="update book_details set quantity=(quantity-1) where id="+b_id+" and quantity!=0";
     			StatementSingleton.getStatement().executeUpdate(validateCount);
     			return true;
     		}
     		else{
     			System.out.println("Book is not available right now!You can reserve it for future");
     			return false;
     		}
    
     	}
       
       public void borrowedHistory() throws Exception{
       		rs=StatementSingleton.getStatement().executeQuery("select h.history_id,h.book_id,h.borrower_id,b.name,h.borrowed_date,h.due_date,h.returned_date from borrowed_history h join borrowers b on b.borrower_id=h.borrower_id");
       		System.out.println(String.format("\t%-5s %-10s %-15s %-15s %-15s %-15s %-15s","Id","BookId","MemberId","Name","BorrowedDate","DueDate","ReturnedDate"));
       		while(rs.next()){
       			System.out.println(String.format("\t%-5s %-10s %-15s %-15s %-15s %-15s %-15s",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
       		}    		
       }     	
}
