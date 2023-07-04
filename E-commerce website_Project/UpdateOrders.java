package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class UpdateOrders {

    private static Connection con=null;
    private static ResultSet rs=null;
    private static Statement st=null;

    static{
        try{
            con=Database.getInstance().getConnection();
            st=con.createStatement();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    int id,quantity,product_id;
    String ordered_date;

    public boolean updateOrder(boolean cancel) throws Exception{
        System.out.print("Enter the order id : ");
        id=new Validation().isNumeric(Website.sc.next());
        if(!new ManageProducts().validateId("orders", id)){
            System.out.println("Invalid order_id!");
            return false;
        }
        else{
            if(cancel){
                rs=st.executeQuery("select * from orders where id="+id+" and ordered_date<=now() and due_date>=now() and status='ordered'");
            }
            else{
                rs=st.executeQuery("select * from orders where id="+id+" and status='received' and received_date<=now() and dead_date>=now()");
            }
            if(rs.next()){
                rs=st.executeQuery("select product_id,quantity from orders where id="+id);
                if(rs.next()){
                    product_id=rs.getInt(1);
                    quantity=rs.getInt(2);
                }
                st.executeUpdate("update product_details set quantity=(quantity+"+quantity+") where id="+product_id);
                st.executeUpdate("update orders set status='cancelled' where id="+id);
                return true;
            }
            else{
                if(cancel) System.out.println("Sorry!You can't cancel the order right now!");
                else System.out.println("Sorry!You can't return the order!");
            }
        }
        return false;
    }

    public void updateDate() {
        try {
            rs = st.executeQuery("SELECT id, ordered_date FROM orders");
            while (rs.next()) {
                id = rs.getInt(1);
                ordered_date = rs.getString(2);
                PreparedStatement updateStatement = con.prepareStatement("UPDATE orders SET status = 'received' WHERE id = ? AND now() >= (?::date + interval '4 days') AND status != 'cancelled'");
                updateStatement.setInt(1, id);
                updateStatement.setString(2, ordered_date);
                updateStatement.executeUpdate();
    
                PreparedStatement update=con.prepareStatement("UPDATE orders SET received_date = now() WHERE id =? AND status != 'cancelled'");
                update.setInt(1, id);
                update.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}