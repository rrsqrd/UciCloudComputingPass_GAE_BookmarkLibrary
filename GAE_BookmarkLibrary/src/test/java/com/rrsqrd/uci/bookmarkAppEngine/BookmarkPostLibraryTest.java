package com.rrsqrd.uci.bookmarkAppEngine;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.rrsqrd.uci.bookmarkAppEngine.BookmarkPostLibrary;


public class BookmarkPostLibraryTest 
{
  @Test
  public void test() throws IOException 
  {
    MockHttpServletResponse response = new MockHttpServletResponse();
    new BookmarkPostLibrary().doGet(null, response);
    
    Assert.assertEquals("text/plain", response.getContentType());
    Assert.assertEquals("UTF-8", response.getCharacterEncoding());
    Assert.assertEquals("Bookmark Post Library App Engine!\r\n", response.getWriterContent().toString());
  }
}
