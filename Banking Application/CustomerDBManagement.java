package com.zoho.banking;
import java.sql.ResultSet;
import java.sql.SQLException;

class CustomerDBManagement extends UserDBManagement{
	 int getBranchId(String acc_no)throws SQLException{
         	rs=st.executeQuery("select u.branch_id from users u join customers c on u.id=c.user_id where c.account_no='"+acc_no+"'");
         	if(rs.next()) return rs.getInt(1);
         	return 0;
     }

	 ResultSet getCustomerId(int user_id,int customer_id)throws SQLException{
		return st.executeQuery("select id from customers where id="+customer_id+"  and user_id="+user_id);
	}
	
	 int getCustomerId(String acc_no)throws SQLException{
		rs=st.executeQuery("select id from customers where account_no='"+acc_no+"'");
		if(rs.next()) return rs.getInt(1);
		return -1;
	}
	
	 double getBalance(String acc_no) throws SQLException{
        rs=st.executeQuery("select balance from customers where account_no='"+acc_no+"'");
        rs.next();
        return rs.getDouble(1);
    }
	
	 boolean isValidCustomer(int branch_id,String ifsc)throws SQLException{
        rs=st.executeQuery("select name from branches where id="+branch_id+" and ifsc='"+ifsc+"'");
        if(rs.next()){
           return true;
        }
        return false;
    }
	
	 String getScheme(String acc_no) throws SQLException{
        rs=st.executeQuery("select s.name from schemes s join customers c on c.scheme_id=s.id where c.account_no='"+acc_no+"'");
        rs.next();
        return rs.getString(1);
    }
	
	 void setBalance(double amount,String acc_no){
		try {
			st.executeUpdate("update customers set balance=(balance-"+amount+") where account_no='"+acc_no+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	 void deposite(int customer_id,double amount){
		try {
			pst=con.prepareStatement("insert into transactions values(default,default,?,?,default)");
			pst.setInt(1, customer_id);
			pst.setDouble(2, amount);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	 void withdraw(int customer_id,double amount){
		try {
			pst=con.prepareStatement("insert into transactions values(default,?,default,?,default)");
    		pst.setInt(1, customer_id);
    		pst.setDouble(2, amount);
    		pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	 void makeTransaction(int customer_id,int benificier_id,double amount){
		try {
			pst=con.prepareStatement("insert into transactions values(default,?,?,?,default)");
	   		pst.setInt(1, customer_id);
	   		pst.setInt(2, benificier_id);
	   		pst.setDouble(3, amount);
	   		pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	 int updateDeposite(double amount,String acc_no) throws SQLException{
		return st.executeUpdate("update customers set balance=(balance+"+amount+") where account_no='"+acc_no+"'");
	}
	
	 ResultSet getCustomer(int user_id) throws SQLException{
		return st.executeQuery("select u.id,u.name,u.mobile_no,u.dob,u.gender,b.name from users u join branches b on u.branch_id=b.id join customers c on c.user_id=u.id where u.id="+user_id);
	}
	
	 ResultSet getAccounts(int user_id) throws SQLException{
		return st.executeQuery("select c.id,s.name,c.account_no,c.balance,c.opening_date from customers c join schemes s on c.scheme_id=s.id join users u on c.user_id=u.id where u.id="+user_id);
	}
	
	 ResultSet getHistory(int customer_id) throws SQLException{
		return st.executeQuery("select c1.account_no,c2.account_no,t.amount,t.transaction_date from transactions t left join customers c1 on t.from_id=c1.id left join customers c2 on c2.id=t.to_id where t.from_id="+customer_id+" or t.to_id="+customer_id);
	}
	
	 ResultSet getAccountNumber(int user_id) throws SQLException{
		return st.executeQuery("select account_no from customers where user_id="+user_id);
	}
}
