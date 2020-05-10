package com.action;
import java.io.IOException;
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
import com.orm.Tcategory;
import com.util.JsonUtil;

public class category_servlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String type = req.getParameter("type");
		if (type.endsWith("categoryAdd")) {
			categoryAdd(req, res);
		}

		if (type.endsWith("categoryDel")) {
			categoryDel(req, res);
		}

		if (type.endsWith("categoryMana")) {
			categoryMana(req, res);
		}
		
		if (type.endsWith("categoryAll")) {
			categoryAll(req, res);
		}
		
		if (type.endsWith("categoryJson")) {
			categoryJson(req, res);
		}
	}

	public void categoryMana(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String sql = " select * from t_catelog";
		List categoryList = new ArrayList();
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tcategory category = new Tcategory();
				category.setCategoryID(rs.getString("catelog_id"));
				category.setCategoryName(rs.getString("catelog_name"));
				categoryList.add(category);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		req.setAttribute("categoryList", categoryList);
		req.getRequestDispatcher("/admin/category/categoryMana.jsp").forward(req, res);
	}
	
	
	public void categoryJson(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/plain"); 
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String sql = " select * from t_catelog";
		List categoryList = new ArrayList();
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tcategory category = new Tcategory();
				category.setCategoryID(rs.getString("catelog_id"));
				category.setCategoryName(rs.getString("catelog_name"));
				categoryList.add(category);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		res.getWriter().println(JsonUtil.list2json(categoryList));
	}
	
	public void categoryAll(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String sql = " select * from t_catelog";
		List categoryList = new ArrayList();
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tcategory category = new Tcategory();
				category.setCategoryID(rs.getString("catelog_id"));
				category.setCategoryName(rs.getString("catelog_name"));
				categoryList.add(category);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		req.setAttribute("categoryList", categoryList);
		req.getRequestDispatcher("/page/categoryList.jsp").forward(req, res);
	}

	public void categoryDel(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String id = req.getParameter("id");

		String sql = "delete from t_catelog where catelog_id=? and not exists (select 1 from  t_goods where catelog_id=goods_catelog_id ) ";
		Object[] params = { id };
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();

		req.setAttribute("message", "sucess");
		req.setAttribute("path", "category?type=categoryMana");

		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void categoryAdd(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String catelog_name = req.getParameter("catelog_name");

		String sql = "insert t_catelog(catelog_name,catelog_del) values(?,?)  ";
		Object[] params = { catelog_name, "no" };
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();

		req.setAttribute("message", "sucess");
		req.setAttribute("path", "category?type=categoryMana");
		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void dispatch(String targetURI, HttpServletRequest request,
			HttpServletResponse response) {
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				targetURI);
		try {
			dispatch.forward(request, response);
			return;
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void destroy() {

	}
}
