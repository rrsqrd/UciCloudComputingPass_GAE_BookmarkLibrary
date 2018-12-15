package com.rrsqrd.uci.bookmarkAppEngine.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.rrsqrd.uci.bookmarkAppEngine.BookmarkDSDao;


@WebServlet(name = "ShowBookmarkPosts", value="/show")
public class ShowBookmarkPosts extends HttpServlet 
{
	private static final long serialVersionUID = 1L;	
	
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		System.out.println("\n--------------------ShowBookmrkPosts: doGet:");

		
		BookmarkDSDao bmDao = new BookmarkDSDao();		
		List<Entity> bookmarkPostList =  bmDao.getAllBookmarkPosts();
							
		bookmarkPostList.forEach(System.out::println);
		
 		try
 		{
 			request.setAttribute("bookmarkPosts", bookmarkPostList);
			request.getRequestDispatcher("/showBookmarkPosts.jsp").forward(request, response);
		}
 		catch (ServletException e)
		{
			e.printStackTrace();
		}				
	}
}
