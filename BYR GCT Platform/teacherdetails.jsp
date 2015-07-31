<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.hongshee.ejforum.util.PageUtils"%>
<%@ page import="com.hongshee.ejforum.common.ForumSetting"%>
<%@ page import="com.hongshee.ejforum.data.UserDAO.UserInfo"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("gbk");
	UserInfo userinfo = PageUtils.getSessionUser(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>导师详情</title>
    <LINK href="${ pageContext.request.contextPath }/styles/teacherdetail.css" type=text/css rel=stylesheet>
  </head>
  
  <body>
   	<div class="wrap">
  		<jsp:include page="head.jsp"></jsp:include>
	   <div id="headermenu"> <% if (userinfo != null) { %><p>欢迎您:&nbsp;<%=userinfo.userID %></p><%} else { response.sendRedirect("login.jsp");}%></div>
	   <div class="main" >
		   <div class="choosecollegemain">
		   		<div style="height:1px;"></div>
		   		<div class="shorttitle">
		   		导师信息
		   		</div>
		   		<table class="detailtable">
		   			<tr><td class="param">姓名</td><td class="value">${teacher.name }</td></tr>
		   			<tr><td class="param">电子邮箱</td><td class="value">${teacher.email }</td></tr>
		   			<tr><td class="param">电话</td><td class="value">${teacher.phone }</td></tr>
		   			<tr><td class="param">简介</td><td class="value">${teacher.introduction }</td></tr>
		   		</table>
		   		<div class="shorttitle">
		   		评论
		   		</div>
		   		<table class="detailtable">
		   			<c:forEach items="${teacher.comments}" var="comment" >
		   				<tr><td class="param">${comment.username }</td><td class="value" rowspan="2">${comment.content }</td></tr>
		   				<tr><td class="param">${comment.publishTime }</td></tr>
		   			</c:forEach>
		   		</table>
		   		<div class="shorttitle">
		   		我也要评论
		   		</div>
		   		<form action="${pageContext.request.contextPath }/servlet/AddComment">
		   			<textarea rows="4" cols="105" name="content" id="textarea"></textarea>
		   			<input type="hidden" value="<%=userinfo.userID %>" name="username" />
		   			<input type="hidden" value="${teacher.name }" name="teacherName" />
		   			<input type="submit" value="提交评论" class="subbutton"/>
		   		</form>
		   </div>
		   
	   </div>
   </div>
  </body>
</html>
