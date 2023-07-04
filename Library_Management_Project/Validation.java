package com.zoho.Library;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
class Validation
{
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	public long isMobile(String s) throws IOException
	{
		while(!Pattern.matches("[6-9][0-9]{9}",s))
		{
		System.out.print("\nEnter input in Correct Format :");
		s=reader.readLine();
		}
		return Long.parseLong(s);
	}
	
	public int isNumeric(String s) throws IOException
	{
		while(!Pattern.matches("[0-9]*",s))
		{
		System.out.print("\nEnter input in Correct Format:");
		s=reader.readLine();
		}
		return Integer.parseInt(s);
	}
}
