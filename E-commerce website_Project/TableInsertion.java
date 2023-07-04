package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
class TableInsertion
{    
     private static Connection con=null;
     private static ResultSet rs=null;
     private static Statement st=null;
     private static PreparedStatement ps=null;
     
     public void insertOrder()throws Exception
     {
         if(con==null){
            con=Database.getInstance().getConnection();
            st=con.createStatement();
         }
         
         if(checkValues("brands")==0)
         {
         	addBrands();
         }
         if(checkValues("models")==0)
         {
         	addModels();
         }
         if(checkValues("products")==0)
         {
         	addProducts();
         }
         if(checkValues("vendors")==0)
         {
            addVendors();
         }
         if(checkValues("product_details")==0)
         {
         	addProductDetails();
         }
         if(checkValues("admins")==0)
         {
            addAdmins();
         }
     }
     
     private void addBrands()
     {
           try
             {
                   st.executeUpdate("INSERT INTO brands (name) VALUES('Samsung'),('Apple'),('Sony'),('Dell'),('LG'),('Google'),('Nokia'), ('Motorola'), ('OnePlus'), ('Vivo');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Brands Not Added");
             } 
     }
     
     private void addModels()
     {
           try
             {
                   st.executeUpdate("INSERT INTO models (name) VALUES('Galaxy S21 Ultra'),('iPhone 12 Pro Max'),('WH-1000XM4'),('XPS 15'),('OLED CX'),('Pixel 6'), ('Nokia G21'), ('Motorola Edge 30 Pro'), ('OnePlus 10 Pro'), ('Vivo X80');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Models Not Added");
             } 
     }

     private void addProducts()
     {
           try
             {
                   st.executeUpdate("INSERT INTO products (name) VALUES('Smartphone'),('Laptop'),('Wireless Headphones'),('Smart TV'),('Noise Cancelling Headphones'),('Pixel 6 Pro'), ('Nokia G21 Plus'), ('Motorola Edge 30 Ultra'), ('OnePlus 10 Ultra'), ('Vivo X80 Pro');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Products Not Added");
             } 
     }
     
     private void addVendors()
     {
       try
       {
            st.executeUpdate("INSERT INTO vendors (name, password, mobile_no, age, gender) VALUES('Ramesh', 'Ramesh@123', 9999999999, 30, 'Male'),('Jane Smith', 'JaneSmith@456', 8888888888, 28, 'Female'),('Murugan', 'Murugan@789', 7777777777, 35, 'Male'),('Sarah Adams', 'SarahAdams@123', 9876543210, 32, 'Female'),('David Brown', 'DavidBrown@456', 8765432109, 27, 'Male'),('Mary Smith', 'password678', 7890123456, 35, 'female'), ('David Jones', 'password901', 1234567890, 40, 'male'), ('Sarah Brown', 'password234', 9876543210, 25, 'female'), ('William Doe', 'password567', 5678901234, 30, 'male'), ('Elizabeth Doe', 'password890', 3456789012, 20, 'female');");
       }
       catch(SQLException e)
             {
                   System.out.println("Vendors Not Added");
             } 
     }
     
     private void addProductDetails()
     {
           try
             {
                   st.executeUpdate("INSERT INTO product_details (vendor_id, brand_id, model_id, product_id, quantity, price) VALUES(1, 1, 1, 1, 10, 9999.99),(2, 2, 2, 2, 5, 12999.99),(3, 3, 3, 3, 8, 2499.99),(4, 4, 4, 4, 12, 17999.99),(5, 5, 5, 5, 3, 3499),(6, 6, 6, 6, 10, 50999), (7, 7, 7, 7, 15, 13199), (8, 8, 8, 8, 10, 44999), (9, 9, 9, 9, 5, 59999), (10, 10, 10, 10, 15, 37990);");                                
             } 
         catch(SQLException e)
             {
                   System.out.println("ProductDetails Not Added");
             } 
     }

     private void addAdmins()
     {
       try
       {
            st.executeUpdate("INSERT INTO admins (name, password, mobile_no, age, gender) VALUES('Kanna', 'Kanna@123', 7397109294, 23, 'Male')");
       }
       catch(SQLException e)
             {
                   System.out.println("Admins Not Added");
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