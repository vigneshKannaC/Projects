package com.zoho.Library;
import java.util.ArrayList;
import java.sql.ResultSet;
class ListBooks{

    ArrayList<BDetails> details=new ArrayList<BDetails>();
    ResultSet rs=null;
    public  void booksList()throws Exception{	
    		boolean flag=true;
		while(flag){	
		   System.out.println("---------------------------------------------\n\t1.Show Books Names\n\t2.Show Authors Names\n\t3.Show Genres Names\n\t4.Show All\n\t5.Search Book\n\t6.back\n---------------------------------------------");
		   System.out.print("Enter number(1-6) :");
		   switch(new Validation().isNumeric(Library.sc.next())){
			case 1:
        	  		showBooks();
        	  		break;
        		case 2:
        			System.out.println(String.format("\t%-5s %-20s","Id","Authors"));
        			showAuthors();
        			break;
        		case 3:
        			System.out.println(String.format("\t%-5s %-20s","Id","Genres"));
        			showGenres();
        			break;
        		case 4: 
        			System.out.println(String.format("\t%-5s %-50s %-25s %-35s %-5s","Id","Book Name","Author Name","Genre","Quantity"));
        	        	showAll();
        	        	break;
        	        
			case 5:
				searchBook(); 	
				break;
			case 6:       		
        			flag=false;
        			break;
        			
        		default: 
        			 System.out.println("Give Valid Input!!!!!!!!!");
				 break;
		    }			
		}
    }
    public  void showBooks() throws Exception{
     		rs=StatementSingleton.getStatement().executeQuery("select * from books");
     		System.out.println(String.format("\t%-5s %-50s","Id","Name"));
     		while(rs.next()){
       			System.out.println(String.format("\t%-5s %-50s",rs.getInt(1),rs.getString(2)));
       		}
     }
     
     public void showAuthors() throws Exception{
     		rs=StatementSingleton.getStatement().executeQuery("select * from authors");
     		while(rs.next()){
     			System.out.println(String.format("\t%-5s %-30s",rs.getInt(1),rs.getString(2)));
       		}
     }
     
     
     	public  void showGenres()throws Exception{
     		rs=StatementSingleton.getStatement().executeQuery("select * from categories");
     		while(rs.next()){
     			System.out.println(String.format("\t%-5s %-30s",rs.getInt(1),rs.getString(2)));
       		}
     	}
   		
	 public void showAll() throws Exception{
		rs=StatementSingleton.getStatement().executeQuery("select d.id,b.name,a.name,c.name,d.quantity from book_details d join books b on d.b_id=b.id join authors a on d.a_id=a.id join categories c on c.id=d.c_id");     		
     		while(rs.next()){ 
       			System.out.println(String.format("\t%-5s %-50s %-25s %-35s %-5s ",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)));
       		}
	 }
	 
	 public void searchBook() throws Exception{
      		System.out.print("Enter the book name : ");
      		String name=Library.sc.next().toUpperCase();
      		System.out.println(String.format("\t%-5s %-50s %-30s %-15s %-20s %-5s","Id","Book Name","Author Name","Year Of Edition","Genre","Quantity"));
           	rs=StatementSingleton.getStatement().executeQuery("select d.id,b.name,a.name,b.b_edition,c.name,d.quantity from book_details d join books b on d.b_id=b.id join authors a on d.a_id=a.id join categories c on c.id=d.c_id where upper(b.name) like '"+name+"%'");
           	while(rs.next()){
           		System.out.println(String.format("\t%-5s %-50s %-30s %-15s %-20s %-5s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getInt(6)));
           	}
    	}
    	
}
