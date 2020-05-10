<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
			<script language="JavaScript" src="<%=path %>/js/public.js" type="text/javascript"></script>
        <script language="javascript">
           function adminDel(userId)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path %>/product?type=productDel&id="+userId;
               }
           }
           
           function adminAdd()
           {
                 var url="<%=path %>/admin/product/productAdd.jsp";
				 window.location.href=url;
           }
           
           function over(picPath)
	       {
			 
        	  if (picPath=="")  picPath="<%=path %>/images/album_no_pic.gif";
			  x = event.clientX;
			  y = event.clientY;      
			  document.all.tip.style.display = "block";
			  document.all.tip.style.top = y;
			  document.all.tip.style.left = x+10;
			  document.all.photo.src =picPath; 
		   }
		   
           function out()
	       {
			  document.all.tip.style.display = "none";
		   }   
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="8" background="<%=path %>/img/tbg.gif">&nbsp;所有产品&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="10%">商品ID</td>
					<td width="20%">商品名称</td>
					<td width="30%">商品描述</td>
				    <td width="10%">图片</td>
				    <td width="10%">类别</td>
				    <td width="10%">市场价</td>
				    <td width="5%">库存</td>
				    <td width="5%">操作</td>
		        </tr>	
				<c:forEach items="${requestScope.productList}" var="product">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
							${product.goodID}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${product.goodName}
					</td>
					<td bgcolor="#FFFFFF" align="center">
							${product.goodMiaoshu}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<img src="<%=path%>${product.goodPic}" with="60px" height="60px"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
							${product.catergoryName}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${product.price}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						 	${product.cunkun}  
					</td>
					<td bgcolor="#FFFFFF" align="center">
						     <input type="button" value="删除" onclick="adminDel(${product.goodID})"/>  
					</td>
				</tr>
				</c:forEach>
			</table>
			
				<table width='98%'  border='0'style="margin-top:8px;margin-left: 5px;">
			  <tr>
			    <td>
			      <input type="button" value="添加" style="width: 80px;" onclick="adminAdd()" />
			    </td>
			  </tr>
		    </table>
	</body>
</html>
