package com.zoho.ecommerce;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
class Controller{
		private static Connection con=null;
		private static Statement st=null;
     	private static ResultSet rs=null;
     	private static PreparedStatement ps=null;

	public void getDetails(String t_name,int id) {
		try{
			con=Database.getInstance().getConnection();
			if(t_name.equals("users")){
				ps=con.prepareStatement("select u.id,u.name,u.password,u.mobile_no,u.age,u.gender,d.address from users u join user_details d on u.id=d.user_id where u.id=?");
				ps.setInt(1,id);
				rs=ps.executeQuery();
				System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s %-40s","id","name","password","mobile_number","age","gender","address"));
				if(rs.next()){
					System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s %-40s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6),rs.getString(7))); 
				}
			}
			else if(t_name.equals("vendors")){
				ps=con.prepareStatement("select * from vendors where id=?");
				ps.setInt(1,id);
				rs=ps.executeQuery();
				System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s %-15s","id","name","password","mobile_number","age","gender","status"));
				if(rs.next()){
					System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s %-15s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6),rs.getString(7))); 
				}
			}
			else if(t_name.equals("admins")){
				ps=con.prepareStatement("select * from admins where id=?");
				ps.setInt(1,id);
				rs=ps.executeQuery();
				System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s","id","name","password","mobile_number","age","gender"));
				if(rs.next()){
					System.out.println(String.format("\t%-5s %-20s %-25s %-18s %-5s %-8s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getInt(5),rs.getString(6))); 
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	int id;
	public void adminAccess(){
		try{
			con=Database.getInstance().getConnection();
			st=con.createStatement();
			ps=con.prepareStatement("select id,name,status from vendors");
			rs=ps.executeQuery();
			System.out.println(String.format("\t%-5s %-20s %-15s","id","name","status"));
			System.out.println(String.format("\t%-5s %-20s %-15s","id","----","------"));
			while(rs.next()){
				System.out.println(String.format("\t%-5s %-20s %-15s",rs.getInt(1),rs.getString(2),rs.getString(3))); 
			}
			System.out.println();
			System.out.print("Enter vendor id : ");
			id=new Validation().isNumeric(Validation.reader.readLine());
			if(!new ManageProducts().validateId("vendors", id)){
				System.out.println("Invalid order_id!");
			}
			else{
				System.out.println("\t\t1.Approve Vendor\t\t2.Decline Vendor\t\t3.back");
				System.out.print("Enter your choice : ");
				while(true){
					int choice=new Validation().isNumeric(Validation.reader.readLine());
					if(choice==1){
						st.executeUpdate("update vendors set status='approved' where id="+id);
						System.out.println("Vendor approved successfully");
						break;
					}
					else if(choice==2){
						st.executeUpdate("update vendors set status='declined' where id="+id);
						System.out.println("Vendor declined successfully");
						break;
					}
					else if(choice==3){
						break;
					}
					else{
						System.out.println("Enter your choice(1-3) : ");
					}
			    }
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
