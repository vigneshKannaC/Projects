package com.zoho.banking;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Date;
import java.io.IOException;

class UserDBManagement {
     static Connection con;
     static  Statement st;
     static PreparedStatement pst;
     ResultSet rs;
     static{
	      try{
	          con=Database.getInstance().getConnection();
	          st=con.createStatement();
	      }
	      catch(SQLException e){
	          e.printStackTrace();
	      }
     }
     
    String getStatus(int id)throws SQLException{
     	rs=st.executeQuery("select status from users where id="+id);
     	if(rs.next()){
     		return rs.getString(1);
     	}
     	else return null;
     }
     
     

      int validateMember(int id) throws SQLException{
         rs=st.executeQuery("select role_id from users where id="+id);
         if(rs.next()){
            return rs.getInt(1);
         }
         else return -1;		   
     }
     
     //overloading
      boolean validateMember(int id,String pass) throws SQLException{
         rs=st.executeQuery("select id from users where id="+id+" and password='"+pass+"'");
         if(rs.next()) return true;
         return false;   
     }
     
     //overloading
      int validateMember(int id,long mobile) throws SQLException{
         rs=st.executeQuery("select role_id from users where id="+id+" and mobile_no="+mobile);
         if(rs.next()) return rs.getInt(1);
         return -1;   
     }
     
     ResultSet checkMemberId(int id) throws SQLException{
    	return st.executeQuery("select id from users where id="+id);
    }
     
      int getBranchId(String name) throws SQLException,IOException{
        rs=st.executeQuery("select id from branches where upper(name)=upper('"+name+"')");
        if(rs.next())
            return rs.getInt(1);
        else{
            System.out.print("Enter the valid branch name :");
            return getBranchId(Validation.reader.readLine());
        }
    }
    
      boolean insertPassword(int id,String pass) throws SQLException {
    	rs=st.executeQuery("select id from users where id="+id+" and password is null");
    	if(rs.next()){
    		st.executeUpdate("update users set password='"+pass+"' where id="+id);
    		return true;
    	}
    	return false;
    }
    
      ResultSet findCustomer(String t_name,int u_id) throws SQLException{
    	 pst=con.prepareStatement("select c.id,c.user_id,u.name,c.aadhar,c.account_no,b.name from customers c join users u on c.user_id=u.id join branches b on b.id=u.branch_id where c.user_id=?");
         pst.setInt(1,u_id);
         return pst.executeQuery();
     }
     
      ResultSet findStaff(String t_name,int u_id) throws SQLException{
    	 pst=con.prepareStatement("select u.id,r.id,b.id,u.name from users u join roles r on u.role_id=r.id join branches b on b.id=u.branch_id where u.id=?");
         pst.setInt(1,u_id);
         return pst.executeQuery();
     }
     
     private int u_id;
      void addToUser(String name,long mobile,String dob,String gender,int branch){
        try{
            Date date=Date.valueOf(dob);
            pst=con.prepareStatement("insert into users values(default,5,?,?,?,?,?,default,default) returning id");
            pst.setString(1,name);
            pst.setLong(2,mobile);
            pst.setDate(3,date);
            pst.setString(4,gender);
            pst.setInt(5,branch);
            rs=pst.executeQuery();
            if(rs.next()){
                u_id=rs.getInt("id");
                System.out.println("New User registered successfully");
            }      
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
     ResultSet checkAadhar(long aadhar) throws SQLException{
    	return st.executeQuery("select s.name from customers c join schemes s on s.id=c.scheme_id where c.aadhar="+aadhar);
    }
    
     ResultSet checkAadhar(long aadhar,String scheme) throws SQLException{
    	return st.executeQuery("select s.name,c.scheme_id from customers c join schemes s on s.id=c.scheme_id where c.aadhar="+aadhar+" and s.name='"+scheme+"'");
    }
    
     ResultSet findScheme(String scheme) throws SQLException{
    	return st.executeQuery("select id from schemes where upper(name)=upper('"+scheme+"')");
    }
    
     int findAge(String dob) throws SQLException{
        rs=st.executeQuery("SELECT DATE_PART('year', AGE(NOW(), '"+dob+"'::timestamp with time zone))");
        rs.next();
        return rs.getInt(1);
    }   
    
      ResultSet findAccount(String acc_type) throws SQLException{
    	 return st.executeQuery("SELECT id FROM schemes WHERE UPPER(name) = UPPER('" + acc_type + "')");
     }

      void addToCutomer(long aadhar,String address,int accountType,int categoryid){
        try{
            pst=con.prepareStatement("insert into customers values(default,?,?,?,?,default,default,default,?,default) returning id");
            pst.setInt(1,u_id);
            pst.setLong(2,aadhar);
            pst.setInt(3,accountType);
            pst.setInt(4,categoryid);
            pst.setString(5,address);
            rs=pst.executeQuery();
            if(rs.next()){
                System.out.println("New customer registered successfully and id : "+u_id);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
