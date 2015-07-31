// Import required java libraries
import java.io.*;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.*;
 
public class servlet_test extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 50 * 1024;
   private int maxMemSize = 4 * 1024;
   private File file ;
 
   public void init( ){
      // Get the file location where it would be stored.
      filePath = 
             "F:/tomcat/apache-tomcat-6.0.37-src/output/build/webapps/ROOT/data"; 
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
      // Check that we have a file upload request
      //isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
    //  if( !isMultipart ){
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet_test</title>"); 
         out.println("</head>");
         out.println("<body>");
         out.println("<p>Hello, this is thee servlet response from server side</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      
      
   }
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
	   response.setContentType("text/html");
	      java.io.PrintWriter out = response.getWriter( );
	    //  if( !isMultipart ){
	         out.println("<html>");
	         out.println("<head>");
	         out.println("<title>Servlet_test</title>"); 
	         out.println("</head>");
	         out.println("<body>");
	         out.println("<p>Hello, this is thee servlet response from server side</p>"); 
	         out.println("</body>");
	         out.println("</html>");
	         return;
   } 
}
