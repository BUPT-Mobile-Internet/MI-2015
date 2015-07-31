<%@ page contentType="text/html;charset=gbk"%>
<%
request.setCharacterEncoding("gbk");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    

  </head>
  
  <body>
   	<form action="${ pageContext.request.contextPath }/servlet/AddTeacher">
  		实验室名：<input type="text" name="labName"/>
  		导师名:<input type="text" name="name"/>
  		电话：<input type="text" name="phone"/>
  		email: <input type="text" name="email"/>
  		简介：<input type="text" name="introduction"/>
  		<input type="submit"/>
  	</form>
  </body>
</html>
