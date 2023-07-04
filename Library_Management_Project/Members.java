package com.zoho.Library;
import java.sql.ResultSet;
import java.sql.Statement;
class Members{
     	ResultSet rs=null;
	public void addMember() throws Exception{
		Library.sc.nextLine();
		System.out.print("Enter your Name : ");
		String name=Library.sc.nextLine();
		System.out.print("Enter your mobile number : ");
		long number=new Validation().isMobile(Library.sc.next());
		
		// Check if the user already exists in the member table
    		String checkBorrowerQuery = "SELECT borrower_id FROM Borrowers WHERE upper(name)=upper('" + name + "') and mobile_no="+number;
    		ResultSet authorResultSet = StatementSingleton.getStatement().executeQuery(checkBorrowerQuery);
    		int BorrowerId=-1;
    		if (authorResultSet.next()) {
        		// member already exists, use the existing id
        		BorrowerId = authorResultSet.getInt(1);
        		System.out.println("Member is already available and id : "+BorrowerId);
    		} else {
        		// member does not exist, insert new record
        		String insertMemberQuery = "INSERT INTO borrowers(name,mobile_no) VALUES ('" + name + "',"+number+") returning borrower_id";
        		rs=StatementSingleton.getStatement().executeQuery(insertMemberQuery);
        		rs.next();
            		BorrowerId = rs.getInt("borrower_id");
            		System.out.println("Member is added successfully and id : "+BorrowerId);
    		}	
	}
	
	public void listMembers()throws Exception{
     		String selectMembers="select * from borrowers";
     		rs=StatementSingleton.getStatement().executeQuery(selectMembers);
     		System.out.println(String.format("\t%-5s %-30s %-20s %-5s","Id","Name","Mobile Number","Books Count"));
     		while(rs.next()){
     			System.out.println(String.format("\t%-5s %-30s %-20s %-5s",rs.getInt(1),rs.getString(2),rs.getLong(3),rs.getInt(4)));
     		}
     	}	
}
