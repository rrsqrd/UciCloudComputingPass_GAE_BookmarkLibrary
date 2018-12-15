package com.rrsqrd.uci.bookmarkAppEngine;

import java.io.IOException;
//import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "BookmarkPostLibrary", urlPatterns = {"/login"})
public class BookmarkPostLibrary extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException 
	{
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		// here we write directly to page...
		//response.getWriter().print("Login Bookmark Post Library!\r\n");
		
		System.out.println("\n--------------------BookmarkPostLibrary: doPost:");		
		
		/**
		 * Session support must be enabled in appengine-web.xml, disabled by default  
		 * via <sessions-enabled>true</sessions-enabled> in that file.  
		 * Without it, getSession() is allowed, but manipulation of session attributes is not.
		 */
		try 
		{
			String uName    = request.getParameter("userName");
			String password = request.getParameter("passWord");
			
			/*
			Enumeration<String> en=request.getParameterNames();
			while(en.hasMoreElements())
			{
				Object objOri=en.nextElement();
				String param=(String)objOri;
				String value=request.getParameter(param);
				System.out.println("LoginAppEngine: Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
			}*/			
			
			boolean validationOk = true;
			if((uName.isEmpty()) || (password.isEmpty()))
			{				
				request.setAttribute("errorMessage", "Please enter a user name and a password");
				validationOk = false;
			}
						
	        String patternAlphaOnly    = "^[a-zA-Z]+$";			
			String patternAlphaNumeric = "^[a-zA-Z0-9]+$";
	        
	        if(validationOk)
	        {
	          if(!Pattern.matches(patternAlphaOnly, uName))
		      {
	        	  request.setAttribute("errorMessage", "User name must be characters a-zA-Z");
	        	  validationOk = false;
		      }
	          else if (!Pattern.matches(patternAlphaNumeric, password)) 
	 	      {
	        	  request.setAttribute("errorMessage", "Password must be alpha numeric a-zA-Z0-9 characters");
	        	  validationOk = false;
	 	      }          
	        }

	        if(!validationOk)
	        {
	        	// Maybe I Could/Should have written index.jsp conditionally to check for request
	        	// parameters to populate form with input values from user????
				//request.setAttribute("userName", uName);
				//request.setAttribute("passWord", password);

				request.getRequestDispatcher("/").forward(request, response);
				return;
	        }

			// get session object or create it if doesn't exist

			//TODO, Add User class and use it with session...
			HttpSession session=request.getSession();   //getSettion(true);
			session.setAttribute("userName", uName);
			session.setAttribute("passWord", password);												

			// app too small, no point in landing on a page with just links
			//request.getRequestDispatcher("/main.html").forward(request, response);

			// Once logged in, provide list of existing bookmark posts
			response.sendRedirect("/show");
	    }
		catch(Exception exp)
		{
	       System.out.println(exp);
	    } 
	}
	
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException 
	{
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().print("Bookmark Post Library App Engine!\r\n");		
	}	
}