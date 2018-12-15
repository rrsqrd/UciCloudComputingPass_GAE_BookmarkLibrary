<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Confirm Post Creation</title>
	<style><%@include file="../bookmarkPost.css"%></style>
	
	<style>
		#successMsg {
		    display: inline-block;
		    width: 243px;
		    height: 18px;
		    padding: 5px;
		    background-color: #ccffcc; 
		}	
	</style>
	
	<script>
		function hideBookmarkPostTableOnError() 
		{
			// visibility:hidden
		    var x = document.getElementById("bmrk");
		    var y = document.getElementById("successMsg");
		    x.style.display = "none";
		    y.style.display = "none";
		}
	</script>

</head>

<body style="padding-left: 15px; font-size: 15px; top:100px;">
	<br><br>
	
	<div class="container">			      
		<div>
			<span id="successMsg"><strong>${message}</strong></span>
	    </div>	
	</div>
	<br><br>
	 
	<table id="bmrk" class="bmrkTable" style="width:75%" >
     	<tr id="tableHdrRow">
			<td><strong>Topic</strong></td>
			<td><strong>URL Name</strong></td>
			<td><strong>URL</strong></td>
			<td><strong>Category</strong></td>			
			<td><strong>Modify Date</strong></td>						
     	</tr>     

<%
    //@SuppressWarnings("unchecked")
	Entity entity = (Entity)request.getAttribute("bookmarkPostEntity");
	
    if (entity != null)
	{
			
%>
		<tr>
			<td style="width:120px;"><%= ( (String)	entity.getProperty("topic")) %>  </td>		
			
			<td style="width:100px;">
				<a  aria-hidden="true" 
					title="<%= ( (String) entity.getProperty("urlName")) %>"
					target="_blank" href="<%= ( (String) entity.getProperty("urlStr")) %>">
			     	<span style="font-size: 10pt;">
			     		<em><%= ( (String) entity.getProperty("urlName")) %></em>
			     	</span>
			     </a> 
			</td>
			
			<td style="width:100px;">
				<a  aria-hidden="true" 
					title="<%= ( (String) entity.getProperty("urlStr")) %>"
					target="_blank" href="<%= ( (String) entity.getProperty("urlStr")) %>">
			     	<span style="font-size: 8pt;">
			     		<em><%= ( (String) entity.getProperty("urlStr")) %></em>
			     	</span>
			     </a> 
			</td>
			
			<td style="width:200px;"><%= ( (String)	entity.getProperty("category")) %>  
			</td>
			<td style="width:120px;"><%= ( (String)	entity.getProperty("modifyDate")) %>
			</td>
	    </tr>
<%
		
	}
    else
    { 
    	
%>
		<tr><td>${errorMessage}</td></tr>
<%
    }
%>	
     </table>
    
<% 
	if(entity == null) 
	{				
%>
	  <div style="font-size:15px; color:red;">${errorMessage}</div>
	  <script>hideBookmarkPostTableOnError();</script>	  
<% 
	} 
%>   
     	
	<br><br><br>
	<div>
	   <table style="padding-left: 15px; font-size: 15px;">
	      	<tr>
		      	<td style="width:100px;"></td>
		        <td><strong><a style="color:blue" href="/show">Show All Bookmark Posts</a></strong></td>
		        <td style="width:50px;"></td>
		        <td><strong><a style="color:blue" href="/createBookmarkPost.jsp">Create New Bookmark Post</a></strong></td>
		        <!-- 
		        <td style="width:50px;"></td>
		        <td><strong><a style="color:blue" href="main.html">Main Page</a></strong></td>
		        -->
	      	</tr>
	   </table>	
	</div>	

	<br><br>
	<%@include file="footer.jsp" %>
</body>
</html>