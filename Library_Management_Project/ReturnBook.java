package com.zoho.Library;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
class ReturnBook{
     	ResultSet rs;
	public void bookReturn() throws Exception{
		System.out.print("Enter Book Id :");
		int b_id=Library.sc.nextInt();
		System.out.print("Enter Member Id :");
		int m_id=Library.sc.nextInt();
		Date deadDate=null,startDate=null;
		String date;
		SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd");
		while(true){
			System.out.print("Enter return date (yyyy-MM-dd): ");
			date= Library.sc.next();
			try {
         			deadDate=input.parse(date);
         			break;
     	 		}
      			catch(Exception e) {
        			System.out.println("Written date format is wrong!");
      			}
      		}
      		rs=StatementSingleton.getStatement().executeQuery("select borrowed_date from borrowed_history where book_id="+b_id+" and borrower_id="+m_id+" and returned_date is null");
      		if(rs.next()){
      			String borrowed=rs.getString(1);
      			try {
         			startDate=input.parse(borrowed);
     	 		}
      			catch(Exception e) {
        			System.out.println("Written date format is wrong!");
      			}
      			if(startDate.compareTo(deadDate)<=0){
      				StatementSingleton.getStatement().executeUpdate("update book_details set quantity=(quantity+1) where id="+b_id);
      				StatementSingleton.getStatement().executeUpdate("update borrowers set count=(count-1) where borrower_id="+m_id+" and count>=0");
      				StatementSingleton.getStatement().executeUpdate("update borrowed_history set returned_date='"+date+"' where book_id="+b_id+" and borrower_id="+m_id);
      				if(isReserved(b_id)){
      					StatementSingleton.getStatement().executeUpdate("update reservations set reserved_date='"+date+"' where book_id="+b_id);
      				}
      				System.out.println("Book was returned successfully");
      			}
      			else{
      				System.out.println("Returned date is invalid!");
      			}
      		}
      		else{
      			System.out.println("There is no borrowed history with the book id and borrower id combination!");
      		}      			
	}
	
	private boolean isReserved(int b_id) throws Exception{
		String reserved="select reserved_count from book_details where b_id="+b_id;
		rs=StatementSingleton.getStatement().executeQuery(reserved);
		rs.next();
		if(rs.getInt(1)>0){
			return true;
		}
		return false;
	}

}
