<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.hongshee.ejforum.util.PageUtils"%>
<%@ page import="com.hongshee.ejforum.common.ForumSetting"%>
<%@ page import="com.hongshee.ejforum.data.UserDAO.UserInfo"%>
<%@ page import="java.net.URLEncoder" %>
<%
	UserInfo userinfo = PageUtils.getSessionUser(request);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	
    <LINK href="./styles/choosecollege.css" type=text/css rel=stylesheet>
    
  </head>
  
  <body>
   
   <div class="wrap">
  		<jsp:include page="head.jsp"></jsp:include>
	   <div id="headermenu">
	   <% if (userinfo != null) { %><p>��ӭ��:&nbsp;<%=userinfo.userID %></p><%} else { response.sendRedirect("login.jsp");}%> 
	   
	   </div>
	   <div class="main">
	   <div class="dec">
	   </div>
	   <div class="choosecollegemain">
	   		<table class="tableform">
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=��Ϣ��ͨ�Ź���ѧԺ">��Ϣ��ͨ�Ź���ѧԺ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=���ӹ���ѧԺ">���ӹ���ѧԺ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=��Ϣ����ѧ���ͨ���о�Ժ">��Ϣ����ѧ���ͨ���о�Ժ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=�����ѧԺ">�����ѧԺ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=���缼���о�Ժ">���缼���о�Ժ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=���������о���">���������о���</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=�Զ���ѧԺ">�Զ���ѧԺ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=��ѧԺ">��ѧԺ</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=���ѧԺ">���ѧԺ</a></td></tr>
	   		</table>
	   </div>
	   </div>
   </div>
  </body>
</html>
