package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
public class UpdateProducts {
    private static Connection con=null;
    private static Statement st=null;
    private static ResultSet rs=null;
    private List<Product> productList=null;

    static{
        try{
            con=Database.getInstance().getConnection();
            st=con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    String b_name,m_name,p_name;
    int quantity,id,vendor_id,b_id,m_id,p_id,product_id;
    double price;

    public void addProducts(Vendor vendor) {
        try {
            System.out.print("Enter the brand name: ");
            b_name = Validation.reader.readLine();
            System.out.print("Enter the model name: ");
            m_name = Validation.reader.readLine();
            System.out.print("Enter the product name: ");
            p_name = Validation.reader.readLine();
            System.out.print("Enter the quantity: ");
            quantity = new Validation().isNumeric(Validation.reader.readLine());
            System.out.print("Enter the price of one product: ");
            price = new Validation().isNumeric(Validation.reader.readLine());
            b_id = getId("brands", b_name);
            m_id = getId("models", m_name);
            p_id = getId("products", p_name);
            product_id = getId("product_details", b_id, m_id, p_id, quantity, price, vendor);
    
            if (product_id != -1) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add the product.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    


    private int getId(String t_name,String name) throws SQLException{
            rs=st.executeQuery("select id from "+t_name+" where upper(name) like upper('"+name+"')");
            if(rs.next()) return rs.getInt(1);
            else{
                rs=st.executeQuery("insert into "+t_name+"(name) values ('"+name+"') returning id");
                rs.next();
                return rs.getInt("id");
            }
    }

    private int getId(String t_name, int b_id, int m_id, int p_id, int quantity, double price, Vendor vendor) throws SQLException {
        int id=-1;
        rs = st.executeQuery("SELECT id FROM " + t_name + " WHERE brand_id = " + b_id + " AND model_id = " + m_id + " AND product_id = " + p_id + " AND vendor_id = " + vendor.getId());
        if (rs.next()) {
            id=rs.getInt(1);
            st.executeUpdate("update product_details set quantity=(quantity+"+quantity+") where id="+id);
            return id;
        } else {
            rs = st.executeQuery("INSERT INTO " + t_name + " VALUES(DEFAULT, " + vendor.getId() + ", " + b_id + ", " + m_id + ", " + p_id + ", " + quantity + ", " + price + ")returning id");
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return id;
    }

    public void listProducts(Vendor vendor) {
        try {
            List<Product> productList = new ArrayList<>(); 
    
            rs = st.executeQuery("SELECT d.id, b.name, m.name, p.name, d.quantity, d.price FROM product_details d JOIN brands b ON d.brand_id = b.id JOIN models m ON d.model_id = m.id JOIN products p ON d.product_id = p.id WHERE vendor_id = " + vendor.getId());
    
            while (rs.next()) {
                productList.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
            }
    
            System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "id", "brand", "model", "product", "quantity", "price"));
            System.out.println(String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s", "--", "-----", "-----", "-------", "--------", "-----"));
    
            for (Product product : productList) {
                System.out.println(product);
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    

    public void updateProducts(Vendor vendor){
        try{
            while(true){
                System.out.println("\n\t\t1.update amount\t2.update quantity\t3.remove product\t4.Go back\n");
                System.out.print("Enter your choice(1-4) :");
                int choice=new Validation().isNumeric(Validation.reader.readLine());
                if(choice<4){
                    System.out.print("Enter the product id : ");
                    id=new Validation().isNumeric(Validation.reader.readLine());
                }
                switch(choice){
                    case 1: System.out.print("Enter the new price :");
                            int price=new Validation().isNumeric(Validation.reader.readLine());
                            st.executeUpdate("update product_details set price="+price+" where id="+id);
                            System.out.println("Price is updated successfully!");
                            break;
                    case 2: System.out.print("Enter the new quantity :");
                            int quantity=new Validation().isNumeric(Validation.reader.readLine());
                            st.executeUpdate("update product_details set quantity="+quantity+" where id="+id);
                            System.out.println("Quantity is updated successfully!");
                            break;
                    case 3: st.executeUpdate("update product_details set quantity=0 where id="+id);
                            System.out.println("Product is removed successfully!");
                            break;
                    case 4: return;
                    default:break;
                }
            }
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}