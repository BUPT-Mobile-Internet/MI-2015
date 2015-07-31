<%@ page contentType="text/html;charset=gbk" errorPage="error.jsp"%>
<%@ page isELIgnored="false" %> 
<%
String path = request.getContextPath();
request.setAttribute("name","name");
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
  <body>
    <a href="${request.contextPath}/forum/index.jsp"><img src="${request.contextPath}/forum/images/logo2.PNG"/></a>
    <a href="${request.contextPath}/forum/choosecollege.jsp"><img src="${request.contextPath}/forum/images/logo1.PNG"/></a>
  </body>
</html>
