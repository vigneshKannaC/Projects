package com.zoho.banking;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
class SignUp{
	ResultSet rs;
	UserDBManagement userDBManagement=new UserDBManagement();
	 void signUp(){		
		try{			
			int id=getMemberId();			
			System.out.print("Enter mobile number : ");
			long mobile=Validation.isMobile(Validation.reader.readLine());			
			int role=userDBManagement.validateMember(id,mobile);			
			if(role!=-1){
				String status=userDBManagement.getStatus(id);
				if(status!=null && (status.equals("manager_approved") || status.equals("active"))){				
					String pass=Validation.checkPassword();
					if(userDBManagement.insertPassword(id,pass)){
						System.out.println("Password added successfully!");
					}
					else{
						System.out.println("You had signedup earlier!");
					}
				}
				else{
					System.out.println("You didn't get approval for Sign Up!");
				}
			}
			else{
				System.out.println("User id and password combination not available!");
			}
		}
		catch(IOException | SQLException e){
			e.printStackTrace();
		}
	}
	
	private int getMemberId() throws SQLException,IOException{
	     	System.out.print("Enter your id : ");
	     	int id=Validation.isNumeric(Validation.reader.readLine());
	     	rs=userDBManagement.checkMemberId(id);
	     	if(rs.next()) return id;
		else{
			System.out.println("User id not found!");
			return getMemberId();
		}
 	}
}
