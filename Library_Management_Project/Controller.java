package com.zoho.Library;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
class Controller
{
    	private static ResultSet resultset=null;
	private static PreparedStatement ps=null;
    public int isExists(int n1,int n2,int n3)
    {
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select id from book_details where b_id="+n1+" And a_id="+n2+" And c_id="+n3+";");
            if(resultset.next())
               return resultset.getInt("id");
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
         return 0;     
    }
    //OverLoading
    public int isExists(String tablename,String book)
    {
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select id from "+tablename+" where name='"+book+"';");
            if(resultset.next())
                return resultset.getInt("id");
            else
             {
                ps=Database.getInstance().getConnection().prepareStatement("INSERT INTO "+tablename+" (name) VALUES(?)RETURNING Id;");
                ps.setString(1,book);
                resultset=ps.executeQuery();
                resultset.next();
                return resultset.getInt("id");
             }
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
        return 0;     
    }
    /*public UserModel isValidUserId(int id)
    {
        UserModel user=null;
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select name,address,mobile,gender from user_details where id="+id+";");
            if(resultset.next())
                 user= new UserModel(resultset.getString("name"),resultset.getString("address"),resultset.getLong("mobile"),resultset.getString("gender"));
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
         return user;  
    }
    //Overloading
    public UserModel isValidUserId(int id,LinkedHashMap<Integer,BarrowModel> barrow)
    {
        UserModel user=null;
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select name,address,mobile,gender from user_details where id="+id+" and status=true;");
            if(resultset.next())
            {
                 user= new UserModel(resultset.getString("name"),resultset.getString("address"),resultset.getLong("mobile"),resultset.getString("gender"));
                 resultset=StatementSingleton.getStatement().executeQuery("select book_id,barrow_date,returning_date from barrow_details where user_id="+id+" AND return_status='false';");
                 while(resultset.next())
                 {
                    barrow.put(resultset.getInt(1),new BarrowModel(resultset.getDate(2).toString(),resultset.getDate(3).toString()));
                 }
            }
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
         return user;  
    }
    public int isEligible(int id)
    {
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select count(*) from barrow_details where user_id="+id+" and return_status='false';");
            if(resultset.next())
                return resultset.getInt("count");
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
         return 0;     
    }
    public int isValidBook(int id,int bid,boolean flag)
    {
        int c=0;
        boolean chk=false;
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select exists(select count from book_details where id="+bid+");");
            resultset.next();
            chk=resultset.getBoolean("exists");
            if(chk)
            {
                resultset=StatementSingleton.getStatement().executeQuery("select count from book_details where id="+bid+";");
                if(resultset.next())
                {
                    c=resultset.getInt("count");
                    if(flag)
                    return c;
                    else
                    {
                        resultset=StatementSingleton.getStatement().executeQuery("select Exists(select book_id from barrow_details where book_id="+bid+" and user_id="+id+" and return_status=false);");
                        resultset.next();
                        if(resultset.getBoolean("exists"))
                         throw new DataNotFoundException("BOOK ALREADY IN HAND!!!!!!");
                        if(c>=1)
                        {
                            StatementSingleton.getStatement().executeUpdate("update book_details set count=(select count from book_details where id="+bid+")-"+1+" where id='"+bid+"';");
                            resultset=StatementSingleton.getStatement().executeQuery("select id from reservation where user_id="+id+" and book_id="+bid+" and status='approved';");
                            if(resultset.next())
                            {
                                c=resultset.getInt("id");
                                if(c!=0)
                                StatementSingleton.getStatement().executeUpdate("update reservation set status='delivered' where id="+c+";");
                            }
                        } 
                    }
                }
            }
            else 
              throw new DataNotFoundException("BOOK NOT AVAILABLE!!!!!!");
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
        return c;     
    }
    public void bookReturning(int id)
    {
        try
        {
            StatementSingleton.getStatement().executeUpdate("update barrow_details set return_status='true' where book_id="+id+";");
            StatementSingleton.getStatement().executeUpdate("update book_details set count=(select count from book_details where id="+id+")+1 where id='"+id+"';");
            StatementSingleton.getStatement().executeUpdate("update reservation set status='approved' where book_id="+id+" and status='pending';");
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
    } 
    public void isReserveApproved(int id)
    {
        try
        {
            resultset=StatementSingleton.getStatement().executeQuery("select count(*) from reservation where status='approved' and user_id="+id+";");
            if(resultset.next())
            {
                if(resultset.getInt("count")>=1)
                {
                    System.out.println("\n\t\t\t--------------------------------");
                    System.out.println(String.format("\t\t\t|%-30s|","APPROVED RESERVAL BOOK ID"));
                    System.out.println("\t\t\t--------------------------------");
                    resultset=StatementSingleton.getStatement().executeQuery("select book_id from reservation where status='approved' and user_id="+id+";");
                    while(resultset.next())
                    {
                        System.out.println(String.format("\t\t\t|%-30s|",resultset.getInt(1)));
                    }
                    System.out.println("\t\t\t--------------------------------\n");
                }
            }
        }
        catch(SQLException e)
        {
                 e.printStackTrace();
        }
    } */
}
