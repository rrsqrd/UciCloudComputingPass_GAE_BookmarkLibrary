package com.rrsqrd.uci.bookmarkAppEngine.actions;

import com.rrsqrd.uci.bookmarkAppEngine.BookmarkDSDao;
import com.rrsqrd.uci.bookmarkAppEngine.model.BookmarkPost;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;



@WebServlet(name = "CreateBookmarkPost", value="/create")
public class CreateBookmarkPost extends HttpServlet 
{
	private static final long serialVersionUID = 1L;	

	private boolean isStrEmptyOrNull(String inputStr)
	{
		if((inputStr == null) || (inputStr.isEmpty()))
			return true;
		return false;
	}
	
    //---------------------
	//
    //---------------------	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		boolean addSuccess = false;
		
		System.out.println("\n--------------------CreateBookmarkPost: doPost:");
		
		String btnAction = request.getParameter("btnAction");
		System.out.println("CreateBookmarkPost: btnAction=" + btnAction);
		
		// On cancel, show all Bookmark Posts
		try {					
			if("Cancel".equals(btnAction)) 
			{
				//System.out.println("CreateBookmarkPost: Cancelling create Bookmark, redirecting to /show");
				// redirect to show  all Bookmark posts 
				response.sendRedirect("/show");
				return;
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred during redirect to Show All Bookmark Posts");
			e.printStackTrace();
		}
		
		System.out.println("CreateBookmarkPost Proceeding with create");
		
		
		Enumeration<String> en=request.getParameterNames();		
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}

		Key key = null;
		String modifyDate = "";		

        String topic 		= request.getParameter("topic");
		String urlName 		= request.getParameter("urlName");
		String urlStr 		= request.getParameter("urlStr");
		String category 	= request.getParameter("category");		
		
		if((!isStrEmptyOrNull(topic))   && 				
		   (!isStrEmptyOrNull(urlName)) && 
		   (!isStrEmptyOrNull(urlStr))  &&
		   (!isStrEmptyOrNull(category)) ) 
		{
			BookmarkDSDao bmDao = new BookmarkDSDao();
					
			boolean exists = bmDao.doesBookmarkPostExistByTopic(topic);			
			if(exists == false)
			{
				modifyDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date().getTime());
				//System.out.println("CreateBookmarkPost: modifyDate " + modifyDate);
				
				BookmarkPost bookmarkPst = new BookmarkPost();
				bookmarkPst.setTopic(topic.trim());
				bookmarkPst.setUrlName(urlName.trim());
				bookmarkPst.setUrlStr(urlStr.trim());
				bookmarkPst.setCategory(category.trim());				
				bookmarkPst.setModifyDate(modifyDate);
				
				key = bmDao.addBookmarkPost(bookmarkPst);
				addSuccess = true;
			}
			else
			{
				Entity entity = bmDao.getBookmarkPostByTopic(topic);				
				System.out.println("CreateBookmarkPost: entity with topic already exists, entityId= " + entity.getKey().getId());
				
	 			request.setAttribute("errorMessage", "A Bookmark with topic of '" + topic + "' already exists");
	 			
	 			// TODO: 
	 			// Currently, create failure results in new page with error message.
	 			// As is, if I was to use create page, it would present empty fields 
	 			// with error message and some page layout differences.
	 			// Better to either
	 			// 1) Present a page with pre populated form with user input values 			
	 			//    i.e similar to BookmarkPostLibary on login... 
	 			//
	 			// 2) From UI, make ajax call to serverlet to check the 
	 			//    datastore for duplicate topic, i.e no submit.
	 			// 
			}			
		}
		else
		{
			request.setAttribute("errorMessage", "Invalid input values");
		}
				
		if ((key != null) && (addSuccess))
		{	 			
 			BookmarkDSDao bmDao = new BookmarkDSDao();
 			Long keyId        =   key.getId();
 			
 			Entity  bookmarkPostEntity =  bmDao.getBookmarkPostByKeyId(keyId);
 			
 			//System.out.println(bookmarkPostEntity); 			
 			//System.out.println("Successfully created new Bookmark Post");
 			
 			request.setAttribute("message", "Successfully created Bookmark Post");
 	 		request.setAttribute("bookmarkPostEntity", bookmarkPostEntity);
		}
		
	    request.getRequestDispatcher("/confirm.jsp").forward(request, response);	    		
	}	
}
