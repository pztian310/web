package com.action;
import java.io.IOException;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.DB;
import com.orm.Tadmin;
import com.orm.Tuser;
public class admin_servlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String type = req.getParameter("type");
		if (type.endsWith("adminlogin")) {
			adminlogin(req, res);
		}
	}
	
	public void adminlogin(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String result="no";
		String userName=req.getParameter("userName");
		String userPw=req.getParameter("userPw");
		String sql="select * from t_admin where  userName=? and userPw=?";
		Object[] params={userName,userPw};
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
				Tadmin admin=new Tadmin();
				admin.setUserId(rs.getString("userId"));
				admin.setUserName(rs.getString("userName"));
				admin.setUserPw( rs.getString("userPw"));
				System.out.println(" username is:"+rs.getString("userName"));
				result="yes";
	            req.getSession().setAttribute("admin", admin);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		if(result.equalsIgnoreCase("yes")){
	    req.setAttribute("message", "login successfully");
		req.setAttribute("path","/cart/admin/index.jsp");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
		}else{
			 req.setAttribute("message", "username or password is wrong ");
				req.setAttribute("path","/cart/admin/login.jsp");
				String targetURL = "/common/success.jsp";
				dispatch(targetURL, req, res);
		}
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
