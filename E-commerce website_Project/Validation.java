package com.zoho.ecommerce;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.io.Console;   
class Validation
{
	static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	public long isMobile(String s) throws IOException
	{
		while(!Pattern.matches("[6-9][0-9]{9}",s))
		{
			System.out.print("Enter mobile number in Correct Format :");
			s=reader.readLine();
		}
		return Long.parseLong(s);
	}
	
	public int isNumeric(String s) throws IOException
	{
		while(!Pattern.matches("[0-9]*",s))
		{
			System.out.print("Enter input in Correct Format:");
			s=reader.readLine();
		}
		return Integer.parseInt(s);
	}
	
	public String checkPassword() throws IOException
	{	
		System.out.print("Enter the password with minimum length is 8 :");
		String pass=new String(System.console().readPassword());
		if(PasswordChecker(pass)){
			return pass;
		}
		else{
			System.out.println("Password must include atleast a special character,capital letter,small letter and number!");
			return checkPassword();
		}
	}
	
	private boolean PasswordChecker(String password) {
        	if(password.length()<8) return false;
        	int c1=0,c2=0,c3=0,c4=0;
        	for(int i=0;i<password.length();i++){
            		if(password.charAt(i)>='a' && password.charAt(i)<='z') c1++;
            		else if(password.charAt(i)>='A' && password.charAt(i)<='Z') c2++;
            		else if(password.charAt(i)>='0' && password.charAt(i)<='9') c3++;
            		else if(("!@#$%^&*()-+").contains(""+(char)password.charAt(i))) c4++;           
       		}
          return (c1>0 && c2>0 && c3>0 && c4>0);
       }
}
