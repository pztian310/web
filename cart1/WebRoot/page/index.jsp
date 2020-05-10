<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@ page import="com.orm.Tuser" %>
<%
String path = request.getContextPath();
Tuser user=null;
if((Tuser)request.getSession().getAttribute("user")!=null){
	user=(Tuser)request.getSession().getAttribute("user");
}
%>


<!DOCTYPE html>
<html>
<head>
<title>Home</title>
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
					<span class="menu"><img src="<%=path%>/images/menu.png" alt="" > </span>
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
                                	<li><a href="<%=path%>/admin/login.jsp">后台管理</a></li>				
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
		<div class="banner">
			<div class="container">	
				
				<p>购物网站</p>
				<a href="#" class="here"> 欢迎您的来到彭子恬、盛雯姝设计的购物网<i> </i></a>
			</div>
		</div>
		
        
			
			
	
		
	
</body>
</html>
			