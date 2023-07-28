package com.zoho.banking;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
class LogIn{
	ResultSet rs;
	UserDBManagement userDBManagement=new UserDBManagement();
	 void logIn(){
		try{								
			int id=getMemberId();	
			int role_id=userDBManagement.validateMember(id);			
			String password=Validation.checkPassword();			
			if(role_id==5){
				if(userDBManagement.validateMember(id,password)){
					MenuExecutor.customerMenu(getMember("customers",id));
				}
				else System.out.println("password is invalid!");
			}
			else if(role_id>0 && role_id<5){
				if(userDBManagement.validateMember(id,password)){
					MenuExecutor.staffMenu(getMember("staffs",id));
				}
				else System.out.println("password is invalid!");
			}
			else{
				System.out.println("Invalid user id!");
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
	     	if(rs.next()) return rs.getInt(1);
	        else{
	        	System.out.println("User id not found!");
	        	return getMemberId();
	        }
	 }
	 
	 <T> T getMember(String t_name,int u_id) throws SQLException{
         if(t_name.equals("customers")){
              rs=userDBManagement.findCustomer(t_name,u_id);
              if(rs.next()) {
			T t = (T)new Customer(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getLong(4),rs.getString(5),rs.getString(6));
			return t;
		}
         }else if(t_name.equals("staffs")){
        	 rs=userDBManagement.findStaff(t_name,u_id);
               if(rs.next()) {
			T t = (T)new Staff(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4));
			return t;
		}
         }
         return null;
  }
	
}
