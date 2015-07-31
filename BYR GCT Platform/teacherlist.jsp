<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.hongshee.ejforum.util.PageUtils"%>
<%@ page import="com.hongshee.ejforum.common.ForumSetting"%>
<%@ page import="com.hongshee.ejforum.data.UserDAO.UserInfo"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("gbk");
	UserInfo userinfo = PageUtils.getSessionUser(request);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>导师列表</title>
	<LINK href="${ pageContext.request.contextPath }/styles/choosecollege.css" type=text/css rel=stylesheet>
	
	<script type="text/javascript">
		function hiddendiv(div) {
			div.style.display = (div.style.display == 'none'?'block':'none');
		}
		
	</script>
	
  </head>
  
  <body>
  	<div class="wrap">
  		<jsp:include page="head.jsp"></jsp:include>
	   <div id="headermenu"> <% if (userinfo != null) { %><p>欢迎您:&nbsp;<%=userinfo.userID %></p><%} else { response.sendRedirect("login.jsp");}%></div>
	   <div class="main">
	   <div class="chooseteachermain">
	   		<c:forEach items="${labs}" var="lab">
	   			<div class="chooselab">
	   				<a href="javascript:void(0)" onclick="hiddendiv(document.getElementById('${lab.labName}'))">${lab.labName }</a>
	   			</div>
	   			<div class="list" style="display: none" id="${lab.labName }">
	   				<table class="showteachers">
		   				<c:forEach items="${lab.teachers}" var="teacher">
		   					<tr ><td class="teacherlist"><a href="${ pageContext.request.contextPath }/servlet/SelectTeacher?name=${teacher.name }">${teacher.name }</a></td><td class="commentnum"><a href="">评论（${fn:length(teacher.comments) }）</a></td></tr>
		   				</c:forEach>
	   				</table>
	   			</div>
	   		</c:forEach>
	   </div>
	   </div>
   </div>
  </body>
</html>
