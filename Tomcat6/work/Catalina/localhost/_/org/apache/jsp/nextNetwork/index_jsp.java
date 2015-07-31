package org.apache.jsp.nextNetwork;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.nnw.dao.impl.UserDaoImpl;
import cn.nnw.domain.User;
import java.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username=null;
String password=null;
Cookie myCookie[]=request.getCookies();//创建一个Cookie对象数组 
System.out.println(">>>>>>>>>>>>>>----");
for(int i=0;myCookie!=null&&i<myCookie.length;i++){
	 //设立一个循环，来访问Cookie对象数组的每一个元素 
	Cookie newCookie= myCookie[i]; 
	
	if(newCookie.getName().equals("username")){
		username = newCookie.getValue();
		System.out.println(">>>>>>>>>>>>>>"+username);
	}
	if(newCookie.getName().equals("password")){
		password = newCookie.getValue();
		System.out.println(">>>>"+password);
	}
}
if(username!=null&&password!=null){
	cn.nnw.dao.UserDao dao = new UserDaoImpl();
	User use = dao.find(username,password);
	if(use!=null){
		request.getSession().setAttribute("user", use);
		//让用户登陆成功后，跳转首页
		/* response.sendRedirect(request.getContextPath() + "/index.jsp");
		return; */
	}
}
		

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<title>未来互联网首页</title>\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n");
      out.write("<meta name=\"description\" content=\"\" />\r\n");
      out.write("<meta name=\"author\" content=\"\" />\r\n");
      out.write("\r\n");
      out.write("<!-- Le styles -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/bootstrap.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/inettuts.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/inettuts.js.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/next-network.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/bootstrap-responsive.css \" />\r\n");
      out.write("<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("    <script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("    <![endif]-->\r\n");
      out.write("\r\n");
      out.write("<!-- Le fav and touch icons -->\r\n");
      out.write("<link rel=\"shortcut icon\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/favicon.ico \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"144x144\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-144-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"114x114\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-114-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"72x72\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-72-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-57-precomposed.png \" />\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write("\tpadding-top: 60px;\r\n");
      out.write("\t/* 60px to make the container go all the way to the bottom of the topbar */\r\n");
      out.write("\tbackground: white;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#logo {\r\n");
      out.write("\tdisplay: table-cell;\r\n");
      out.write("\tvertical-align: middle;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/* add by me */\r\n");
      out.write("#columns #column1 {\r\n");
      out.write("\tbackground-image: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#columns #column3 {\r\n");
      out.write("\tbackground-image: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#columns .widget .widget-head {\r\n");
      out.write("\theight: 35px;\r\n");
      out.write("}\r\n");
      out.write(".uneditable-input {\r\n");
      out.write("  height: 30px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"navbar navbar-inverse navbar-fixed-top\">\r\n");
      out.write("\t\t\t<div class=\"navbar-inner\">\r\n");
      out.write("\t\t\t\t<a class=\"brand\" href=\" ccnx://bupt/index.jsp\" title=\"记忆犹新\"\r\n");
      out.write("\t\t\t\t\tstyle=\"padding-left: 28px;\">Next Network</a>\r\n");
      out.write("\t\t\t\t<ul id=\"navigation\" class=\"nav\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<li class=\"active\"><a href=\" ccnx://bupt/category/memory\">首页</a></li>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<li><a href=\" ccnx://bupt//category/lover\">测试1</a></li>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<li><a href=\" ccnx://bupt//category/travel\">测试2</a></li>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<li><a href=\" ccnx://bupt//category/flavor\">测试3</a></li>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<li><a href=\" ccnx://bupt//category/timeline\">测试4</a></li>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<ul class=\"nav nav-pills pull-right\" style=\"padding-right: 30px;\">\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                     ");
      out.write("\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- 下拉应用列表框 -->\r\n");
      out.write("\t<div id=\"app-list\" class=\"collapse\"\r\n");
      out.write("\t\tstyle=\"margin-top: -19px; padding-top: 13px; background-image: url(img/intro-threejs-header-pattern.png);\">\r\n");
      out.write("\t\t<table>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"background-color:#87cefa;\">\r\n");
      out.write("\t\t\t\t\t<div style=\"margin:10px;margin-top: -80px;\">\r\n");
      out.write("\t\t\t\t\t\t<ul class=\"nav nav-list\" id=\"myTab\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"max-width: 60px; padding: 8px 0\">\r\n");
      out.write("\t\t\t\t\t\t\t<li class=\"active\"><a data-toggle=\"tab\" href=\"#yule\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"color:#000000\">生活娱乐</a>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class=\"\"><a data-toggle=\"tab\" href=\"#xinxi\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"color:#000000\">新闻信息</a>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class=\"\"><a data-toggle=\"tab\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"color:#000000\">待加入</a>\r\n");
      out.write("\t\t\t\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t<div class=\"tab-content\" id=\"myTabContent\">\r\n");
      out.write("\t\t\t\t\t\t<div id=\"yule\" class=\"tab-pane fade active in\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t日历 <a class=\"pull-right app-module\" id=\"moduleid-1\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t天气 <a class=\"pull-right app-module\" id=\"moduleid-2\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/weather.png\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t音乐 <a class=\"pull-right app-module\" id=\"moduleid-3\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\" src=\" ccnx://bupt/img/music.png\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t地图 <a class=\"pull-right app-module\" id=\"moduleid-7\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/shishiredian.png\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t视频 <a class=\"pull-right app-module\" id=\"moduleid-6\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/shishiredian.png\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- <div>个性推荐\r\n");
      out.write("                                    <a class=\"pull-right app-module\" id=\"moduleid-5\" href=\"#\" title=\"添加\"> <i class=\"icon-plus\"></i>\r\n");
      out.write("                                </a>\r\n");
      out.write("                                </div>\r\n");
      out.write("                                <hr style=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("                                <img class=\"img-rounded app-image\" alt=\"100x100\" style=\"width: 180px; height: 80px;\" src=\" ccnx://bupt/img/gexingtuijian.png\"></div> -->\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>title</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\twell5\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>title</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\twell5\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"xinxi\" class=\"tab-pane fade\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t实时热点 <a class=\"pull-right app-module\" id=\"moduleid-4\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t个性推荐 <a class=\"pull-right app-module\" id=\"moduleid-5\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t最常访问 <a class=\"pull-right app-module\" id=\"moduleid-8\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t推荐新闻 <a class=\"pull-right app-module\" id=\"moduleid-9\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t维基百科 <a class=\"pull-right app-module\" id=\"moduleid-10\" href=\"#\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"添加\"> <i class=\"icon-plus\"></i> </a>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img class=\"img-rounded app-image\" alt=\"100x100\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"width: 180px; height: 80px;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tsrc=\" ccnx://bupt/img/calendar.jpg\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>title</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\twell5\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"well well-large span2\"\r\n");
      out.write("\t\t\t\t\t\t\t\tstyle=\"width: 120px; margin-left:10px\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>title</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr\r\n");
      out.write("\t\t\t\t\t\t\t\t\tstyle=\"margin: 1px 0; color : #E6E6FA; border-bottom : 1px solid lavender;\">\r\n");
      out.write("\t\t\t\t\t\t\t\twell5\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t<img id=\"logo\" style=\"width:250px; height: 150px; margin: auto;\"\r\n");
      out.write("\t\t\tsrc=\" ccnx://bupt/img/new.jpg\" alt=\"\" />\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"row\">\r\n");
      out.write("\t\t<div class=\"span6 offset4\">\r\n");
      out.write("\t\t\t<form action=\"\">\r\n");
      out.write("\t\t\t\t<div class=\"input-append\">\r\n");
      out.write("\t\t\t\t\t<input class=\"span5\" id=\"appendedInputButtons\" type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tplaceholder=\"搜索一下\" required>\r\n");
      out.write("\t\t\t\t\t<!-- autofocus -->\r\n");
      out.write("\t\t\t\t\t<button class=\"btn\" type=\"button\">Search</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"columns\">\r\n");
      out.write("\r\n");
      out.write("\t\t<ul id=\"column1\" class=\"column\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<!-- <input type=\"hidden\" id=\"orderlist1\" /> -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-green\" id=\"intro\" >\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>我的导航</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\" >\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li>\r\n");
      out.write("                        <a href=\"http://www.baidu.com\" style=\"color:black\">www.baidu.com</a>\r\n");
      out.write("                    </li>\r\n");
      out.write("                    <li>\r\n");
      out.write("                        <a href=\"http://www.baidu.com\" style=\"color:black\">www.sina.com.cn</a>\r\n");
      out.write("                    </li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("        ");
      if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\t\t<ul id=\"column2\" class=\"column\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<!--      <input type=\"hidden\" id=\"orderlist2\" /> -->\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-yellow\" id=\"appid-6\">\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>推荐视频</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("                        <embed class=\"app-flash\" src=\" ccnx://bupt/http://player.youku.com/player.php/sid/XNTQ2NzEyMDI0/v.swf\" quality=\"high\" allowfullscreen=\"true\" flashvars=\"playMovie=true&auto=1\" pluginspage=\"http://get.adobe.com/cn/flashplayer/\" style=\"visibility: visible;\" allowscriptaccess=\"never\"\r\n");
      out.write("                        width=\"415\" height=\"330\" \r\n");
      out.write("                        type=\"application/x-shockwave-flash\"></embed>\r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-yellow\">    \r\n");
      out.write("    <div class=\"widget-head\">\r\n");
      out.write("        <h3>视频</h3>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"widget-content\">\r\n");
      out.write("        <p>视频:日本右翼抵达钓附近海域 10艘海保船护航</p>\r\n");
      out.write("        <embed src=\" ccnx://bupt/http://you.video.sina.com.cn/api/sinawebApi/outplay.php/P060S3A4B27K+l1lHz2stssM5aINt8vji2m3vFatJBEZDFjhZoPdK51SjyvJRpYWnm1NRpo3ffYn1gJOMfQJomp0MmYSnRSGN7cMObXR5KGZOwEd8XFHrnimAN1ioaFYgSpOExLVx7QC4gOTpDHUcWCy7gU1CoWDU0N+wlQ2rMdGrBCU3Ope5hLT27zKFcYpr3VcZSaKrKcuxA.swf\" quality=\"high\" allowfullscreen=\"true\" flashvars=\"playMovie=true&auto=1\" pluginspage=\"http://get.adobe.com/cn/flashplayer/\" style=\"visibility: visible;\" allowscriptaccess=\"never\"\r\n");
      out.write("                        width=\"393\" height=\"300\" \r\n");
      out.write("                        type=\"application/x-shockwave-flash\"></embed>\r\n");
      out.write("    <!--  http://you.video.sina.com.cn/api/sinawebApi/outplay.php/P060S3A4B27K+l1lHz2stssM5aINt8vji2m3vFatJBEZDFjhZoPdK51SjyvJRpYWnm1NRpo3ffYn1gJOMfQJomp0MmYSnRSGN7cMObXR5KGZOwEd8XFHrnimAN1ioaFYgSpOExLVx7QC4gOTpDHUcWCy7gU1CoWDU0N+wlQ2rMdGrBCU3Ope5hLT27zKFcYpr3VcZSaKrKcuxA.swf\r\n");
      out.write("                        http://video.sina.com.cn/p/news/c/v/2013-04-23/102662337491.html \r\n");
      out.write("                        <!-- http://v.youku.com/v_show/id_XNTQ2NzEyMDI0.html\r\n");
      out.write("                       <!--  http://v.youku.com/v_show/id_XNDQ1MjIyODg4.html -->\r\n");
      out.write("\t\t\t<!-- </div> </li>\r\n");
      out.write("    -->\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-yellow\" id=\"appid-7\">\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>地图</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("                 <iframe scrolling=\"no\" style=\"width: 100%; height: 360px;\" frameborder=\"0\" class=\"gwt-Frame\" id=\"remote_iframe_9\" name=\"remote_iframe_9\" src=\" ccnx://bupt/http://www-ig-opensocial.googleusercontent.com/gadgets/ifr?exp_rpc_js=1&amp;exp_track_js=1&amp;url=http%3A%2F%2Fwww.gstatic.com%2Fig%2Fmodules%2Fmapsearch%2Flocalsearch_v2.xml&amp;container=ig&amp;view=home&amp;lang=zh-cn&amp;country=US&amp;sanitize=0&amp;v=35542839c232e0b0&amp;parent=http://www.google.com&amp;libs=core:core.io:core.iglegacy:auth-refresh&amp;is_signedin=1&amp;synd=ig&amp;mid=9#st=c%3Dig%26e%3DAPu7icpd7cn0k4ucChZMDXBWX14o/GwKRI66L3IJIm0PtA2FjfnyZvrXgLYoQr3ZK4DbxJuwabZ39JuIkVm0FTk/Y9CuNeHTZ1sn9oLofali7u%252BI9fVUn4uwahrmxYc8%252BeXtnV9kRZoD&amp;gadgetId=115506503466011460772&amp;gadgetOwner=118187998234078167265&amp;gadgetViewer=118187998234078167265&amp;rpctoken=-1020536692&amp;ifpctok=-1020536692&amp;up_traffic=&amp;up_trafficMode=false&amp;up_transitionQuery=&amp;up_mapType=roadmap&amp;up_locationCacheLat=37.788081&amp;up_idleZoom=11&amp;up_location=%E6%97%A7%E9%87%91%E5%B1%B1&amp;up_rawquery=&amp;up_kml=false&amp;up_largeMapMode=true&amp;up_locationCacheLng=-122.409668&amp;up_selectedtext=&amp;up_locationCacheString=\"> </iframe>\r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\t\t<ul id=\"column3\" class=\"column\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<!--  <input type=\"hidden\" id=\"orderlist3\" /> -->\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-white\" id=\"appid-4\">\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>实时热点</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li>\r\n");
      out.write("                         <a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
      out.write("                    </li>\r\n");
      out.write("                </ul>\r\n");
      out.write("               \r\n");
      out.write("                \r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("\t\t\t<!-- <li id=\"appid-5\" class=\"widget color-white\" >\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>个性推荐</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("               <ul>\r\n");
      out.write("                    <li>\r\n");
      out.write("                         <a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
      out.write("                    </li>\r\n");
      out.write("                </ul>\r\n");
      out.write("                \r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!--  <li id=\"appid-3\" class=\"widget color-yellow\" >\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>百度音乐</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("                <iframe width=\"420\" scrolling=\"no\" height=\"210\" frameborder=\"0\" src=\"http://play.baidu.com/player/hao123/?r=1366853429566#top/dayhot\" data-url=\"http://play.baidu.com/player/hao123/\" id=\"funnynew-iframe\"></iframe>\r\n");
      out.write("            </div>\r\n");
      out.write("        </li> -->\r\n");
      out.write("        ");
      if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- <li id=\"appid-1\" class=\"widget color-white\">\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>日历</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"widget-content\">\r\n");
      out.write("                <div style=\"margin-left: 9px\">\r\n");
      out.write("                    <a href=\"#calendarModal\"  data-toggle=\"modal\">\r\n");
      out.write("                            <img class=\"img-rounded app-image\" alt=\"100x100\" style=\" width: 430px; height: 150px;\" src=\" ccnx://bupt/img/calendarhref.png\">\r\n");
      out.write("                    </a>\r\n");
      out.write("                </div>\r\n");
      out.write("                \r\n");
      out.write("            </div>\r\n");
      out.write("        </li>\r\n");
      out.write(" -->\r\n");
      out.write("\t<div id=\"calendarModal\" class=\"modal hide fade\" tabindex=\"-1\"\r\n");
      out.write("\t\trole=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n");
      out.write("\t\t\t<h3>日历</h3>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div style=\" margin-left: 9px\">\r\n");
      out.write("\t\t\t<iframe width=\"100%\" scrolling=\"no\" frameborder=\"0\"\r\n");
      out.write("\t\t\t\tname=\"BAPPIframeCanvas114629136775647655430\"\r\n");
      out.write("\t\t\t\tsrc=\"");
      out.print(basePath );
      out.write("wnl.html\" allowtransparency=\"true\"\r\n");
      out.write("\t\t\t\tstyle=\"background-color: transparent; height: 440px;\"\r\n");
      out.write("\t\t\t\tid=\"BAPPIframeCanvas114629136775647655430\"></iframe>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!--?keyword=%25E6%2597%25A5%25E5%258E%2586&amp;canvas_pos=platform&amp;bd_user=302920995&amp;bd_sig=25da4000bb7c243063cfce48e2d3afa0&amp;canvas_pos=platform -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"loginModal\" class=\"modal hide fade\" tabindex=\"-1\"\r\n");
      out.write("\t\trole=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\r\n");
      out.write("\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\r\n");
      out.write("\t\t\t<h3>请先登录</h3>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t<h4>使用微博账号登录</h4>\r\n");
      out.write("\t\t\t<p>更好的收藏和分享，并发现好友的推荐！</p>\r\n");
      out.write("\t\t\t<div style=\"text-align: center\">\r\n");
      out.write("\t\t\t\t<div class=\"btn-group\"\r\n");
      out.write("\t\t\t\t\tstyle=\"display:inline-block; margin-left: 3px\">\r\n");
      out.write("\t\t\t\t\t<button class=\"btn btn-large btn-danger dropdown-toggle\"\r\n");
      out.write("\t\t\t\t\t\tdata-toggle=\"dropdown\" style=\"padding: 6px 14px\">\r\n");
      out.write("\t\t\t\t\t\t微博登录&nbsp; <span class=\"caret\"></span>\r\n");
      out.write("\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t<ul id=\"share\" class=\"dropdown-menu\">\r\n");
      out.write("\t\t\t\t\t\t<li class=\"rec-sina\"><a href=\" ccnx://bupt/user/sina/oauth\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"font-size: 12px;\">新浪微博</a></li>\r\n");
      out.write("\t\t\t\t\t\t<!--                    <li class=\"rec-tx\">\r\n");
      out.write("                    -->\r\n");
      out.write("\t\t\t\t\t\t<!--                        <a href=\"-->\r\n");
      out.write("\t\t\t\t\t\t<!--\" style=\"font-size: 12px;\">\r\n");
      out.write("                    -->\r\n");
      out.write("\t\t\t\t\t\t<!--                            腾讯微博-->\r\n");
      out.write("\t\t\t\t\t\t<!--                        </a>\r\n");
      out.write("                    -->\r\n");
      out.write("\t\t\t\t\t\t<!--                    </li>-->\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<hr style=\"size: 2px\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t<form class=\"form-horizontal\" id=\"login-form\" action=\"ccnx://bupt/servlet/LoginServlet\"\r\n");
      out.write("\t\t\t\tmethod=\"post\">\r\n");
      out.write("\t\t\t\t<fieldset>\r\n");
      out.write("\t\t\t\t\t<h4>使用本站账号登录</h4>\r\n");
      out.write("\t\t\t\t\t<p></p>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<div class=\"control-group\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"control-label login\"> 邮箱 <font\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"color: red\">*</font> </label>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"login-controls\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-prepend\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"add-on\"> <i class=\"icon-envelope\"></i> </span> <input\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"span3\" size=\"16\" name=\"LoginForm_email\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"LoginForm_email\" type=\"text\"> <span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"errorMessage\" id=\"LoginForm_email_em_\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tstyle=\"display:none\"></div> </span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<div class=\"control-group\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"control-label login\"> 密码 <font\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"color: red\">*</font> </label>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"login-controls\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-prepend\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"add-on\"> <i class=\"icon-asterisk\"></i> </span> <input\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"span3\" size=\"16\" name=\"LoginForm_password\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"LoginForm_password\" type=\"password\"> <span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"errorMessage\" id=\"LoginForm_password_em_\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tstyle=\"display:none\"></div> </span>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<label class=\"checkbox login-checkbox\"> <input\r\n");
      out.write("\t\t\t\t\t\tname=\"LoginForm_checkbox\" class=\"login-checkbox\" type=\"checkbox\">下次自动登录(一周）</label>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<div style=\"text-align: center\">\r\n");
      out.write("\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-info span2\">登录</button>\r\n");
      out.write("\t\t\t\t\t\t<a href=\" ccnx://bupt/login.jsp\" class=\"btn span2\">注册</a>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</fieldset>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- <input name=txt><input type=button value=setday onclick=\"setday(this,document.all.txt)\"> -->\r\n");
      out.write("\t<!-- <input onfocus=\"setday(this,'hehe')\" type=\"text\"> -->\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"username\" title=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.useremail}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></div>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/jquery-1.7.2.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/bootstrap.min.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/jquery-ui-1.10.3.custom.js \"></script>\r\n");
      out.write("\t<!-- <script type=\"text/javascript\" srcsrc=\" ccnx://bupt/ ccnx://bupt//jquery-ui-personalized-1.6rc2.min.js \"></script> -->\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/inettuts.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/index.js \"></script>\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t<!-- <script type=\"text/javascript\" src=\" ccnx://bupt/ js/calendar.js \"></script> -->\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /nextNetwork/index.jsp(128,4) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user!=null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t");
        out.write("\r\n");
        out.write("\t\t\t\t\t<li>\r\n");
        out.write("\t\t\t\t\t\t<div class=\"btn-group\">\r\n");
        out.write("\t\t\t\t\t\t\t<a class=\"btn dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<i class=\"icon-user icon-black\"></i> &nbsp; ");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.useremail}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write(" <span\r\n");
        out.write("\t\t\t\t\t\t\t\tclass=\"caret\"></span> </a>\r\n");
        out.write("\t\t\t\t\t\t\t<ul class=\"dropdown-menu\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<li><a href=\"#\">个人资料</a></li>\r\n");
        out.write("\t\t\t\t\t\t\t\t<li><a href=\"ccnx://bupt/servlet/LogoutServlet\">退出</a></li>\r\n");
        out.write("\t\t\t\t\t\t\t</ul>\r\n");
        out.write("\t\t\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t\t\t<li>\r\n");
        out.write("\t\t\t\t\t\t<a class=\"pull-right accordion-toggle\" href=\"#\"\r\n");
        out.write("\t\t\t\t\t\tdata-toggle=\"collapse\" data-target=\"#app-list\"> <i\r\n");
        out.write("\t\t\t\t\t\t\tclass=\"icon-plus icon-white\"></i> 添加应用 </a></li>\r\n");
        out.write("\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /nextNetwork/index.jsp(146,5) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user==null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<li><a href=\"#loginModal\" class=\"dropdown-toggle\"\r\n");
        out.write("\t\t\t\t\t\tdata-toggle=\"modal\"> <i class=\"icon-user icon-white\"></i>\r\n");
        out.write("\t\t\t\t\t\t\t&nbsp; <span style=\"color:white\">登录/注册</span> <!--\r\n");
        out.write("                            <a class=\"pull-right\" href=\"/auth/register\">注册</a>\r\n");
        out.write("                        <a class=\"pull-right\" href=\"/auth/signin\">登录</a>\r\n");
        out.write("                        --> </a></li>\r\n");
        out.write("\t\t\t\t\t<li><a href=\"#loginModal\" class=\"dropdown-toggle\"\r\n");
        out.write("\t\t\t\t\t\tdata-toggle=\"modal\"> <i class=\"icon-plus icon-white\"></i>\r\n");
        out.write("\t\t\t\t\t\t\t添加应用 <!--\r\n");
        out.write("                            <a class=\"pull-right\" href=\"/auth/register\">注册</a>\r\n");
        out.write("                    <a class=\"pull-right\" href=\"/auth/signin\">登录</a>\r\n");
        out.write("                    --> </a></li>\r\n");
        out.write("                    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent(null);
    // /nextNetwork/index.jsp(389,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user==null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t<li class=\"widget color-orange\" id=\"appid-8\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>最常访问</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<ul>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">forum.byr.edu.cn</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t</ul>\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t<!-- <li class=\"widget color-white\" id=\"appid-4\">\r\n");
        out.write("            <div class=\"widget-head\">\r\n");
        out.write("                <h3>实时热点</h3>\r\n");
        out.write("            </div>\r\n");
        out.write("            <div class=\"widget-content\">\r\n");
        out.write("                <ul>\r\n");
        out.write("                    <li>\r\n");
        out.write("                         <a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("                    </li>\r\n");
        out.write("                </ul>\r\n");
        out.write("               \r\n");
        out.write("                \r\n");
        out.write("            </div>\r\n");
        out.write("        </li> -->\r\n");
        out.write("\t\t\t<li id=\"appid-5\" class=\"widget color-white\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>个性推荐</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<ul>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t</ul>\r\n");
        out.write("\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent(null);
    // /nextNetwork/index.jsp(509,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user==null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t<li class=\"widget color-white\" id=\"appid-9\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>推荐新闻</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<iframe scrolling=\"no\" frameborder=\"0\" class=\"gwt-Frame\"\r\n");
        out.write("\t\t\t\t\t\tid=\"remote_iframe_7\" name=\"remote_iframe_7\"\r\n");
        out.write("\t\t\t\t\t\tsrc=\"http://www-ig-opensocial.googleusercontent.com/gadgets/ifr?exp_rpc_js=1&amp;exp_track_js=1&amp;url=http%3A%2F%2Fwww.gstatic.com%2Fig%2Fmodules%2Ftabnews%2Ftabnews_v2.xml&amp;container=ig&amp;view=home&amp;lang=zh-cn&amp;country=US&amp;sanitize=0&amp;v=dadcd56d7947b0c3&amp;parent=http://www.google.com&amp;libs=core:core.io:core.iglegacy:auth-refresh&amp;is_signedin=1&amp;synd=ig&amp;mid=7#st=c%3Dig%26e%3DAPu7icqgoiRyfiv3LNxBal1g1xLRrkfHbIIqHSakgNnPe7WQ%252Bx7bTvCpDcYtgd/ZY5Tzf0sn/QTGkqfDUzWkxmBQzRrruG3fboIDsDVD91JYfT3qzbAVgXT7DSumqfBDUN8QjUZYn2Oh&amp;gadgetId=111502982863977288083&amp;gadgetOwner=118187998234078167265&amp;gadgetViewer=118187998234078167265&amp;rpctoken=2145383376&amp;ifpctok=2145383376&amp;up_items=5&amp;up_ned=&amp;up_queryList=&amp;up_font_size=13pt&amp;up_selectedTab=0&amp;up_tabs=h,b,t,s,e&amp;up_last_url=http://ajax.googleapis.com/ajax/services/search/news%3Fv%3D1.0%26hide%3Drelated%26key%3Dinternal-ig-tabnews%26ned%3Dcn%26topic%3Dh%26rsz%3Dlarge&amp;up_onebox=&amp;up_show_image=0\"\r\n");
        out.write("\t\t\t\t\t\tstyle=\"width: 100%;height: 234px;\"></iframe>\r\n");
        out.write("\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t<li class=\"widget color-white\" id=\"appid-10\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>维基百科</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<iframe scrolling=\"no\" frameborder=\"0\" class=\"gwt-Frame\"\r\n");
        out.write("\t\t\t\t\t\tid=\"remote_iframe_13\" name=\"remote_iframe_13\"\r\n");
        out.write("\t\t\t\t\t\tsrc=\"http://www-ig-opensocial.googleusercontent.com/gadgets/ifr?exp_rpc_js=1&amp;exp_track_js=1&amp;url=http%3A%2F%2Fwww.gstatic.com%2Fig%2Fmodules%2Fwikipedia%2Fwikipedia_v2.xml&amp;container=ig&amp;view=home&amp;lang=zh-cn&amp;country=US&amp;sanitize=0&amp;v=d5881d8791fc194c&amp;parent=http://www.google.com&amp;libs=core:core.io:core.iglegacy:auth-refresh&amp;is_signedin=1&amp;synd=ig&amp;mid=13#st=c%3Dig%26e%3DAPu7icqc6NhL/5CM2CGjUnYl0k39rkBTx19eieqDnMfIEb9xx/3yd3BgV%252B8VKfn78K3EQ/tmivLdxB7gC5x9wXVB//0/q2qhWW6q97AxdvaSKGoVKA9XcO/IrSO8q/xAB0yJMyDTPkbn&amp;gadgetId=116039311165796053871&amp;gadgetOwner=118187998234078167265&amp;gadgetViewer=118187998234078167265&amp;rpctoken=-891628792&amp;ifpctok=-891628792&amp;up_isChecked=true\"\r\n");
        out.write("\t\t\t\t\t\tstyle=\"width: 100%;height: 266px;\"></iframe>\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }
}
