package com.service;
import java.sql.ResultSet;
import java.util.List;
import com.dao.DB;
public class liuService
{
	
	public static String getUserName(String id)
	{
		String name="";
		
		String sql="select * from t_user where id=?";
		Object[] params={id};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			name=rs.getString("loginname");
			rs.close();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			name="游客";
		}
		mydb.closed();
		return name;
	}
	
	
	public static int[] Random(int suoyoudeshu[],int geshu)
	{
		if(suoyoudeshu.length<geshu)
		{
			return suoyoudeshu;
		}
		
		int[] numbers = suoyoudeshu;
		int n=numbers.length;
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = i + 1;
		}
		int[] result = new int[geshu];
		for (int i = 0; i < result.length; i++)

		{

			int r = (int) (Math.random() * n);

			result[i] = numbers[r];

			numbers[r] = numbers[n - 1];

			n--;

		}
		
		return result;  

	}
	
	public static int[] Random(List<Integer> list,int geshu)
	{
		int[] numbers = new int[list.size()];
		for(int i=0;i<list.size();i++)
		{
			numbers[i]=list.get(i);
		}
		
		if(numbers.length<geshu)
		{
			return numbers;
		}
		
		int n=numbers.length;
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = i + 1;
		}
		int[] result = new int[geshu];
		for (int i = 0; i < result.length; i++)

		{

			int r = (int) (Math.random() * n);

			result[i] = numbers[r];

			numbers[r] = numbers[n - 1];

			n--;

		}
		
		return result;  
	}
}
