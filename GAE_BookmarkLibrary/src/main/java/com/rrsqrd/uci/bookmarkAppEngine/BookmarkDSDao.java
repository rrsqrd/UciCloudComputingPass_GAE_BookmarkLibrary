package com.rrsqrd.uci.bookmarkAppEngine;

import java.util.List;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.rrsqrd.uci.bookmarkAppEngine.model.BookmarkPost;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class BookmarkDSDao 
{
	private static final String BOOKMARK_POST_KIND = "BookmarkPost";
	
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	public Key addBookmarkPost(BookmarkPost bookmarkPst)
	{
		Key key = null;

		// Caller checks fields for nulls and empty
		if (bookmarkPst != null)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			Entity entity = null;
			entity = new Entity(BOOKMARK_POST_KIND);

			entity.setProperty("topic", 		bookmarkPst.getTopic());		
			entity.setProperty("urlName", 		bookmarkPst.getUrlName());			
			entity.setProperty("urlStr",  		bookmarkPst.getUrlStr());
			entity.setProperty("category", 		bookmarkPst.getCategory());
			entity.setProperty("modifyDate", 	bookmarkPst.getModifyDate());

			key = datastore.put(entity);
		}
		return key;
	}
	
	/**
	 * updateBookmarkPost: 
	 * 	specified Entity already exists in the data store,
	 *  uses existing entityKeyId to identify which entity to update
	 * @param topic
	 * @param author
	 * @param category
	 * @param creationDate
	 * @return
	 */
	public Key updateBookmarkPost(BookmarkPost bookmarkPst, Long entityKeyId)
	{
		Key key = null;

		if (bookmarkPst != null && entityKeyId != null )
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			Entity entity = null;
			
			// Create a new entity with the same key as the original
			entity = new Entity(BOOKMARK_POST_KIND, entityKeyId);
			
			entity.setProperty("topic", 		bookmarkPst.getTopic());		
			entity.setProperty("urlName", 		bookmarkPst.getUrlName());			
			entity.setProperty("urlStr",  		bookmarkPst.getUrlStr());
			entity.setProperty("category", 		bookmarkPst.getCategory());
			entity.setProperty("modifyDate", 	bookmarkPst.getModifyDate());

			key = datastore.put(entity);
		}
		return key;
	}	
	/**
	 * rerturns list of all Entities
	 * @return
	 */
	public List<Entity> getAllBookmarkPosts()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// To read entities from Cloud Datastore, you must use a query. 
		// This query sorts bookmark posts in asending order by topic up to 50 entities		
		Query findQuery = new Query(BOOKMARK_POST_KIND);
		findQuery.addSort("topic", SortDirection.ASCENDING);

		PreparedQuery pq = datastore.prepare(findQuery);
		FetchOptions option = FetchOptions.Builder.withLimit(50);

		List<Entity> results = pq.asList(option);

		return results;
	}	
	
	/**
	 * returns boolean 
	 * @param topic
	 * @return
	 */
	public boolean doesBookmarkPostExistByTopic(String topic)
	{
		List<Entity> results = null;
		boolean doesExist = false;

		if (topic != null && !topic.isEmpty())
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 			
			Query findQuery = new Query(BOOKMARK_POST_KIND);
			String topicTrm= topic.trim();
			
			// Not sure why but converting string toLowerCase resulted in failed EQUAL check
			Filter topicFilter = new FilterPredicate("topic", FilterOperator.EQUAL, topicTrm);			
			
			findQuery.setFilter(topicFilter);
			findQuery.addSort("topic", SortDirection.ASCENDING);
			
			results = datastore.prepare(findQuery).asList(FetchOptions.Builder.withLimit(10));

			if(results.size() > 0)
			{
				doesExist = true;
				System.out.println("doesBookmarkPostExistByTopic:  BookmarkPost with topic: '" + 
						topicTrm + "' already exists");
			}
			else
			{
				System.out.println("doesBookmarkPostExistByTopic:  BookmarkPost with topic: '" + 
						topicTrm + "' does NOT exist");
			}		
		}

		return doesExist;
	}
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	public Entity getBookmarkPostByTopic(String topic)
	{
		List<Entity> results = null;
        Entity entity = null;	

		if (topic != null && !topic.isEmpty())
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 			
			Query findQuery = new Query(BOOKMARK_POST_KIND);
			String topicTrm= topic.trim();
			
			// Not sure why but converting string toLowerCase resulted in failed EQUAL check
			Filter topicFilter = new FilterPredicate("topic", FilterOperator.EQUAL, topicTrm);			
			
			findQuery.setFilter(topicFilter);
			findQuery.addSort("topic", SortDirection.ASCENDING);
			
			results = datastore.prepare(findQuery).asList(FetchOptions.Builder.withLimit(10));

			if(results.size() == 1)
			{
				entity = results.get(0);
				System.out.println("getBookmarkPostByTopic: keyId=" + entity.getKey());
			}
			else
			{
				System.out.println("getBookmarkPostByTopic:  BookmarkPost with topic: '" + topicTrm + "' does NOT exist");
			}		
		}

		return entity;
	}	
		
	/**
	 * 
	 * @param deleteByKeyId
	 * @return
	 */
	public boolean deleteBookmarkPostByKeyId(Long keyIdToDelete)
	{
		boolean retVal = false;

		//System.out.println("deleteByKeyId attempting to delete entity with key Id= " + keyIdToDelete);
		
		if (keyIdToDelete != null && keyIdToDelete > 0)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			try 
			{
				// KeyIdToDelete is obtained from entity.getKey().getId() in jsp page
				Entity entity = datastore.get(KeyFactory.createKey(BOOKMARK_POST_KIND, keyIdToDelete));
				
				if(entity != null)
				{ 
					System.out.println("deleteByKeyId deleting entity with key Id= " + keyIdToDelete);					
					datastore.delete(entity.getKey());
					retVal = true;
				}
				else
				{
					System.out.println("deleteByKeyId Unable to delete entity with key Id= " + 
				                            keyIdToDelete + " not found");
					 
				}
			}
			catch(Exception e)
			{
				// EntityNotFoundException
				System.out.println(e.getMessage());				
				
			}
		}

		return retVal;
	}
	
	
	/**
	 *    utilty function:
	 *    good to have in early development when your code is not quite right..
	 * @param deleteByKeyId
	 * @return
	 */
	public boolean deleteByKeyId(Long keyIdToDelete)
	{
		boolean retVal = false;

		//System.out.println("deleteByKeyId attempting to delete entity with key Id= " + keyIdToDelete);
		
		if (keyIdToDelete != null && keyIdToDelete > 0)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			try 
			{						
				Entity entity = datastore.get(KeyFactory.createKey(BOOKMARK_POST_KIND, keyIdToDelete));
				
				if(entity != null)
				{
					System.out.println("deleteByKeyId deleting entity with key Id= " + keyIdToDelete);
					datastore.delete(entity.getKey());
					retVal = true;
				}
				else
				{
					System.out.println("deleteByKeyId Unable to delete entity with key Id= " + 
				                            keyIdToDelete + " not found");
					 
				}
			}
			catch(Exception e)
			{
				// EntityNotFoundException
				System.out.println(e.getMessage());				
			}
		}

		return retVal;
	}
	
	/**
	 * getBookmarkPostByKeyId
	 *     
	 * @param keyId
	 * @return
	 */
	public Entity getBookmarkPostByKeyId(Long keyId)
	{
        Entity entity = null;

		//System.out.println("BookmarkPostDSDao: getBookmarkPostByKeyId Id= " + keyId);
		
		if (keyId != null && keyId > 0)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			try 
			{
				entity = datastore.get(KeyFactory.createKey(BOOKMARK_POST_KIND, keyId));
				
				if(entity != null)
				{
					System.out.println("BookmarkPostDSDao: getBookmarkPostByKeyId found bookmark with with keyId= " + keyId);
				}
				else
				{
					System.out.println("BookmarkPostDSDao: getBookmarkPostByKeyId Unable to find bookmark with keyId= " + keyId);
					 
				}
			}
			catch(Exception e)
			{
				// EntityNotFoundException
				System.out.println("getBookmarkPostByKey: " + e.getMessage());		
				
			}
		}

		return entity;
	}
	
	/**
	 * 
	 */	
	public BookmarkPost getBookmarkPostFromEntityKeyId(Long keyId)
	{
		BookmarkPost bookmarkPst = null;
		//System.out.println("getBookmarkPostPostFromEntityKeyId Id= " + keyId);
		
		if (keyId != null && keyId > 0)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			try 
			{						
				Entity entity = datastore.get(KeyFactory.createKey(BOOKMARK_POST_KIND, keyId));
				
				if(entity != null)
				{
					bookmarkPst = new BookmarkPost();
					bookmarkPst.setTopic(((String) entity.getProperty(BookmarkPost.TOPIC)));
					bookmarkPst.setUrlName(((String) entity.getProperty(BookmarkPost.URL_NAME)));
					bookmarkPst.setUrlStr(((String) entity.getProperty(BookmarkPost.URL_STR)));		 
					
					bookmarkPst.setCategory(((String)   entity.getProperty(BookmarkPost.CATEGORY)));
					bookmarkPst.setModifyDate(((String) entity.getProperty(BookmarkPost.MODIFY_DATE)));

					bookmarkPst.setEntityKeyID(((Long) entity.getProperty(BookmarkPost.ENTITY_KEY_ID)));
				}
			}
			catch(Exception e)
			{
				// EntityNotFoundException
				System.out.println(e.getMessage());				
			}			
		}	
		
		return bookmarkPst;
		
	}

}
