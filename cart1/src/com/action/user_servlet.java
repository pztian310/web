package com.action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.DB;
import com.orm.Tuser;
public class user_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		if(type.endsWith("userReg"))
		{
			userReg(req, res);
		}
		
		if(type.endsWith("userLogin"))
		{
			userLogin(req, res);
		}
		
		if(type.endsWith("userLogout"))
		{
			userLogout(req, res);
		}
		
		if(type.endsWith("userDelete"))
		{
			userDelete(req, res);
		}
		
		if(type.endsWith("userMana"))
		{
			userMana(req, res);
		}
		
		if(type.endsWith("userAdd"))
		{
			userAdd(req, res);
		}
	}
	
	
	public void userLogin(HttpServletRequest req,HttpServletResponse res){
		String result="no";
		String loginName=req.getParameter("loginName");
		String loginPw=req.getParameter("loginPw");
		String sql="select * from t_user where  user_name=? and user_pw=?";
		Object[] params={loginName,loginPw};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			boolean mark=(rs==null||!rs.next()?false:true);
			if(mark==false)
			{
				result="no";
			}
			if(mark==true)
			{
				Tuser user=new Tuser();
				user.setUserID(rs.getString("user_id"));
			    user.setUsername(rs.getString("user_name"));
			    user.setLoginpw(rs.getString("user_pw"));
			    user.setPhone(rs.getString("user_tel"));
			    user.setAddress(rs.getString("user_address"));
				System.out.println(" username is:"+rs.getString("user_name"));
				result="yes";
	            req.getSession().setAttribute("user", user);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		if(result.equalsIgnoreCase("yes")){
		  req.setAttribute("message", "logining success");
		  req.setAttribute("path","/cart/page/index.jsp");
		  String targetURL = "/common/success.jsp";
		  dispatch(targetURL, req, res);
		}else{
		  req.setAttribute("message", "User name or password");
		  req.setAttribute("path","/cart/page/login.jsp");
		  String targetURL = "/common/success.jsp";
		  dispatch(targetURL, req, res);
		}
	}
	
	
	public void userReg(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		
        req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		String username= req.getParameter("username");
		String loginpw=req.getParameter("loginpw");
		String phone=req.getParameter("phone");
		String address=req.getParameter("address");
		
		System.out.println("username:"+username);
	    
		String sql="insert into t_user (user_name,user_pw,user_address,user_tel,user_del) values(?,?,?,?,?) ";
		Object[] params={username,loginpw,address,phone,"no"};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		req.setAttribute("message", "注册成功  用户名是 is:"+username+";密码是："+loginpw);
		req.setAttribute("path","/cart/page/login.jsp");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	public void userDelete(HttpServletRequest req,HttpServletResponse res){
       String id=req.getParameter("id");
	   String sql="delete from t_user where user_id=?";
		Object[] params={id};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "删除用户成功");
		req.setAttribute("path", "user?type=userMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
		
	}
	
	
	public void userMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List userList=new ArrayList();
		String sql="select * from t_user";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tuser user=new Tuser();
				user.setUserID(rs.getString("user_id"));
				user.setUsername(rs.getString("user_name"));
				user.setLoginpw(rs.getString("user_pw"));
				user.setAddress(rs.getString("user_address"));
				user.setPhone(rs.getString("user_tel"));
				userList.add(user);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("userList", userList);
		req.getRequestDispatcher("admin/user/userMana.jsp").forward(req, res);
	}
	
	public void userLogout(HttpServletRequest req,HttpServletResponse res)
	{
		String url=req.getContextPath();
		System.out.println("url is:"+url);
		req.getSession().setAttribute("user", null);
		req.setAttribute("message", "safe exit");
		req.setAttribute("path","/cart/page/login.jsp");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	public void userAdd(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException
	{
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		String username= req.getParameter("username");
		String loginpw=req.getParameter("loginpw");
		String phone=req.getParameter("phone");
		String address=req.getParameter("address");
		
		System.out.println("username:"+username);
	

		String sql="insert into t_user (user_name,user_pw,user_address,user_tel,user_del) values(?,?,?,?,?) ";
		Object[] params={username,loginpw,address,phone,"no"};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		req.setAttribute("message", "添加用户成功");
		req.setAttribute("path","user?type=userMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	public void dispatch(String targetURI,HttpServletRequest request,HttpServletResponse response) 
	{
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(targetURI);
		try 
		{
		    dispatch.forward(request, response);
		    return;
		} 
		catch (ServletException e) 
		{
                    e.printStackTrace();
		} 
		catch (IOException e) 
		{
			
		    e.printStackTrace();
		}
	}
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
	
	public void destroy() 
	{
		
	}
}
