<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@ page import="com.orm.Tuser" %>
<%@ page import="com.orm.Tpingjia" %>
<%@ page import="com.orm.Tproduct" %>
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
<title>Single</title>
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
					<span class="menu"><img src="<%=path%>/images/menu.png" alt="" ></span>
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
			<h6><a href="<%=path%>/page/index.jsp">HOME</a>/ <span>详情页面</span></h6>
		</div>
	</div>	
		
		  <c:forEach items="${requestScope.productList}" var="product">
		<!---->
		<div class="container">
		<div class="single">
			
					<div class="single-middle">
						<div class="col-md-7 need">
							<img class="img-responsive" src="<%=path %>/${product.goodPic}" alt="">
						</div>
						<div class="col-md-5 tag">
							<div class="social-in">
								
								<div class="col-md-4 date">
									<p>名称 : ${product.goodName}</p>
									
								</div>
								<p><br/><br/></p>
								<div class="col-md-4 date">
									<p> 价格: ${product.price}</p>
									
								</div>
								<p><br/><br/></p>
								<div class="col-md-4 date">
									<p> 库存：${product.cunkun}</p>
										<p><br/></p>
								</div>
									<p><br/><br/></p>
								<div class="col-md-4 date">
									<p> 分类:${product.catergoryName}</p>
										<p><br/></p>
								</div>
							    <span class="actual"></span>
								 
								
								
								<div class="clearfix"> </div>
							</div>
							<p class="tag-in">
							<span>${product.goodMiaoshu}</span></p>
							
							  <form  action="<%=path %>/order?type=cartAdd" method="post">
						  数量：<input name="goodnum" type="text" class="shul" value="1">
					      <input type="hidden" name="goodName"  value="${product.goodName}">
					      <input type="hidden" name="goodPrice" value="${product.price}">
					      <input type="hidden" name="goodID" value="${product.goodID}">
					 <input type="submit" value="加入购物车" title=""> 
				  </form>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="comments-area">
		  		        	  <c:forEach items="${requestScope.pingjiaList}" var="pingjia">
			                  <p>${pingjia.username}于	${pingjia.createDate}</p>
			                  <p>内容：${pingjia.content}				
	                          </br>			
			                 </c:forEach>	
		  		        	<h3>我要留言</h3>
							<form action="<%=path %>/product?type=pingjiaAdd" method="post">
								<p>
									<span>留言内容</span>									
									<textarea name="content" ></textarea>
									 <input type="hidden" name="goodID" value="${product.goodID}">
								</p>
								<div class="sub-in">
									<input type="submit" value="提交">
								</div>
							</form>
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
		</c:forEach>	
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
			