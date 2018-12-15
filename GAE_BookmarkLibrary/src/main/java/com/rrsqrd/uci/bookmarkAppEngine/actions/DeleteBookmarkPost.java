package com.rrsqrd.uci.bookmarkAppEngine.actions;

import java.io.IOException;

//import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.rrsqrd.uci.bookmarkAppEngine.BookmarkDSDao;


@SuppressWarnings("serial")

@WebServlet(name = "DeleteBookmarkPost", value="/delete")
public class DeleteBookmarkPost extends HttpServlet
{

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{	
		System.out.println("\n-------------------\nBegin DeleteBookmarkPost.doPost");
		
		/*
		Enumeration<String> en=request.getParameterNames();
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("DeleteBookmarkPost: Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}*/				

		String btnAction = request.getParameter("btnAction");
		System.out.println("DeleteBookmarkPost btnAction=" + btnAction);
		
		// On cancel, show all bookmark posts
		try 
		{					
			if("Cancel".equals(btnAction)) {
				//System.out.println("Cancelling Delete Bookmark Post");
				// redirect to show  all Bookmark posts				
				response.sendRedirect("/show");
				return;
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred during DeleteBookmarkPost.doPost ");
			e.printStackTrace();
		}
		
		try
 		{				 
			String entityKeyId 	= request.getParameter("entityKeyId").toString();			
			//if((entityKeyId != null) && (entityKeyId!=""))
			//	 System.out.println("EndityKeyId: " + entityKeyId);
						
			//-------------------------
			//  Delete BookmarkPos post identified by entityKeyId
			//-------------------------		 	
			BookmarkDSDao bmDao = new BookmarkDSDao();
			
			List<Entity> bmListB4 =  bmDao.getAllBookmarkPosts();
			System.out.println("DeleteBookmarkPost.doPost: bookmark post count BEFORE DELETE=" + bmListB4.size());
			
			// -- By KeyId
		 	Long entityKeyIdToDelete = Long.valueOf(entityKeyId);
			System.out.println("DeleteBookmarkPost: bookmarkDao.deleteByKeyId: " +  entityKeyIdToDelete);
			boolean delSuccess = bmDao.deleteByKeyId(entityKeyIdToDelete);
			
			if(delSuccess)
				request.setAttribute("Message", "Success"); 
			else
				request.setAttribute("Message", "Failed"); 

			//--------------
			// Retrieve & return a fresh list of BookmarkPosts.
			//--------------			
			List<Entity> bookmarkList =  bmDao.getAllBookmarkPosts();
			
			//bookmarkList.forEach(System.out::println);			
			
			System.out.println("DeleteBookmarkPosts.doPost: bookmark post count AFTER DELETE=" + bookmarkList.size());
			
			request.setAttribute("bookmarkPosts", bookmarkList);	 			 	
			request.getRequestDispatcher("/showBookmarkPosts.jsp").forward(request, response);			
 		}
		catch (IOException | ServletException e)
		{
			e.printStackTrace();
		}
		
	}


	
	
}
