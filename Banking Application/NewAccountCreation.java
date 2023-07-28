package com.zoho.banking;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
class NewAccountCreation extends UserDBManagement{
	ResultSet rs;
	private final int minorAge=10;
	private final int majorAge=18;
	private final int seniorCitizenAge=65;

	 void createAccount(){
		try {
			System.out.print("Enter your name : ");
			String name=Validation.reader.readLine();
			System.out.print("Enter your aadhar number : ");    
			long aadharNumber=isExcistsAadhar(Validation.isAadhar(Validation.reader.readLine()));
			if(aadharNumber==-1) return;
			System.out.print("Enter your mobile number : ");
			long mobile=Validation.isMobile(Validation.reader.readLine());
			System.out.print("Enter your gender : ");
			String gender=Validation.reader.readLine();
			String dob=Validation.validateDate();
			int age=findAge(dob);
			String accountType="savings";
			if(age>=majorAge){
				System.out.print("Enter the account type(savings/current) : ");
				accountType=Validation.reader.readLine();
			}
			int accountId=getAccId(accountType);
			System.out.print("Enter your branch name: ");
			int branch=getBranchId(Validation.reader.readLine());
			int categoryId=findCategory(age);
			System.out.println("Enter your Address : ");
			String address=findAddress();
			addToUser(name,mobile,dob,gender,branch);
			addToCutomer(aadharNumber,address,accountId,categoryId);
		} 
		catch(IOException | SQLException e){
			e.printStackTrace();
		}
	}

	private int findCategory(int age){
		if(age<=minorAge) return 1;
		else if(age<=majorAge) return 2;
		else if(age<seniorCitizenAge) return 3;
		else return 4;
	}

	private String findAddress() throws IOException{	    
		System.out.print("\tEnter the door no: ");
		int num=Validation.isNumeric(Validation.reader.readLine());		
		System.out.print("\tEnter the street name : ");
		String street=Validation.reader.readLine();		
		System.out.print("\tEnter the city name : ");
		String city=Validation.reader.readLine();		
		System.out.print("\tEnter the district name : ");
		String district=Validation.reader.readLine();		
		System.out.print("\tEnter the pincode no: ");
		int pincode=Validation.getPincode();		
		return ""+num+","+street+","+city+","+district+","+pincode;		
	}
	 long isExcistsAadhar(long aadhar) throws SQLException,IOException{        
        rs=checkAadhar(aadhar);
        if(rs.next()){            
            System.out.println("Your account is already present in the scheme.");
            System.out.println("\t1.Do u want to create another account with different scheme?\t2.Go Back");
            System.out.print("Enter your choice : ");
            if(Validation.isNumeric(Validation.reader.readLine())==1){
                return isExcists(aadhar);
            }
            else {
                return -1;
            }
        } 
        else{  
            return aadhar;
        }
    }
	
	 long isExcists(long aadhar) throws SQLException,IOException{
		System.out.print("Enter the new scheme name:");
		String scheme=Validation.reader.readLine();
		rs=checkAadhar(aadhar,scheme);
		if(rs.next()){
		    return isExcistsAadhar(aadhar);
		}
		else{
		    rs=findScheme(scheme);
		    if(rs.next()){
		        return aadhar;
		    }
		    else{
		        System.out.println("Choose a valid plan!");
		    }
		}
		return -1;
	   }
	
	 int getAccId(String acc_type) throws SQLException,IOException{
        rs = findAccount(acc_type);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            System.out.println("No account type found for: " + acc_type);
            System.out.print("Enter valid type of account(savings/current/salary) : ");
            return getAccId(Validation.reader.readLine());
        }
    }
}
