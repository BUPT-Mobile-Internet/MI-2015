package cn.nnw.init;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.nnw.domain.Moduleinfo;
import cn.nnw.domain.User;
import cn.nnw.utils.HibernateUtils;

public class DbInitServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("oppopopopo");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}//onclick='alert(\\\"hehehe\\\");'
	static {
	
		String up_hotmodule = "<li class='widget color-white' id='appid-4'><div class='widget-head'><h3>实时热点</h3></div><div class='widget-content'><a id='hotchange'  onclick='hotchange();' shape='rect'  style='float: right;color:black;cursor:pointer;'><i class='icon-repeat'></i> 换一换</a><ul>";
	    String down_hotmodule = "</ul></div></li>";
	    String mid_hotmodule;
	    String hotmodule;
	    String hothtml;
	    String hotlink;
	    String title;
	    String hotvideo="";
	    Session session = null;
	   // 显示的热点数
	    int hotcount = 4;
	   	try {
	   		 session = HibernateUtils.getSession();
	   		 session.beginTransaction();
	   		 
	   		 String hql = " select hotlink, title from Hotlink";      
	         Query query = session.createQuery(hql);      
	        //默认查询出来的list里存放的是一个Object数组      
	         List<Object[]> list = query.list();
	         int[] oldi = new int[hotcount];
	         for(int i=0;i<list.size()&&i<hotcount;i++){ 
	        	 Random ran=null;
	        	 int j=0;
	        	 boolean contn=true;
	        	 while(contn){
	        		 ran = new Random();
	        		 j= ran.nextInt(list.size());
		        	 for(int n=0;n<hotcount;n++){
		        		  if(oldi[n]==j){
		        			  break;
		        		  }
		        		  else if(oldi[n]!=j&&n==hotcount-1){
		        			  contn=false;
		        		  }
		        	 }
	        	 }
	        	
	        	 Object[] object =list.get(j);
	             hotlink = (String)object[0];      
	             title = (String)object[1];
	             System.out.println("---------"+title);
	             hothtml = "<li><a href='"+hotlink+"' style='color:black'>"+title+"</a></li>";
	             up_hotmodule += hothtml;
	         }
	        hotmodule = up_hotmodule + down_hotmodule;
	        Moduleinfo modulelink = new Moduleinfo();
            modulelink.setModuleid("moduleid-4");
            modulelink.setModulehtml(hotmodule);
            session.update(modulelink);
            up_hotmodule = "<li class='widget color-white' id='appid-6'><div class='widget-head'><h3>热点视频</h3></div><div class='widget-content'><h4>";
            mid_hotmodule = "</h4><embed class='app-flash' src='";
            down_hotmodule = "' quality='high' allowfullscreen='true' flashvars='playMovie=true&auto=1' pluginspage='http://get.adobe.com/cn/flashplayer/' style='visibility: visible;' allowscriptaccess='never' width='415' height='330' type='application/x-shockwave-flash'></embed></div></li>";
            hql = " select hotvideo, title from Hotvideo";      
	        Query quer = session.createQuery(hql);      
	        //默认查询出来的list里存放的是一个Object数组      
	        List<Object[]> videolist = quer.list(); 
	        title="";
	        for(Object[] object : videolist){      
	             hotvideo = (String)object[0];      
	             title = (String)object[1];
	             System.out.println("---------"+title);
	         }
	        up_hotmodule += title+mid_hotmodule+hotvideo;
	        hotmodule = up_hotmodule + down_hotmodule;
	        Moduleinfo modulevideo = new Moduleinfo();
	        modulevideo.setModuleid("moduleid-6");
	        modulevideo.setModulehtml(hotmodule);
            session.update(modulevideo);
            session.getTransaction().commit();
	   	 }catch(Exception e){
	   		 e.printStackTrace();
	   		 session.getTransaction().rollback();
	   	 }finally{
	   		 HibernateUtils.closeSession(session);
	   	 }
	}
	

}
