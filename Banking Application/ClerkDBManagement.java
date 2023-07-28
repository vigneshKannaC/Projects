package com.zoho.banking;
import java.sql.ResultSet;
import java.sql.SQLException;
class ClerkDBManagement extends CustomerDBManagement{
	 ResultSet getDetails(int user_id){
		try{
			rs=st.executeQuery("select u.id,u.name,u.mobile_no,u.dob,u.gender,b.name from users u join branches b on b.id=u.branch_id where u.id="+user_id);      
            	}
		catch(SQLException e){
			e.printStackTrace();
            	}
            return rs;
         }
            
            
          ResultSet getStaffs(int role_id,int branch_id) throws SQLException{
        	 return st.executeQuery("select id,name,gender,status from users where role_id="+role_id+" and branch_id="+branch_id);
         }
         
          ResultSet getStaffs(int role_id) throws SQLException{
        	 return st.executeQuery("select id,name,gender,status from users where role_id="+role_id);
         }
	
          ResultSet showCustomers(int role_id,int branch_id){
         	try{
         		rs=st.executeQuery("select c.id,u.name,u.mobile_no,u.gender,c.status,s.name from users u join customers c on c.user_id=u.id join schemes s on s.id=c.scheme_id where u.role_id="+role_id+" and u.branch_id="+branch_id);
         	}
         	catch(SQLException e){
         		e.printStackTrace();
            }
            return rs;      
         }
         
          ResultSet showCustomers(int role_id){
          	try{
          		rs=st.executeQuery("select c.id,u.name,u.mobile_no,u.gender,c.status,s.name from users u join customers c on c.user_id=u.id join schemes s on s.id=c.scheme_id where u.role_id="+role_id);
          	}
          	catch(SQLException e){
          		e.printStackTrace();
             }
             return rs;      
          }
         
          ResultSet findCustomer(int id){
         	try{
         		rs=st.executeQuery("select user_id from customers where id="+id);
         	}
         	catch(SQLException e){
         		e.printStackTrace();
            }
            return rs;       
         }
         
         private int count;
          int statusUpdate(String status,int id){
         	try{
         		count=st.executeUpdate("update customers set status='"+status+"' where status='clerk_approved' and id="+id);
         	}
         	catch(SQLException e){
			e.printStackTrace();
            	}
         	return count;
         }
         
          void statusUpdate(int id){
         	try{
         		st.executeUpdate("update users set status='active' where id=(select user_id from customers where id="+id+")");
         	}
         	catch(SQLException e){
			e.printStackTrace();
            	}
         }
         
          void updateStatus(String status,int id){
         	try{
         		st.executeUpdate("update customers set status='"+status+"' where status='applied' and id="+id);
         	}
         	catch(SQLException e){
			e.printStackTrace();
            	}
         }
         
          ResultSet findStaff(int role_id,int id){
         	try{
         		rs=st.executeQuery("select name from users where id="+id+" and role_id="+role_id);
         	}
         	catch(SQLException e){
			e.printStackTrace();
            	}
            return rs;       
         }
         
          int setStatus(String status,int id){
         	try{
         		count=st.executeUpdate("update users set status='"+status+"' where id="+id);
         	}
         	catch(SQLException e){
			e.printStackTrace();
            	}
         	return count;
         }  
         
          void updateAccountNo(String acc_no,int customer_id){
         	try{
         		st.executeUpdate("update customers set account_no='"+acc_no+"' where id="+customer_id);
         	}
         	catch(SQLException e){
         		e.printStackTrace();
            	}
         }            
}
