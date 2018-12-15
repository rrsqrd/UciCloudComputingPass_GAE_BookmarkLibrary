package com.rrsqrd.uci.bookmarkAppEngine.actions;


import java.io.IOException;
//import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.rrsqrd.uci.bookmarkAppEngine.BookmarkDSDao;


@SuppressWarnings("serial")

@WebServlet(name = "RedirectBookmarkPosts", value="/manage")
public class RedirectBookmarkPosts extends HttpServlet
{

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{	
		System.out.println("\n-------------------\nBegin RedirectBookmarkPosts.doPost");
	
		/*
		Enumeration<String> en=request.getParameterNames();
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("RedirectBookmarkPosts: Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}*/				

		String btnAction = request.getParameter("btnAction");		
		//System.out.println("RedirectBookmarkPosts: btnAction=" + btnAction);
		
		if("Delete".equals(btnAction)) {
			this.deleteBookmarkPost(request, response);
		}
		else if("Edit".equals(btnAction)) {
			this.editBookmarkPost(request, response);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void deleteBookmarkPost(HttpServletRequest request, HttpServletResponse response) // throws IOException
	{
		System.out.println("\n-------------------Begin RedirectBookmarkPosts.deleteBookmarkPost");
				
		/*Enumeration<String> en=request.getParameterNames();
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("RedirectBookmarkPosts: Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}*/				
		
		try
 		{		
			String topic 		= request.getParameter("entityTopic").toString();			
			System.out.println("RedirectBookmarkPosts: Bookmark post topic to delete: '" + topic + "'");
						
			String entityKeyIdStr 	= request.getParameter("entityKeyId").toString();			
			System.out.println("EntityKeyId: " + entityKeyIdStr);
						
			//-------------------------
			//  Retrieve Bookmark Post entity identified by entityKeyId & forward to update servlet
			//-------------------------		 	
			BookmarkDSDao bmDao = new BookmarkDSDao();
			
			Long entityKeyId  = Long.valueOf(entityKeyIdStr);
			Entity bookmarkPostEntity = bmDao.getBookmarkPostByKeyId(entityKeyId);

			request.setAttribute("bookmarkPostEntity", bookmarkPostEntity);
			request.getRequestDispatcher("/deleteBookmarkPost.jsp").forward(request, response);
			
 		}
		catch (IOException | ServletException e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void editBookmarkPost(HttpServletRequest request, HttpServletResponse response) // throws IOException
	{
		System.out.println("\n-------------------Begin RedirectBookmarkPosts.editBookmarkPost");

		/*
		Enumeration<String> en=request.getParameterNames();
		while(en.hasMoreElements())
		{
			Object objOri=en.nextElement();
			String param=(String)objOri;
			String value=request.getParameter(param);
			System.out.println("RedirectBookmarkPosts: Parameter Name is '"+param+"' and Parameter Value is '"+value+"'");
		}*/	
		
		try
 		{		
			String topic 		= request.getParameter("entityTopic").toString();			 
			System.out.println("RedirectBookmarkPosts: Bookmark Topic to edit: '" + topic + "'");
						 			
			String entityKeyIdStr 	= request.getParameter("entityKeyId").toString();			
			System.out.println("RedirectBookmarkPosts: EntityKeyId: " + entityKeyIdStr);
						
			//-------------------------
			//  Retrieve Bookmark Post entity identified by entityKeyId & forward to update servlet
			//-------------------------		 	
			BookmarkDSDao bmDao = new BookmarkDSDao();
			
			Long entityKeyId          = Long.valueOf(entityKeyIdStr);
			Entity bookmarkPostEntity = bmDao.getBookmarkPostByKeyId(entityKeyId);

			//System.out.println("RedirectBookmarkPosts: forwarding request & bookmarkEntity to updateBookmarkPost.jsp ");
			
			request.setAttribute("bookmarkPostEntity", bookmarkPostEntity);
			request.getRequestDispatcher("/updateBookmarkPost.jsp").forward(request, response);
			
 		}
		catch (IOException | ServletException e)
		{
			e.printStackTrace();
		}						
	}
	

}
