package com.zoho.Library;
public class BookModel
{
      private String bookname;
      private String authorname;
      private String categoryname;
      private int bookcount;
      
      public BookModel(String bookname,String authorname,String categoryname,int bookcount)
      {
                    this.bookname=bookname;
                    this.authorname=authorname;
                    this.categoryname=categoryname;
                    this.bookcount=bookcount;
      }
      
      public String getBookName()
      {
              return bookname;
      }
      public void setBookName(String bookname)
      {
              this.bookname=bookname;
      }
      public String getAuthorName()
      {
              return authorname;
      }
      public void setAuthorName(String authorname)
      {
              this.authorname=authorname;
      }
      public String getCategoryName()
      {
              return categoryname;             
      }
      public void setCategoryName(String categoryname)
      {  
              this.categoryname=categoryname;
      }
      public int getBookCount()
      {
              return bookcount;             
      }
      public void setBookCount(int bookcount)
      {  
              this.bookcount=bookcount;
      }
}
