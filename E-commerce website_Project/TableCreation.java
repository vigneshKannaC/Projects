package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;
class TableCreation
{     
     public static Statement st=null;
     public void tableOrder()throws Exception
     {
         if(st==null) st=Database.getInstance().getConnection().createStatement();
         brandsTable();
         modelsTable();
         productsTable();
         vendorsTable();
         productDetailsTable();
         usersTable();
         userDetailsTable();
         ordersTable();
         adminTable();
     }
     private void brandsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists brands(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("brands Table Not Created");
             } 
     }
     private void modelsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists models(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("models Table Not Created");
             } 
     }
     private void productsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists products(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("product Table Not Created");
             } 
     }

      private void vendorsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists vendors(id serial primary key,name varchar not null,password varchar not null,mobile_no bigint not null,age int not null,gender varchar not null,status varchar default 'registered')");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("vendors Table Not Created");
             } 
     }
     
     
     private void productDetailsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists product_details(id serial primary key,vendor_id int,brand_id int not null,model_id int not null,product_id int not null,quantity int not null,price numeric(15,2) not null,foreign key(vendor_id) references vendors(id) on delete cascade,foreign key(brand_id) references brands(id) on delete cascade,foreign key(model_id) references models(id) on delete cascade,foreign key(product_id) references products(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("productDetails Table Not Created");
             } 
     }
     
     private void usersTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists users(id serial primary key,name varchar not null,password varchar not null,mobile_no bigint not null,age int not null,gender varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("user Table Not Created");
             } 
     }
     
     private void userDetailsTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists user_details(id serial primary key,user_id int not null,address varchar not null,foreign key(user_id) references users(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("userDetails Table Not Created");
             } 
     }
     
     private void ordersTable(){
     	try
              {
                   st.executeUpdate("create table if not exists orders(id serial primary key,user_id int not null,product_id int not null,address varchar not null,ordered_date date default now(),due_date date generated always as(ordered_date+interval'4 days')stored,payment_mode varchar not null,status varchar default 'ordered',received_date date,dead_date date generated always as(received_date+interval'15 days')stored,quantity int not null,total_amount decimal(15,2),foreign key(user_id) references users(id) on delete cascade,foreign key(product_id) references product_details(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("orders Table Not Created");
             } 
     
     }
     
     private void adminTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists admins(id serial primary key,name varchar not null,password varchar not null,mobile_no bigint not null,age int not null,gender varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("admin Table Not Created");
             } 
     }
}