package com.zoho.Library;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class UpdateBook
{
	private ResultSet rs=null;
	private PreparedStatement ps=null;
	public void addBooks()throws SQLException
	{
		BookModel books=null;
		String bookName="",autherName="",categoryName="";
		int count=0;
		while(true)
		{
			System.out.print("\n\t\tEnter Book Name:");
			Library.sc.nextLine();
			bookName=Library.sc.nextLine();
			System.out.print("\t\tEnter Author Name:");
			autherName=Library.sc.nextLine();
			System.out.print("\t\tEnter Category Name:");
			categoryName=Library.sc.nextLine();
			System.out.print("\t\tEnter Book Count:");
			count=Library.sc.nextInt();
			System.out.println("\nBOOKNAME:"+bookName+"\nAUTHORNAME:"+autherName+"\nCATEGORY NAME:"+categoryName+"\nBOOK COUNR:"+count);
			System.out.println("press-'y' for confirm");
			if(Library.sc.next().equals("y"))
			{
				books=new BookModel(bookName,autherName,categoryName,count);
				break;
			}
		}
		int bid=0,aid=0,cid=0,id=0;	
		bid=new Controller().isExists("books",books.getBookName().trim());
		aid=new Controller().isExists("authors",books.getAuthorName().trim());
		cid=new Controller().isExists("categories",books.getCategoryName().trim());
		id=new Controller().isExists(bid,aid,cid);
		if(id==0)
		{
			try
			{
				ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO Book_details (b_id, a_id, c_id,quantity) VALUES(?,?,?,?)RETURNING Id;");
				ps.setInt(1,bid);
				ps.setInt(2,aid);
				ps.setInt(3,cid);
				ps.setInt(4,count);
				rs=ps.executeQuery();
				rs.next();
				bid=rs.getInt("Id");
				if(bid!=-1)
					System.out.println("Book Added Sucessfully");
			}
			catch(SQLException e)
			{
					e.printStackTrace();
			} 
					
		}
		else
		{
			try
			{
				StatementSingleton.getStatement().executeUpdate("update book_details set quantity=(select quantity from book_details where id="+id+")+"+count+" where id="+id);
				System.out.println("Book Added Sucessfully");
			}
			catch(SQLException e)
			{
					e.printStackTrace();
			}   
		}   
	}
}


