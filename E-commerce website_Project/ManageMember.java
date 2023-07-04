package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
class ManageMember {

     private static Connection con=null;
     private static ResultSet rs=null;
     private static Statement st=null;
     private static PreparedStatement ps=null;

     int id;
     String name;
     public void addUser(String name,long mobile,String password,int age,String gender,String address){
      try{
         con=Database.getInstance().getConnection();
         ps=con.prepareStatement("insert into users values(default,?,?,?,?,?)returning id");
         ps.setString(1,name);
         ps.setString(2,password);
         ps.setLong(3,mobile);
         ps.setInt(4,age);
         ps.setString(5,gender);
         rs=ps.executeQuery();
               if(rs.next()) id=rs.getInt("id");
		
         ps=con.prepareStatement("insert into user_details values(default,?,?)");
         ps.setInt(1,id);
         ps.setString(2,address);
         ps.executeUpdate();
      }
      catch(SQLException e){
         e.printStackTrace();
      }
     }
     public void addMember(String member,String name,long mobile,String password,int age,String gender) throws SQLException{
      try{
         con=Database.getInstance().getConnection();
         ps=con.prepareStatement("insert into "+member+" values(default,?,?,?,?,?)");
         ps.setString(1,name);
         ps.setString(2,password);
         ps.setLong(3,mobile);
         ps.setInt(4,age);
         ps.setString(5,gender);
         ps.executeUpdate();
      }
      catch(SQLException e){
         e.printStackTrace();
     }
     }
     
     
     public boolean validateMember(String member,long mobile) throws SQLException{
         con=Database.getInstance().getConnection();
         ps=con.prepareStatement("select id from "+member+" where mobile_no=?");
         ps.setLong(1,mobile);
         rs=ps.executeQuery();
         if(rs.next()) return true;
         else return false;		   
     }
     
     //overloading
     public int validateMember(String member,String pass) throws SQLException{
         con=Database.getInstance().getConnection();
         id=-1;
         ps=con.prepareStatement("select id from "+member+" where password=?");
         ps.setString(1,pass);
         rs=ps.executeQuery();
         if(rs.next()) id=rs.getInt(1);
         return id;   
     }

     public String isEligibleVendor(Vendor vendor) throws SQLException{
         con=Database.getInstance().getConnection();
         st=con.createStatement();
         rs=st.executeQuery("select status from vendors where id="+vendor.getId());
         rs.next();
         if(rs.getString(1).equals("approved")) return "approved";
         else if(rs.getString(1).equals("declined")) return "declined";
         else return "registered";
     }

     public <T> T getMember(String t_name,int id) throws SQLException{
        con=Database.getInstance().getConnection();
            if(t_name.equals("vendors")){
                  ps=con.prepareStatement("select * from vendors where id=?");
           		  ps.setInt(1,id);
      		      rs=ps.executeQuery();
                  if(rs.next()) return (T)new Vendor(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6),rs.getString(7));
            }else if(t_name.equals("users")){
                  String address=null;
                  ps=con.prepareStatement("select address from user_details where id=?");
                  ps.setInt(1,id);
      		      rs=ps.executeQuery();
                  if(rs.next()) address=rs.getString(1);
                  ps=con.prepareStatement("select * from users where id=?");
           		  ps.setInt(1,id);
      		      rs=ps.executeQuery();
                  if(rs.next()) return (T)new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6),address);
            }
            else{
                    ps=con.prepareStatement("select * from admins where id=?");
           		    ps.setInt(1,id);
      		        rs=ps.executeQuery();
                    if(rs.next()) return (T)new Admin(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6));
            }
            return null;
     }
}