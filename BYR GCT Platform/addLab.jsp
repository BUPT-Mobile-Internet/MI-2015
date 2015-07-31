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
  	<form action="${ pageContext.request.contextPath }/servlet/AddLab">
  		学院名：<input type="text" name="collegeName" value="信息与通信工程学院"/>
  		实验室名：<input type="text" name="labName"/>
  		<input type="submit"/>
  	</form>
  </body>
</html>
