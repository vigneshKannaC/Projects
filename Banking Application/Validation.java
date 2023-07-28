package com.zoho.banking;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class Validation {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static long isMobile(String s) throws IOException {
        while (!Pattern.matches("[6-9][0-9]{9}", s)) {
            System.out.print("\tEnter mobile number in correct format: ");
            s = reader.readLine();
        }
    	return Long.parseLong(s);
    }

    static long isAadhar(String s) throws IOException {
		while (!Pattern.matches("[1-9][0-9]{11}", s)) {
		    System.out.print("\tEnter aadhar number in correct format: ");
		    s = reader.readLine();
		}
		return Long.parseLong(s);
	}

    static int isNumeric(String s) throws IOException {
        while (!Pattern.matches("[0-9]+", s)) {
            System.out.print("\tEnter input in correct format: ");
            s = reader.readLine();
        }
        return Integer.parseInt(s);
    }
    
    private static boolean isValidDate(String dob) {
        try {
            LocalDate.parse(dob);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String validateDate() throws IOException{
        String dob;
        boolean isValid = false;

        do {
            System.out.print("Enter your DOB (yyyy-mm-dd): ");
            dob = reader.readLine();
            isValid = isValidDate(dob);

            if (!isValid) {
                System.out.println("Invalid date format!");
            }
        } while (!isValid);

        return dob;
    }

   static String checkPassword() throws IOException {
       Console console = System.console();
       if (console == null) {
           throw new IOException("No console available. Please run the program in a console or terminal.");
       }
       
       System.out.print("Enter the password with a minimum length of 8: ");
       String pass = new String(console.readPassword());
       
       if (PasswordChecker(pass)) {
           return pass;
       } else {
           System.out.println("Password must include at least a special character, a capital letter, a small letter, and a number!");
           return checkPassword();
       }
   }

    private static boolean PasswordChecker(String password) {
        if (password.length() < 8)
            return false;

        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLowerCase(ch))
                c1++;
            else if (Character.isUpperCase(ch))
                c2++;
            else if (Character.isDigit(ch))
                c3++;
            else if ("!@#$%^&*()-+".contains(String.valueOf(ch)))
                c4++;
        }

        return (c1 > 0 && c2 > 0 && c3 > 0 && c4 > 0);
    }
    
    static String generateAccountNumber(int customer_id,int branch_id) {
    	return ""+String.format("%03d",customer_id)+String.format("%07d",branch_id);
    }
    
    static String getAccNo() throws IOException{
        String acc=""+reader.readLine();
        if(acc.length()==10 && Pattern.matches("[0-9]{10}", acc)){
            return acc;
        }
        else{
            System.out.print("\tEnter the valid Account number : ");
            return getAccNo();
        }
    }

    static String getISFC() throws IOException{
        String ifsc=reader.readLine();
        if(ifsc.length()==11 && ifsc.startsWith("INDIAN")){
            return ifsc;
        }
        else{
            System.out.print("\tEnter the valid ifsc code : ");
            return getISFC();
        }
    }

    static int getPincode() throws IOException{
        String pincode=reader.readLine();
        if(pincode.length()==6 && Pattern.matches("[6][0-4][0-9]{4}", pincode)){
            return Integer.parseInt(pincode);
        }
        else{
            System.out.print("\tEnter the valid pincode : ");
            return getPincode();
        }
    }
}
