package com.zoho.Library;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
class TableInsertion
{
     private static Statement st=null;
     private static ResultSet resultset=null;
     private static PreparedStatement ps=null;
     public void insertOrder()throws Exception
     {
         if(st==null)
         st=Database.getInstance().getConnection().createStatement();
         if(checkValues("books")==0)
         {
         	addBooks();
         }
         if(checkValues("authors")==0)
         {
         	addAuthors();
         }
         if(checkValues("categories")==0)
         {
         	addCategory();
         }
         if(checkValues("book_details")==0)
         {
         	addBookDetails();
         }
     }
     
     private void addBooks()
     {
           try
             {
                   st.executeUpdate("INSERT INTO books (name)VALUES ('The Great Gatsby'),('To Kill a Mockingbird'),('1984'),('Pride and Prejudice'),('The Catcher in the Rye'),('The Hobbit'),('The Lord of the Rings'),('Harry Potter and the Philosopher''s Stone'),('The Chronicles of Narnia'),('Animal Farm'),('Brave New World'),('The Da Vinci Code'),('The Alchemist'),('The Hunger Games'),('Gone with the Wind'),('The Kite Runner'),('The Fault in Our Stars'),('The Shining'),('The Adventures of Tom Sawyer'),('Jane Eyre'),('Moby-Dick'),('The Odyssey'),('Alice''s Adventures in Wonderland'),('The Picture of Dorian Gray'),('The Count of Monte Cristo'),('Frankenstein'),('Wuthering Heights'),('Don Quixote'),('War and Peace'),('The Divine Comedy'),('Hamlet');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("books Not Added");
             } 
     }
     
     private void addAuthors()
     {
           try
             {
                   st.executeUpdate("insert into authors (name)values('f. scott fitzgerald'),('harper lee'),('george orwell'),('jane austen'),('j.d. salinger'),('j.r.r. tolkien'),('j.k. rowling'),('c.s. lewis'),('george orwell'),('aldous huxley'),('dan brown'),('paulo coelho'),('suzanne collins'),('margaret mitchell'),('khaled hosseini'),('john green'),('stephen king'),('mark twain'),('charlotte bronte'),('herman melville'),('homer'),('lewis carroll'),('oscar wilde'),('alexandre dumas'),('mary shelley'),('emily bronte'),('miguel de cervantes'),('leo tolstoy'),('dante alighieri'),('william shakespeare');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Authors Not Added");
             } 
     }
     
     private void addBookDetails()
     {
           try
             {
                   st.executeUpdate("INSERT INTO book_details (b_id, a_id, c_id,quantity) VALUES (3, 3, 1, 5),(4, 4, 2, 3),(5, 5, 3, 2),(6, 6, 4, 8),(7, 7, 5, 10),(8, 8, 6, 4),(9, 9, 7, 6),(10, 10, 8, 3),(11, 11, 9, 7),(12, 12, 1, 9),(13, 13, 2, 2),(14, 14, 3, 5),(15, 15, 4, 3),(16, 16, 5, 7),(17, 17, 6, 2),(18, 18, 7, 8),(19, 19, 8, 5),(20, 20, 9, 7),(21, 21, 1, 4),(22, 22, 2, 6),(23, 23, 3, 3),(24, 24, 4, 9),(25, 25, 5, 5),(26, 26, 6, 6),(27, 27, 7, 3),(28, 28, 8, 7),(29, 29, 9, 4),(30, 30, 1, 8);");                                
             } 
         catch(SQLException e)
             {
                   System.out.println("BookDetails Not Added");
             } 
     }
     
     private void addCategory()
     {
           try
             {
                   st.executeUpdate("INSERT INTO categories (name) VALUES ('Fiction'),('Non-Fiction'),('Mystery'),('Science Fiction'),('Romance'),('Biography'),('History'),('Thriller'),('Fantasy');");                                
             }             
         catch(SQLException e)
             {
                   System.out.println("Category Not Added");
             } 
     }
     
     private static int checkValues(String tablename)
     {
         int count=0;
         try
           {
                 ps=Database.getInstance().getConnection().prepareStatement("SELECT COUNT(id) FROM "+tablename+";");
                 resultset=ps.executeQuery();
                 resultset.next();
                 count=resultset.getInt("count");
            
           }
         catch(SQLException e)
           {
                e.printStackTrace();
           }
        return count;
     }
     
}


