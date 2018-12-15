package com.rrsqrd.uci.bookmarkAppEngine.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import com.rrsqrd.uci.bookmarkAppEngine.BookmarkDSDao;
import com.rrsqrd.uci.bookmarkAppEngine.model.BookmarkPost;


@WebServlet(name = "UpdateBookmarkPost", value="/update")
public class UpdateBookmarkPost extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// make as cmn utility
	private boolean isStrEmptyOrNull(String inputStr)
	{
		System.out.println(inputStr);
		if((inputStr == null) || (inputStr.isEmpty()))
			return true;
		return false;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{

	}	

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) //throws IOException
	{	
		System.out.println("\n-------------------Begin UpdateBookmarkPost.doPost");

		/*
		Enumeration<String> en=request.getParameterNames();		
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}*/

		String btnAction = request.getParameter("btnAction");
		System.out.println("UpdateBookmarkPost btnAction=" + btnAction);
		
		// On cancel, show all bookmark posts
		try 
		{					
			if("Cancel".equals(btnAction)) 
			{
				System.out.println("UpdateBookmarkPost: Cancelling edit Post, redirecting to /show");
				response.sendRedirect("/show");
				return;
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred during UpdateBookmarkPost.doPost ");
			e.printStackTrace();
		}
				
		Key key = null;
		String modifyDate = "";
		
        String topic 	 		= request.getParameter("topic");	
		String urlName   		= request.getParameter("urlName");
		String urlStr    		= request.getParameter("urlStr");
		String category  		= request.getParameter("category");
		String entityKeyIdStr 	= request.getParameter("entityKeyId");	
		
		BookmarkDSDao bmDao = new BookmarkDSDao();
		
		try 
		{
			// ui validation checks for null and "" but just in case...			
			if((!isStrEmptyOrNull(topic))   && 
			   (!isStrEmptyOrNull(urlName)) && (!isStrEmptyOrNull(urlStr)) &&
			   (!isStrEmptyOrNull(category)) &&
			   (!isStrEmptyOrNull(entityKeyIdStr)) ) 
			{
				Long entityKeyId  = Long.valueOf(entityKeyIdStr);
				Entity bookmarkPostEntity = bmDao.getBookmarkPostByKeyId(entityKeyId);
				
				if(bookmarkPostEntity!= null)
				{
					System.out.println("bmDao.getBookmarkPostEntityByKeyId returned VALID ENTITY");
					
					BookmarkPost bmPst = bmDao.getBookmarkPostFromEntityKeyId(entityKeyId);			
					boolean okToUpdate = false;
					
					// If new topic text has changed, make an existing
					// Bookmark Post does not already have that name.
					if(!(bmPst.getTopic().equals(topic)))
					{
						if(bmDao.doesBookmarkPostExistByTopic(topic))
						{
				 			request.setAttribute("errorMessage", "A Bookmark Post with topic " + topic + "already exists");				 			
						}
						else{
							okToUpdate = true;
						}
					}
					else{
						okToUpdate = true;
					}
					
					if(okToUpdate)
					{
						// Get current date&time, consider removing time..
						modifyDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date().getTime());
						System.out.println("Modify/CreationDate " + modifyDate);
						
						BookmarkPost bookmarkPst = new BookmarkPost();
						bookmarkPst.setTopic(topic.trim());
						bookmarkPst.setUrlName(urlName.trim());
						bookmarkPst.setUrlStr(urlStr.trim());
						bookmarkPst.setCategory(category.trim());						
						bookmarkPst.setModifyDate(modifyDate);						
						
						key = bmDao.updateBookmarkPost(bookmarkPst, entityKeyId);
						
						if(key == null)
						{								
							System.out.println("Failure on bmDao.updateBookmarkPost()");
				 			request.setAttribute("errorMessage", "Failed to upate Bookmark with topic " + topic);							
						}
					}
				}
				else
				{
					System.out.println("UpdateBookmarkPost: bmDao.getBookmarkByKeyId returned INVALID ENTITY");
					
		 			request.setAttribute("errorMessage", "Unable to retrieve bookmark from storage");					
				}
			}
			else
			{
				System.out.println("UpdateBookmarkPost: One or more input/request params are invalid");
				
	 			request.setAttribute("errorMessage", "One or more input/request params are invalid");
			}
					
			if (key != null)
			{
	 			Long   keyId      = key.getId();	 			
	 			Entity bookmarkPostEntity = bmDao.getBookmarkPostByKeyId(keyId);
	 			
	 			//System.out.println(bookmarkPostEntity);
	 			
	 			request.setAttribute("message", "Successfully updated Bookmark Post");
	 	 		request.setAttribute("bookmarkPostEntity", bookmarkPostEntity);
			}
			
			// With forward, the original request and response objects are transferred 
			// along with additional parameters if needed.
			// URL remains intact. Server forwards the request to another resource within same server.
		    request.getRequestDispatcher("/confirm.jsp").forward(request, response);
		}
		catch (IOException | ServletException e)
		{
			e.printStackTrace();
		}						    	    
	}
	
	
}