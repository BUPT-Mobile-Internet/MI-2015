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
	   <% if (userinfo != null) { %><p>欢迎您:&nbsp;<%=userinfo.userID %></p><%} else { response.sendRedirect("login.jsp");}%> 
	   
	   </div>
	   <div class="main">
	   <div class="dec">
	   </div>
	   <div class="choosecollegemain">
	   		<table class="tableform">
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=信息与通信工程学院">信息与通信工程学院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=电子工程学院">电子工程学院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=信息光子学与光通信研究院">信息光子学与光通信研究院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=计算机学院">计算机学院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=网络技术研究院">网络技术研究院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=教育技术研究所">教育技术研究所</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=自动化学院">自动化学院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=理学院">理学院</a></td></tr>
	   				<tr><td><a href="${ pageContext.request.contextPath }/servlet/SelectCollege?collegename=软件学院">软件学院</a></td></tr>
	   		</table>
	   </div>
	   </div>
   </div>
  </body>
</html>
