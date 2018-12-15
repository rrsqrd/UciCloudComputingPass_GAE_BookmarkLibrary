<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@ page import="com.google.appengine.api.datastore.Entity" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>	


<head>	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Delete Post</title>
	<style><%@include file="../bookmarkPost.css"%></style>
		
	<style>
		label {
		  width:100px;
		  display: inline-block;
		}
	</style>
</head>


<body style="margin-top:30px; padding-left: 50px; font-size: 15px">			
	<div class="container">
	<div>
	    <table style="15px; font-size: 15px;">
	      <tr>
	      	<td style="width:100px;"></td>
	      	<!-- 
	        <td><strong><a style="color:blue" href="main.html">Main Page</a></strong></td>
	        <td style="width:50px;"></td>
	        -->
	        <td><strong><a style="color:blue" href="/show">Show All Bookmark Posts</a></strong></td>
        	<td style="width:50px;"></td>
        	<td><strong><a style="color:blue" href="logout.jsp">Logout</a></strong></td>	        
	      </tr>
	    </table>	    	    
	</div>
	
	<h3 style="color:blue; margin-top:50px;">Are you sure you want to delete this Bookmark Post?</h3>
	
	<form method="POST" name="deleteForm" action="/delete"  accept-charset="utf-8">	 

		<%			
		   	//@SuppressWarnings("unchecked")
			Entity entity = (Entity)request.getAttribute("bookmarkPostEntity");			
		    if (entity != null)
			{	
		    	/*
				System.out.println("topic   =" + entity.getProperty("topic").toString());
				System.out.println("author  =" + entity.getProperty("author").toString());
				*/				
		%>
	
		<table>
		    <tr>
		      <td><label style="width: 90px;" for="topic">Topic</label></td>
		      <td><input type="text" name="topic" 
		      			id="topic" size="60" 
		    	  		value='<%= ( (String) entity.getProperty("topic")) %>' disabled	    	  		 	    	  	     
		      			class="form-control" /><td>
		    </tr>			            
                  
            <tr>            
                <td><label style="width: 90px;" for="Name">URL Name: </label></td>
                <td> <input type="text" name="urlName" 
                        id="urlName" size="60"  
                        value='<%= ( (String) entity.getProperty("urlName")) %>' disabled 
                        class="form-control" />
                </td>
            	<td></td>
            </tr>
            <tr>
              	<td><label style="width: 90px;" for="Str">URL:</label></td>
              	<td>
				 <input type="text" name="urlStr" 
				 		id="urlStr" size="60" 
				 		value='<%= ( (String) entity.getProperty("urlStr")) %>' disabled 
		      			class="form-control" />                      	
               	</td>
              	<td><label id="urlStrErrMsg" style="color:red;">${errorMessage }</label></td>      		
            </tr>
             			
            <tr>
              	<td><label style="width: 90px;" for="category">Category:</label></td>
              	<td><input type="text" name="category" 
                    	id="category" size="60"
                    	value='<%= ( (String) entity.getProperty("category")) %>' disabled
                		class="form-control" />
               	</td>
            </tr>            	
                           
		    <tr>
		        <td>
				<input id="entityKeyId" type="hidden"  name="entityKeyId"
		      		     value='<%= ( (Long)  entity.getKey().getId() ) %>'></td>    
		    </tr>            	    		    
		</table>

		<br>		
		<div> 
			<table style="15px; font-size: 15px;">
		      <tr>
		      	<td style="width:50px;">
		        	<input style="background-color: #7FFF00;"
		        		id="delete" type="submit" name="btnAction" value="Delete"></td>
		        <td style="width:20px"></td>
		        <td style="width:50px;">
		        	<input  style="background-color: #ff6347;"
		        		id="cancel" type="submit" name="btnAction" value="Cancel"></td>
		      </tr>
		    </table>
	    </div>
		 		
	    
<%
	}
	else
	{
		System.out.println("Request entity is null");
	}    
%>
	  	<br>
		<div> 
			<div id="msg" style="display:none; font-size:15px; color:red;">
		    	   All input field are required!!!
		    </div>	  
		</div>
	  </form>	  	  	  
	</div>	
	
	<br><br>    	    
    <%@include file="footer.jsp" %>    
</body>
</html>