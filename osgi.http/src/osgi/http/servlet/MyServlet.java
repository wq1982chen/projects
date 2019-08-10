package osgi.http.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  µœ÷≤‚ ‘ . 
     * @param request the req. 
     * @param response the res. 
     * @throws IOException io exception. 
     */ 
    public void doGet( 
            HttpServletRequest request, 
            HttpServletResponse response 
            ) throws IOException { 
    	
        PrintWriter writer = response.getWriter();
		writer.write("hello osgi http servlet.time now is "+new Date()); 
    } 
	
}
