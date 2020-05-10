package com.action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.DB;
import com.orm.Tcategory;
import com.orm.Tpingjia;
import com.orm.Tproduct;
import com.orm.Tuser;

public class product_servlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		if(type.endsWith("productAdd"))
		{
			productAdd(req, res);
		}
		if(type.endsWith("productAll"))
		{
			try {
				productAll(req, res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type.endsWith("productDel"))
		{
			productDel(req, res);
		}
		
		if(type.endsWith("pingjiaAdd"))
		{
			pingjiaAdd(req, res);
		}
		
		if(type.endsWith("productMana"))
		{
			productMana(req, res);
		}	
		
		if(type.endsWith("productMain"))
		{
			try {
				productMain(req, res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void productMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String sql="select a.goods_id,a.goods_name,a.goods_miaoshu,a.goods_pic,a.goods_shichangjia,a.goods_catelog_id,a.goods_kucun,b.catelog_name from t_goods a join t_catelog b on b.catelog_id=a.goods_catelog_id where 1=1";
		List productList=new ArrayList();
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tproduct product=new Tproduct();
				product.setGoodID(rs.getString("goods_id"));
				product.setGoodName(rs.getString("goods_name"));
				product.setGoodMiaoshu(rs.getString("goods_miaoshu"));
				product.setGoodPic(rs.getString("goods_pic"));
				product.setCatergoryID(rs.getString("goods_catelog_id"));
				product.setCatergoryName(rs.getString("catelog_name"));
				product.setPrice(rs.getString("goods_shichangjia"));
				product.setCunkun(rs.getString("goods_kucun"));
				productList.add(product);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		req.setAttribute("productList", productList);
		req.getRequestDispatcher("/admin/product/productMana.jsp").forward(req, res);
	}
	
	public void productAdd(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException
	{
	    
		System.out.println("parameter is:"+req.getParameter("goodName"));
		String goodName=req.getParameter("goodName");
		String goodMiaoshu=req.getParameter("goodMiaoshu");//new String(req.getParameter("goodMiaoshu").getBytes("iso-8859-1"), "utf-8");
		String goodPic=req.getParameter("fujian");
		String price=req.getParameter("price");
		String kucun=req.getParameter("kucun");
		
		System.out.println("cunkun is"+kucun);
		String catergoryID=req.getParameter("catergoryID");

		String sql="insert into t_goods(goods_name,goods_miaoshu,goods_pic,goods_shichangjia,goods_catelog_id,goods_kucun,goods_Del) values(?,?,?,?,?,?,?)";
		Object[] params={goodName,goodMiaoshu,goodPic,price,catergoryID,kucun,"no"};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "sucess");
		req.setAttribute("path", "product?type=productMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
        
	}
	
	
	
	public void productDel(HttpServletRequest req,HttpServletResponse res)
	{
		String id=req.getParameter("id");
		
		String sql="delete from t_goods where goods_id=?";
		Object[] params={id};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "sucess");
		req.setAttribute("path", "product?type=productMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	
	public void pingjiaAdd(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		Tuser user=(Tuser)req.getSession().getAttribute("user");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String productID=req.getParameter("goodID");
		String content=req.getParameter("content");
		
		if(user!=null){
		String sql="insert into productpingjia(product_id,content,createDate,user_id) values(?,?,?,?)";
		Object[] params={productID,content,df.format(new Date()),user.getUserID()};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "pingjia sucess");
		req.setAttribute("path", "product?type=productAll&goodID="+productID);
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
		}else{
			req.setAttribute("message", "请先登录");
			req.setAttribute("path","/cart/page/login.jsp");
			String targetURL = "/common/success.jsp";
			dispatch(targetURL, req, res);
		}
	}
	
	
	public void productAll(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException, SQLException
	{
		List productList=new ArrayList();
		List pingjiaList=new ArrayList();
		String sql="select a.goods_id,a.goods_name,a.goods_miaoshu,a.goods_pic,a.goods_shichangjia,a.goods_catelog_id,a.goods_kucun,b.catelog_name from t_goods a join t_catelog b on b.catelog_id=a.goods_catelog_id where 1=1";
		String goodID=req.getParameter("goodID");
		String categoryID=req.getParameter("categoryID");
		String productName=req.getParameter("productName");
		
		if(goodID!=null && !goodID.equalsIgnoreCase("")){
			sql=sql+" and a.goods_id="+Integer.parseInt(goodID);
			
		}
		
		if(categoryID!=null && !categoryID.equalsIgnoreCase("")){
			sql=sql+" and a.goods_catelog_id="+Integer.parseInt(categoryID);
			
		}
		if(productName!=null&&!productName.equalsIgnoreCase("")){
			sql=sql+" and a.goods_name like '%"+productName+"%'";
		}
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tproduct product=new Tproduct();
				product.setGoodID(rs.getString("goods_id"));
				product.setGoodName(rs.getString("goods_name"));
				product.setGoodMiaoshu(rs.getString("goods_miaoshu"));
				product.setGoodPic(rs.getString("goods_pic"));
				product.setCatergoryID(rs.getString("goods_catelog_id"));
				product.setCatergoryName(rs.getString("catelog_name"));
				product.setPrice(rs.getString("goods_shichangjia"));
				product.setCunkun(rs.getString("goods_kucun"));
				productList.add(product);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		if(goodID!=null && !goodID.equalsIgnoreCase("")){
			
			DB mydb4=new DB();
			String sql2="select tu.user_name,aa.content,aa.createDate \n"
					   +" from productpingjia aa \n"
					   +" join t_user tu on tu.user_id=aa.user_id"
                       +" where aa.product_id="+goodID;
			mydb4.doPstm(sql2, params);
			ResultSet rs2=mydb4.getRs();
			while(rs2.next())
			{
				Tpingjia pingjia=new Tpingjia();
				pingjia.setUsername(rs2.getString("user_name"));
				pingjia.setContent(rs2.getString("content"));
				pingjia.setCreateDate(rs2.getString("createDate"));
				pingjiaList.add(pingjia);
		    }
			rs2.close();
			mydb4.closed();
			req.setAttribute("pingjiaList", pingjiaList);
			req.setAttribute("productList", productList);
			req.getRequestDispatcher("/page/example.jsp").forward(req, res);
			
		}else{
			req.setAttribute("productList", productList);
			req.getRequestDispatcher("/page/product.jsp").forward(req, res);
		}
		
	}
	

	public void productMain(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException, SQLException
	{
		List productList=new ArrayList();
		String sql="select a.goods_id,a.goods_name,a.goods_miaoshu,a.goods_pic,a.goods_shichangjia,a.goods_catelog_id,a.goods_kucun,b.catelog_name from t_goods a join t_catelog b on b.catelog_id=a.goods_catelog_id where 1=1";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tproduct product=new Tproduct();
				product.setGoodID(rs.getString("goods_id"));
				product.setGoodName(rs.getString("goods_name"));
				product.setGoodMiaoshu(rs.getString("goods_miaoshu"));
				product.setGoodPic(rs.getString("goods_pic"));
				product.setCatergoryID(rs.getString("goods_catelog_id"));
				product.setCatergoryName(rs.getString("catelog_name"));
				product.setPrice(rs.getString("goods_shichangjia"));
				product.setCunkun(rs.getString("goods_kucun"));
				productList.add(product);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		String sql2 = " select * from t_catelog";
		List categoryList = new ArrayList();
		Object[] params2 = {};
		DB mydb2 = new DB();
		try {
			mydb2.doPstm(sql2, params2);
			ResultSet rs2 = mydb2.getRs();
			while (rs2.next()) {
				Tcategory category = new Tcategory();
				category.setCategoryID(rs2.getString("catelog_id"));
				category.setCategoryName(rs2.getString("catelog_name"));
				categoryList.add(category);
			}
			rs2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb2.closed();
		req.getSession().setAttribute("categoryList", categoryList);
		req.setAttribute("productList", productList);
		req.getRequestDispatcher("/page/index.jsp").forward(req, res);
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
