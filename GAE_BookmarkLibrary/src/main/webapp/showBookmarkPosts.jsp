
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>


<html>
<head>
 	<meta http-equiv="content-type" content="text/html; charset=UTF-8">    
	<title>Show Posts</title> 
  	<style><%@include file="../bookmarkPost.css"%></style>
  	<script>
  		function hideBookmarkPostTable() 
		{
			// visibility:hidden
	    	var x = document.getElementById("bmrk");
	    	x.style.display = "none";
		}  		
  	</script>
</head>

<body style="margin-top:30px; padding-left: 50px; ">	
	<div class="container">
	 <%--  <jsp:include page="/header.jsp" /> --%> 
	
    <table style="15px; font-size: 15px;">
      <tr>
      	<td style="width:100px;"></td>
        <td><strong><a style="color:blue" href="/show">Refresh Bookmark Posts</a></strong></td>
        <td style="width:50px;"></td>
        <td><strong><a style="color:blue" href="createBookmarkPost.jsp">Create new Bookmark Post</a></strong></td>
        <td style="width:50px;"></td>
        <td><strong><a style="color:blue" href="logout.jsp">Logout</a></strong></td>        
      </tr>
    </table>	
	</div>
	
	<br>
   	<h1 style="color:blue">Bookmark Posts</h1>
   	
    <table id="bmrk" class="bmrkTable" style="width:90%" >
     	<tr id="tableHdrRow">
			<td align="center"><strong>Topic</strong></td>
			<!-- <td align="center"><strong>Author</strong></td> -->
			<td align="center"><strong>URL Name</strong></td>
			<td align="center"><strong>URL</strong></td>
			<td align="center"><strong>Category</strong></td>
			<td align="center"><strong>Modify Date</strong></td>			
			<td align="center"><strong>Action</strong></td>            
     	</tr>
<%
    @SuppressWarnings("unchecked")
	List<Entity> bookMarkList = (List<Entity>)request.getAttribute("bookmarkPosts");
	
    if (bookMarkList != null && !bookMarkList.isEmpty())
	{    
    	
		for (int i = 0; i < bookMarkList.size(); i++)
		{
			Entity entity = bookMarkList.get(i);	

			System.out.println("topic   =" + entity.getProperty("topic").toString());
			System.out.println("urlName =" + entity.getProperty("urlName"));
			System.out.println("urlStr  =" + entity.getProperty("urlStr"));
			Long entityKeyIdParm   =  ((Long)  entity.getKey().getId() ) ;			
			
%>
		<tr>
			<td style="width:200px;"><strong>
				<%= ( (String)	entity.getProperty("topic")) %></strong>  </td>			
			
			<td style="width:200px;">
				<a  aria-hidden="true" 
					title="<%= ( (String) entity.getProperty("urlName")) %>"
					target="_blank" href="<%= ( (String) entity.getProperty("urlStr")) %>">
			     	<span style="font-size: 10pt;">
			     		<em><%= ( (String) entity.getProperty("urlName")) %></em>
			     	</span>
			     </a> 
			</td>
			
			<td style="width:75px;">
				<a  aria-hidden="true" 
					title="<%= ( (String) entity.getProperty("urlStr")) %>"
					target="_blank" href="<%= ( (String) entity.getProperty("urlStr")) %>">
			     	<span style="font-size: 8pt;">
			     		<em><%= ( (String) entity.getProperty("urlStr")) %></em>
			     	</span>
			     </a> 
			</td>			

			<td style="width:100px;">
				<%= ( (String)	entity.getProperty("category")) %> 
			</td>			
			<td style="width:100px;">
				<%= ( (String)	entity.getProperty("modifyDate")) %> 
			</td>		
			
			
			<td style=" width:5px;">
				<form id="editAndDeleteForm" method="POST" action="redirect">							
	    		<table>
	      			<tr style="color: blue;">	      				      				
	      				<td style ="border: 0px;">
	      					<input style="background-color: #7FFF00;;" id="editID" 
	      						type="submit" name="btnAction" value="Edit"> </td>	      				 
	      					
	      				<td style ="border: 0px;">
						<td style ="border: 0px; width:10px;">
							<input style="background-color: #ff6347;" id="deleteID" 
								type="submit" name="btnAction" value="Delete"> </td>
	      			</tr>			
	      		</table>
	      		<div>
	      			<div style ="border: 0px"> 
	      					<input  id="entityTopic" type="hidden" name="entityTopic" 
	      					value='<%=entity.getProperty("topic")%>'>
	      			</div>
	      				     
   					<div style ="border: 0px"> 
   							<input id="entityKeyId" type="hidden"  name="entityKeyId"
	      				     value='<%= ( (Long)  entity.getKey().getId() ) %>'> 
	      			</div>	      		
	      		</div>
	      		</form>
	      	</td>
	     </tr>
<%
		}    	
	}
    else
    {
    	//bookMarkList is empty but not null
    	bookMarkList = null;
    }
%>	
    </table>    
    
    
<% 
	if(bookMarkList == null) 
	{				
%>
	  <div style="font-size:15px; color:red;">There are no Posts to be displayed</div>
	  <script>hideBookmarkPostTable();</script>	  
<% 
	} 
%>  

 
	<br><br>
	<%@include file="footer.jsp" %>
  </body>
</html>
