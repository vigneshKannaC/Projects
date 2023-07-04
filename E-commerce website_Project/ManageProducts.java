package com.zoho.ecommerce;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

class ManageProducts {

    private static Connection con = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private static Statement st = null;
    HashMap<Integer, Product> map = new HashMap<Integer, Product>();
    List<Order> orderList = null;

    public void viewProducts(User user) {
        try{
            con = Database.getInstance().getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select d.id,b.name,m.name,p.name,d.quantity,d.price from product_details d join brands b on d.brand_id=b.id join models m on d.model_id=m.id join products p on d.product_id=p.id");
            while (rs.next()) {
                map.put(rs.getInt(1), new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
            }
            Set<Map.Entry<Integer, Product>> set = map.entrySet();
            Iterator<Map.Entry<Integer, Product>> itr = set.iterator();
            int count = 1;
            while (count <= map.size()) {
                System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "id", "brand", "model", "product", "quantity", "price"));
                System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "--", "-----", "-----", "-------", "--------", "-----"));
                int val = 0;
                while (itr.hasNext() && val < 5) {
                    Map.Entry<Integer, Product> entry = itr.next();
                    System.out.println(entry.getValue());
                    if (count == map.size()) {
                        System.out.println("              <---------------------------------------END------------------------------------------------------->");
                    }
                    count++;
                    val++;
                }
                System.out.println();
                while (true) {
                    System.out.println("\n\t1.Go to next\t2.search Product\t3.Order Product\t\t4.Go Back");
                    System.out.println();
                    System.out.print("Enter your choice(1-4): ");
                    int num = new Validation().isNumeric(Validation.reader.readLine());
                    if (num == 1)
                        break;
                    else if (num == 2)
                        searchProduct();
                    else if (num == 3)
                        orderProduct(user);
                    else if (num == 4)
                        return;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    int id, quantity, count;
    double total_amount;
    String payment_mode, address;

    private void searchProduct(){
        try{
            con = Database.getInstance().getConnection();
            st = con.createStatement();
            System.out.print("Enter the product/model/brand name  : ");
            String name=Validation.reader.readLine();
            rs = st.executeQuery("select d.id,b.name,m.name,p.name,d.quantity,d.price from product_details d join brands b on d.brand_id=b.id join models m on d.model_id=m.id join products p on d.product_id=p.id where upper(b.name) like upper('"+name+"%') or upper(m.name) like upper('"+name+"%') or upper(p.name) like upper('"+name+"%');");
            System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "id", "brand", "model", "product", "quantity", "price"));
            System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "--", "-----", "-----", "-------", "--------", "-----"));
        
            while(rs.next()) {
                System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s",rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void orderProduct(User user) throws Exception {
        try {
            Order order = null;
            System.out.print("Enter the product id:");
            id = new Validation().isNumeric(Validation.reader.readLine());
    
            if (validateId("product_details", id)) {
                rs = st.executeQuery("select quantity from product_details where product_id=" + id);
                if (rs.next())
                    count = rs.getInt(1);
    
                System.out.print("Enter the payment mode(GPay/PhonePay/Paytm/COD):");
                payment_mode = Validation.reader.readLine();
    
                address = updateAddress(user);
    
                System.out.print("Enter no of product : ");
                quantity = new Validation().isNumeric(Validation.reader.readLine());
    
                if (quantity > count) {
                    System.out.println("Sorry!Insufficient quantity");
                } else {
                    total_amount = (quantity * map.get(id).getPrice());
    
                    st.executeUpdate("update product_details set quantity=(quantity-" + quantity + ") where product_id=" + id);
                    ps = con.prepareStatement("insert into orders values(default,?,?,?,default,default,?,default,default,default,?,?)returning id");
                    ps.setInt(1, user.getId());
                    ps.setInt(2, id);
                    ps.setString(3, address);
                    ps.setString(4, payment_mode);
                    ps.setInt(5, quantity);
                    ps.setDouble(6, total_amount);
                    rs = ps.executeQuery();
                    int ordered_id = -1;
                    if (rs.next())
                        ordered_id = rs.getInt("id");
                    rs = st.executeQuery("select * from orders where id=" + ordered_id);
                    if (rs.next()) {
                        order = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getDouble(12));
                        if (orderList == null) {
                            orderList = new ArrayList<Order>();
                        }
                        orderList.add(order);
                    }
                    System.out.println("Ordered product : ");
                    System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-10s %-30s", "order_id", "user_id", "product_id", "address", "ordered_date", "due_date", "paymont_mode", "status", "quantity", "amount_paid"));
                    System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-10s %-30s", "--------", "-------", "----------", "-------", "------------", "--------", "------------", "------", "--------", "-----------"));
                    System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-10s %-30s", order.getId(), order.getUser_id(), order.getProduct_id(), order.getAddress(), order.getOrdered_date(), order.getDue_date(), order.getPayment_mode(), order.getStatus(), order.getQuantity(), order.getTotal_amount()));
                }
            } else {
                System.out.println("Product id is invalid!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public String updateAddress(User user) throws Exception {
            con = Database.getInstance().getConnection();
            st = con.createStatement();  

            address = user.getAddress();

            System.out.println("Your old addresses : ");
            System.out.println(String.format("\t\t%-5s %-30s","id","address"));
            System.out.println(String.format("\t\t%-5s %-30s","--","-------"));

            rs = st.executeQuery("select * from user_details where user_id=" +user.getId());
            while(rs.next()){
                System.out.println(String.format("\t\t%-5s %-30s",rs.getInt(1),rs.getString(3)));
            }

            while(true){
                System.out.println();
                System.out.println("\t1.Select address from above list \t2.Do u want to add address?\t3.Go back");
                System.out.println();
                System.out.print("Enter your choice : ");

                byte num=(byte)new Validation().isNumeric(Validation.reader.readLine());

                if(num==1){
                    System.out.print("Enter the id with respect to address :");
                    byte u_id=(byte)new Validation().isNumeric(Validation.reader.readLine());
                    rs = st.executeQuery("select address from user_details where id=" +u_id);
                    if(rs.next()) return rs.getString(1);
                    else System.out.println("Invalid address id!");
                }    

                else if(num==2) return addAddress(user);

                else if(num==3) break;
            }

            return address;
    }

    private String addAddress(User user) throws Exception{
        address="";
        System.out.print("Enter your new address :");
        rs = st.executeQuery("insert into user_details values(default," + user.getId() + ",'" + Validation.reader.readLine() + "') returning id;");
        if (rs.next())
            id = rs.getInt("id");
        rs = st.executeQuery("select address from user_details where id=" +id);
        if (rs.next())
            address = rs.getString(1);
        return address;
    }

    public boolean validateId(String t_name, int id) throws Exception {
        con = Database.getInstance().getConnection();
        st = con.createStatement();
        rs = st.executeQuery("select * from " + t_name + " where id=" + id);
        if (rs.next())
            return true;
        return false;
    }

    public void ordersHistory(User user) {
        try {
            con = Database.getInstance().getConnection();
            st = con.createStatement();
            List<Order> orderList = new ArrayList<>();
    
            rs = st.executeQuery("select * from orders where user_id=" + user.getId());
            System.out.println(user.getId());
            while (rs.next()) {
                Order order = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getInt(11), rs.getDouble(12));
                orderList.add(order);
            }
    
            System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-10s %-30s ",
                    "order_id", "user_id", "product_id", "address", "ordered_date", "due_date", "paymont_mode",
                    "status", "received_date", "end_date", "quantity", "amount_paid"));
            System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-10s %-30s",
                    "--------", "-------", "----------", "-------", "------------", "--------", "------------",
                    "------", "-------------", "--------", "--------", "-----------"));
    
            for (Order order : orderList) {
                if (order.getUser_id() == user.getId()) {
                    System.out.println(String.format("%-10s %-10s %-10s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-10s %-30s",
                            order.getId(), order.getUser_id(), order.getProduct_id(), order.getAddress(),
                            order.getOrdered_date(), order.getDue_date(), order.getPayment_mode(), order.getStatus(),
                            order.getReceived_date(), order.getDead_date(), order.getQuantity(), order.getTotal_amount()));
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }     
    
}