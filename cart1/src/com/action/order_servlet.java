package com.action;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.DB;
import com.orm.TOrderItem;
import com.orm.Torder;
import com.orm.Tuser;
public class order_servlet extends HttpServlet {
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		
		if(type.endsWith("orderAdd"))
		{
			orderAdd(req, res);
		}
		if(type.endsWith("orderAll"))
		{
			orderAll(req, res);
		}
		if(type.endsWith("orderDetail"))
		{
			orderDetail(req, res);
		}
		
		if(type.endsWith("cartAdd"))
		{
			cartAdd(req, res);
		}
		
		if(type.endsWith("cartDel"))
		{
			cartDel(req, res);
		}
		if(type.endsWith("cartClear"))
		{
			cartClear(req, res);
		}
		
		if(type.endsWith("orderMana"))
		{
			orderMana(req, res);
		}
		
		if(type.endsWith("orderfahuo"))
		{
			orderfahuo(req, res);
		}
		if(type.endsWith("orderdel"))
		{
			orderdel(req, res);
		}
	}
	
	public void cartAdd(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		if(req.getSession().getAttribute("user")!=null){
			int goodID = Integer.parseInt(req.getParameter("goodID"));
			int goodPrice = Integer.parseInt(req.getParameter("goodPrice"));
			int goodnum = Integer.parseInt(req.getParameter("goodnum"));
			String goodName = req.getParameter("goodName");
			boolean flag = true;
			TOrderItem orderitem = new TOrderItem();
			orderitem.setGoodID(goodID);
			orderitem.setGoodName(goodName);
			orderitem.setGoodnum(goodnum);
			orderitem.setGoodPrice(goodPrice);
			Vector cart = (Vector) req.getSession().getAttribute("cart");
			if (cart == null) {
				cart = new Vector();
			}

			else {
				for (int i = 0; i < cart.size(); i++) {
					TOrderItem form = (TOrderItem) cart.elementAt(i);
					if (form.goodID == orderitem.goodID) {
						form.goodnum = form.goodnum + orderitem.goodnum;
						cart.setElementAt(form, i);
						flag = false;

					}
				}
			}
			if (flag)
				cart.add(orderitem);
			req.getSession().setAttribute("cart", cart);
			
			req.getRequestDispatcher("/page/cart.jsp").forward(req, res);
		}
		else{
			req.getRequestDispatcher("/page/login.jsp").forward(req, res);
		}
	}
	
	
	public void cartDel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		if(req.getSession().getAttribute("user")!=null){
			int goodID = Integer.parseInt(req.getParameter("goodID"));
			
			TOrderItem orderitem = new TOrderItem();
			orderitem.setGoodID(goodID);
			Vector cart = (Vector) req.getSession().getAttribute("cart");
			if (cart == null) {
				cart = new Vector();
			}

			else {
				for (int i = 0; i < cart.size(); i++) {
					TOrderItem form = (TOrderItem) cart.elementAt(i);
					if (form.goodID == orderitem.goodID) {
						form.goodnum = form.goodnum + orderitem.goodnum;
						cart.remove(form);
					}
				}
			}
			
			req.getSession().setAttribute("cart", cart);
			
			req.getRequestDispatcher("/page/cart.jsp").forward(req, res);
		}
		else{
			req.getRequestDispatcher("/page/login.jsp").forward(req, res);
		}
	
	}
	
	
	public void cartClear(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		req.getSession().removeAttribute("cart");
		req.setAttribute("message", "cartClear success");
		req.setAttribute("path","/cart/page/cart.jsp");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
    public void orderAdd(HttpServletRequest req,HttpServletResponse res)
	{
    	Tuser user=(Tuser)req.getSession().getAttribute("user");
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
    	int sum=0;
		int nextorderID=199;
		String orderbianhao=df2.format(new Date());
		String orderdate=df.format(new Date());
		String sql="select max(order_id)+1 orderid from t_order";
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			boolean mark=(rs==null||!rs.next()?false:true);
	        nextorderID=rs.getInt("orderid");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
        mydb.closed();
		
        
        String sql2="insert into t_orderitem (order_id,goods_id,goods_quantity) values(?,?,?) ";
		DB mydb2=new DB();
        Vector cart = (Vector) req.getSession().getAttribute("cart");
		if(cart!=null){
           for (int i = 0; i < cart.size(); i++) {
			  TOrderItem item=(TOrderItem)cart.elementAt(i);
	          sum=sum+item.goodnum*item.getGoodPrice();
	          Object[] params2={nextorderID,item.getGoodID(),item.getGoodnum() };
	          mydb2.doPstm(sql2, params2);
	          try {
				updateCuKun(item.getGoodID(),item.getGoodnum());
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
           
           String sql3="insert into t_order (order_id,order_bianhao,order_date,order_songhuodizhi,order_jine,order_user_id,order_status) values(?,?,?, ?,?,?,?) ";
           Object[] params3={nextorderID,orderbianhao,orderdate,user.getAddress(),sum, user.getUserID(),"待发货"};
           mydb2.doPstm(sql3, params3);
		}
		mydb2.closed();
		req.getSession().removeAttribute("cart");
	    req.setAttribute("message", "builder order success");
	    req.setAttribute("path","/cart/page/cart.jsp");
	    String targetURL = "/common/success.jsp";
	    dispatch(targetURL, req, res);
	}
	
	public void orderAll(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		
		if(req.getSession().getAttribute("user")!=null){
		Tuser user=(Tuser)req.getSession().getAttribute("user");
		List orderList=new ArrayList();
		String sql="select * from t_order where order_user_id="+user.getUserID();
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Torder order=new Torder();
				order.setOrderID(rs.getString("order_id"));
				order.setOrderbianhao(rs.getString("order_bianhao"));
				order.setOrdermoney(rs.getString("order_jine"));
				order.setOrderDate(rs.getString("order_date"));
				order.setOrderAddress(rs.getString("order_songhuodizhi"));
				order.setOrderStatus(rs.getString("order_status"));
				orderList.add(order);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("orderList", orderList);
		req.getRequestDispatcher("/page/myorder.jsp").forward(req, res);
		}else{
			req.getRequestDispatcher("/page/login.jsp").forward(req, res);
		}
	}
	
	public void orderDetail(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		
		String orderID=req.getParameter("orderID");
		List orderItemList=new ArrayList();
		String sql="select a.goods_id,b.goods_name,b.goods_shichangjia,a.goods_quantity from t_orderitem  a" 
				+" join t_goods b on b.goods_id=a.goods_id "
				+"where order_id="+orderID;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				TOrderItem orderitem=new TOrderItem();
				orderitem.setGoodName(rs.getString("goods_name"));
				orderitem.setGoodnum(rs.getInt("goods_quantity"));
				orderitem.setGoodPrice(rs.getInt("goods_shichangjia"));
				orderitem.setGoodID(rs.getInt("goods_id"));
				orderItemList.add(orderitem);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("orderItemList", orderItemList);
		req.getRequestDispatcher("/page/orderdetail.jsp").forward(req, res);
	}
	
	
	public void orderMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List orderList=new ArrayList();
		String sql="select * from t_order ";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Torder order=new Torder();
				order.setOrderID(rs.getString("order_id"));
				order.setOrderbianhao(rs.getString("order_bianhao"));
				order.setOrdermoney(rs.getString("order_jine"));
				order.setOrderDate(rs.getString("order_date"));
				order.setOrderAddress(rs.getString("order_songhuodizhi"));
				order.setOrderStatus(rs.getString("order_status"));
				orderList.add(order);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("orderList", orderList);
		req.getRequestDispatcher("/admin/order/orderMana.jsp").forward(req, res);
	}
	
	public void orderfahuo(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String orderno= req.getParameter("orderno");
        String sql = " update t_order  set order_status='已发货' where order_bianhao=?";
		Object[] params = {orderno};
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
        
		req.setAttribute("message", "sucess");
		req.setAttribute("path", "order?type=orderMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	public void orderdel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		String orderno= req.getParameter("orderno");
        String sql = " delete from t_order  where order_bianhao=?";
		Object[] params = {orderno};
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
        
		req.setAttribute("message", "sucess");
		req.setAttribute("path", "order?type=orderMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	
	public void updateCuKun(int goodsID,int num) throws ServletException, IOException
	{
		
        String sql = " update t_goods set goods_kucun=goods_kucun-"+num+" where goods_id="+goodsID;
		Object[] params = {};
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
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
