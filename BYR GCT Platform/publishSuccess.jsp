<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.hongshee.ejforum.util.PageUtils"%>
<%@ page import="com.hongshee.ejforum.common.ForumSetting"%>
<%@ page import="com.hongshee.ejforum.data.UserDAO.UserInfo"%>
<%
request.setCharacterEncoding("gbk");
UserInfo userinfo = PageUtils.getSessionUser(request);
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>�������۳ɹ�</title>
	<meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/servlet/SelectTeacher?name=${name}" />
	<LINK href="${ pageContext.request.contextPath }/styles/teacherdetail.css" type=text/css rel=stylesheet>
  </head>
  
  <body>
  		<div class="wrap">
  		<jsp:include page="head.jsp"></jsp:include>
	   <div id="headermenu"> <% if (userinfo != null) { %><p>��ӭ��:&nbsp;<%=userinfo.userID %></p><%} else { response.sendRedirect("login.jsp");}%></div>
	   <div style="font-size: 18px;margin:20px;">�������۳ɹ���������ת�ص�ʦҳ�档</div>
	   
   </div>
  </body>
</html>
