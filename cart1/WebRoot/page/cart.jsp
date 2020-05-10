<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@ page import="com.orm.Tuser"%>
<%@ page import="com.orm.TOrderItem" %>
<%@ page import="java.util.*"%>
<%
String path = request.getContextPath();
Tuser user=null;
if((Tuser)request.getSession().getAttribute("user")!=null){
	user=(Tuser)request.getSession().getAttribute("user");
}



	if(user==null){
		%>
			<script type="text/javascript">
				alert("请先登录");
			window.location.href="<%=path%>/page/login.jsp";
			</script>
   <%
		return;
	 }
%>
<!DOCTYPE html>
<html>
<head>
<title>Contact</title>
<link href="<%=path%>/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=path%>/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Trudge Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--fonts-->
<link href='http://fonts.useso.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
<!--//fonts-->
<script type="text/javascript" src="<%=path%>/js/move-top.js"></script>
<script type="text/javascript" src="<%=path%>/js/easing.js"></script>
				<script type="text/javascript">
					jQuery(document).ready(function($) {
						$(".scroll").click(function(event){		
							event.preventDefault();
							$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
						});
					});
					
					function loginOut(){
						window.location.href="/cart/user?type=userLogout";
						
					}
					</script>

</head>
<body>
<!--header-->
	<div class="header">
		<div class="container">	
			<div class="logo">
				<a href="<%=path%>/page/index.jsp"><img src="<%=path%>/images/log.png" alt=""></a>
			</div>
				<div class="top-nav">
					<span class="menu"><img src="images/menu.png" alt="" ></span>
					<ul>
					    <li> <a href="<%=path%>/page/index.jsp">首页</a></li>
						<li><a href="<%=path %>/category?type=categoryAll">产品分类</a></li>
						<li><a href="<%=path%>/page/login.jsp">登陆</a></li>
						<li><a href="<%=path%>/page/register.jsp">注册</a></li>
							<li><a href="<%=path%>/page/cart.jsp">我的购物车</a></li>
					
						<li><a href="<%=path%>/page/about.jsp">关于我们</a></li>	
					    <%if (user!=null){
                                  %>
					               <li>
					                                  您好，<%=user.getUsername()%><a href="javascript:void(0)" onclick="loginOut()">[退出]</a> &nbsp;&nbsp;
					             </li>
					            <%
                                }
                      %>				
					</ul>
					<!--script-->
				<script>
					$("span.menu").click(function(){
						$(".top-nav ul").slideToggle(500, function(){
						});
					});
			</script>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	<div class="banner banner-in">
		<div class="container">
			<h6><span>购物车页面</span></h6>
		</div>
	</div>	
		<!---->
	
			<div class="container">
			<div class="contact">
			<div class="contact-in">
				
				 <div class=" col-md-9 contact-left">
					
					   <h3>我的购物车 </input></h3>
				
				<a href="<%=path %>/order?type=cartClear">清空购物车</a>&nbsp;
				
				<a href="<%=path %>/order?type=orderAll">查看我的订单</a> &nbsp;
				<a href="<%=path %>/page/cart.jsp">返回购物车</a> &nbsp;
			
	 <%if(session.getAttribute("cart")==null){%>
	   购物车目前为空！！！
     <%} else{%>
      <table width="850"  style="font-size:12px" cellpadding="0" cellspacing="0" border="2">
         <tr >
            <td  height="28"><div align="center">序号</div></td>
            <td ><div align="center">商品名称</div></td>
            <td ><div align="center">商品价格</div></td>
            <td ><div align="center">数量</div></td>
            <td ><div align="center">总价格</div></td>
             <td ><div align="center">操作</div></td>
        </tr>
       
       <%
            float sum=0;
        Vector cart=(Vector)session.getAttribute("cart");
        for(int i=0;i<cart.size();i++){
           TOrderItem item=(TOrderItem)cart.elementAt(i);
          sum=sum+item.goodnum*item.getGoodPrice();
          System.out.print("sum="+sum);
        %>
     
 

  <tr>
    <td height="28"><div align="center"><%=i+1%></div></td>
            <td><div align="center"><%=item.getGoodName()%></div></td>
            <td><div align="center"><%=item.getGoodPrice()%>yuan</div></td>
            <td><div align="center"><%=item.getGoodnum()%></td>
            <td><div align="center"><%=item.goodnum*item.getGoodPrice()%>yuan</div></td>
             <td><div align="center"><a href="<%=path%>/order?type=cartDel&goodID=<%=item.getGoodID()%>">删除</a></div></td>
  </tr>
 

 <%} 
        }%>
   <tr>
   </tr>
 </table>
					
					
					
		 <%if(session.getAttribute("cart")!=null){%>		
		 <form  action="<%=path %>/order?type=orderAdd" method="post">			
		</br>			
		 <p>
            
              请选择支付方式:<select id="paymode"  name="paymode">
              <option value="线下货到付款">线下货到付款</option>
              <option value="网银">网银</option>
              <option value="支付宝">支付宝</option>
            </select>
       
       </p>			
	
		</br>			
		 <p>
          <input type="submit" name="提交" value="提交">
       
      </p>							
	</form>			
	<% } %>						
					
					
					
					
					
					
					
					
					
				  </div>

				<div class=" col-md-3 contact-right">
				     	
				    </div>
					  <div class="clearfix"></div>
				 </div>
				
			   
      		</div>
		   </div>

			<div class="content-bottom-in">
				<div class="container">
					<ul class="social">
						<li><a href="#"><i> </i></a></li>						
						<li><a href="#"><i class="twitter"> </i></a></li>
						<li><a href="#"><i class="dribble"> </i></a></li>
						<li><a href="#"><i class="gmail"> </i></a></li>
					</ul>
				</div>
			</div>
	
		<!---->
	<div class="footer">
		<div class="container">
			<div class="footer-top">
					<div class="col-md-3 amet-sed">
					<h4></h4>
					<p></p> 
					<p></p>
					<p></p>
					<a href="#" ><i> </i></a>
					</div>
					<div class="col-md-3 amet-sed ">
					<h4></h4>
					
						<ul class="nav-bottom">
							<li> </li>
							<li></li>
							<li></li>	
							<li></li>								
						</ul>
					</div>
					<div class="col-md-3 amet-sed ">
						<h4></h4>
						<ul  class="nav-bottom">
							<li></li>						
							<li></a></li>
							<li></li>
							
						</ul>
					</div>
					<div class="col-md-3 amet-sed ">
						<h4></h4>
						<p></p>
						<p class="footer-class">Copyright &copy; 版权所有.</p>
						<p class="footer-class">Copyright &copy; 版权所有.</p>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
			 <script type="text/javascript">
						$(document).ready(function() {
							/*
							var defaults = {
					  			containerID: 'toTop', // fading element id
								containerHoverID: 'toTopHover', // fading element hover id
								scrollSpeed: 1200,
								easingType: 'linear' 
					 		};
							*/
							
							$().UItoTop({ easingType: 'easeOutQuart' });
							
						});
					</script>
				<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>

		</div>
</body>
</html>
			