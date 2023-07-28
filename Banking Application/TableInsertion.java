package com.zoho.banking;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
class TableInsertion
{    
     private static Connection con;
     private static ResultSet rs;
     private static Statement st;
     private static PreparedStatement ps;
     
     static
     {
         if(con==null){
            try{
            	con=Database.getInstance().getConnection();
            	st=con.createStatement();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
         }
     }
     
      static void insertData(){
         if(checkValues("branches")==0)
         {
         	addBranches();
         }
         if(checkValues("schemes")==0)
         {
         	addSchemes();
         }
         if(checkValues("categories")==0)
         {
         	addCategories();
         }
         if(checkValues("roles")==0)
         {
            addRoles();
         }  
         if(checkValues("users")==0)
         {
            addUser();
         }         
     }
     
     private static void addBranches()
     {
           try
             {
                   st.executeUpdate("INSERT INTO branches (name,ifsc) VALUES ('RJPM','INDIAN00001'),('SRIVI','INDIAN00002'),('SIVAKASI','INDIAN00003'),('MADURAI','INDIAN00004'),('TENKASI','INDIAN00005')");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Branches Not Added");
             } 
     }
     
     private static void addSchemes()
     {
           try
             {
                   st.executeUpdate("INSERT INTO schemes (name) VALUES ('savings'),('current')");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Schems Not Added");
             } 
     }

     private static void addCategories()
     {
           try
             {
                   st.executeUpdate("INSERT INTO categories (name) VALUES ('Child'),('Teenager'),('Adult'),('Senior')");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Categories Not Added");
             } 
     }
     
     private static void addRoles()
     {
       try
       {
            st.executeUpdate("INSERT INTO roles (name) VALUES ('Admin'),('Manager'),('Clerk'),('Cashier'),('Customer')");
       }
       catch(SQLException e)
             {
                   System.out.println("Roles Not Added");
             } 
     }
     
     private static void addUser() {
         try {
             st.executeUpdate("INSERT INTO users VALUES(default,1,'Kanna','7397109294','2001-06-15','male',1,'Kanna@123','active'),(default,2,'Murugan','9999999999','2001-10-25','male',2,'Murugan@123','active'),(default,2,'Selva','8888888888','2001-6-21','male',3,'Selva@123','active'),(default,2,'Ramesh','7777777777','1998-6-14','male',4,'Ramesh@123','active'),(default,2,'Ramya','6666666666','2000-8-19','female',5,'Ramya@123','active'),(default,5,'Vignesh',9888888888,'2000-01-16','male',2,'Vignesh@123','active'),(default,5,'Dinesh',7888888888,'2000-01-16','male',2,'Dinesh@123','active'),(default,5,'Divya',6888888888,'2000-01-16','female',3,'Divya@123','active'),(default,4,'Meena',7788888888,'2001-06-16','female',2,'Meena@123','active'),(default,3,'Reena',7788768888,'2001-06-16','female',3,'Reena@123','active');");
             st.executeUpdate("INSERT INTO customers VALUES(default,6,989897876765,2,1,'0020000001',1000,default,'srivi','manager_approved'),(default,7,989897876765,1,1,'0020000002',10000,default,'srivi','manager_approved'),(default,8,989897875665,1,1,'0030000003',0,default,'sivakasi','manager_approved'),(default,6,989897876765,1,1,'0020000004',0,default,'srivi','manager_approved')");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     private static int checkValues(String tablename)
     {
         int count=0;
         try
           {
                 ps=con.prepareStatement("SELECT COUNT(id) FROM "+tablename);
                 rs=ps.executeQuery();
                 rs.next();
                 count=rs.getInt(1);
           }
         catch(SQLException e)
           {
                e.printStackTrace();
           }
        return count;
     }   
}
