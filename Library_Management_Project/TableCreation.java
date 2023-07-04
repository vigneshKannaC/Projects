package com.zoho.Library;
import java.sql.SQLException;
import java.sql.Statement;
class TableCreation
{
     
     public static Statement st=null;
     public void tableOrder()throws Exception
     {
         if(st==null) st=Database.getInstance().getConnection().createStatement();
         booksTable();
         categoryTable();
         authorTable();
         bookDetails();
         barrowDetails();
         reservationDetails();
         borrowedHistoryTable();
     }
     private void booksTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists books(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("booksTable Not Created");
             } 
     }
     private void authorTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists authors(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("authorTable Not Created");
             } 
     }
     private void categoryTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists categories(id serial primary key,name varchar not null)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("categoryTable Not Created");
             } 
     }

     private void bookDetails()
     {
           try
             {
                   st.executeUpdate("create table if not exists book_details(id serial primary key,b_id int not null,a_id int not null,c_id int,quantity int,reserved_count int default 0,foreign key(b_id) references Books(id) on delete cascade,foreign key(a_id) references Authors(id) on delete cascade,foreign key(c_id) references Categories(id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("bookDetails Not Created");
             } 
     }
     private void barrowDetails()
     {
           try
             {
                   st.executeUpdate("create table if not exists borrowers(borrower_id serial primary key,name varchar not null,mobile_no bigint not null,count int default 0)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("barrowDetails Not Created");
             } 
     }
     private void reservationDetails()
     {
           try
             {
                   st.executeUpdate("create table if not exists reservations(reserved_id serial primary key,book_id int,borrower_id int,reserved_date date,foreign key(book_id) references Book_details(id) on delete cascade,foreign key(borrower_id) references Borrowers(borrower_id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("barrowDetails Not Created");
             } 
     }
     
          private void borrowedHistoryTable()
     {
           try
             {
                   st.executeUpdate("create table if not exists borrowed_history(history_id serial primary key,book_id int,borrower_id int,borrowed_date date default now(),due_date date generated always as(borrowed_date+interval'15 days')stored,returned_date date,foreign key(book_id) references Book_details(id) on delete cascade,foreign key(borrower_id) references Borrowers(borrower_id) on delete cascade)");                                
             }
             
         catch(SQLException e)
             {
                   System.out.println("borrowedHistoryTable Not Created");
             } 
     }
}
