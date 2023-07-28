package com.zoho.banking;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

class AccountDetails{
    private  ResultSet rs;
    List<String> accountNumbersList;
    CustomerDBManagement customerDBManagement = new CustomerDBManagement();
    
	  void showCustomer(int user_id){
		try{	
			rs=customerDBManagement.getCustomer(user_id);
			rs.next();
			User user=new User(rs.getInt(1),rs.getString(2),rs.getLong(3),Date.valueOf(rs.getString(4)),rs.getString(5),rs.getString(6));
			System.out.println(String.format("%-5s %-15s %-15s %-15s %-10s %-10s","id","name","mobile_no","dob","gender","branch"));
			System.out.println(String.format("%-5s %-15s %-15s %-15s %-10s %-10s","--","----","---------","---","------","------","---------","-------"));
			System.out.println(String.format("%-5s %-15s %-15s %-15s %-10s %-10s",user.getId(),user.getName(),user.getMobileNumber(),user.getDob(),user.getGender(),user.getBranch()));			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	  void showAccounts(int user_id) {
		System.out.println("\nAccounts List :");
		System.out.println("-------- ------");
		
		System.out.println(String.format("%-5s %-10s %-15s %-10s %-10s","id","scheme","account_no","balance","opened_date"));
		System.out.println(String.format("%-5s %-10s %-15s %-10s %-10s","--","------","----------","-------","-----------"));
		try{
			rs=customerDBManagement.getAccounts(user_id);
			while(rs.next()){
				Account account=new Account(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),Date.valueOf(rs.getString(5)));
				System.out.println(String.format("%-5s %-10s %-15s %-10s %-10s",account.getId(),account.getScheme(),account.getAccountNumber(),account.getBalance(),account.getOpenedDate()));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	 void showTransactions(int user_id){
		System.out.print("\nEnter the account id from above list : ");
		int customer_id=-1;
		try {
			customer_id = Validation.isNumeric(Validation.reader.readLine());		
			rs=customerDBManagement.getCustomerId(user_id,customer_id);
			if(rs.next()){
				System.out.println("\nTransactions List :");
				System.out.println("------------ ------");
		
				System.out.println(String.format("%-15s %-15s %-10s %-10s","FromAccountNo","ToAccountNo","Amount","TransactionDate"));
				System.out.println(String.format("%-15s %-15s %-10s %-10s","-------------","-----------","------","---------------"));
			
				rs=customerDBManagement.getHistory(customer_id);
				while(rs.next()){
					Transaction transaction=new Transaction(rs.getString(1),rs.getString(2),rs.getDouble(3),Date.valueOf(rs.getString(4)));
					System.out.println(String.format("%-15s %-15s %-10s %-10s",transaction.getFrom_accountNo(),transaction.getTo_accountNo(),transaction.getAmount(),transaction.getTransaction_date()));
				}
			}
			else{
				System.out.println("\tInvalid Id!!");
				showTransactions(user_id);
			}
		}
		
		catch(SQLException | IOException e){
			e.printStackTrace();
		}
				
	}

	 String chooseAccount(int user_id){
		accountNumbersList=new ArrayList<String>();
		String acc_no="";
		try{
			rs=customerDBManagement.getAccountNumber(user_id);
			while(rs.next()){
				accountNumbersList.add(rs.getString(1));
			}
			System.out.println("Your accounts List : "+accountNumbersList);
			System.out.print("Enter account number : ");
			acc_no=Validation.reader.readLine();
			if(!accountNumbersList.contains(acc_no)){
				System.out.println("Account number is invalid!");
				chooseAccount(user_id);
			}
		}
		catch(SQLException | IOException e){
			e.printStackTrace();
		}
		return acc_no;
	}
}
