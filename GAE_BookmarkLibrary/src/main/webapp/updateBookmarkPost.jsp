<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.google.appengine.api.datastore.Entity" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<script><%@include file="/WEB-INF/js/bookmarkPost.js"%></script>	
	<script>
		function handleUrlStrErrMsg(showMsg)
		{
		    var x = document.getElementById("urlStrErrMsg");
		    if(showMsg==false)
		    {
		    	x.innerHTML     = "";
		    	x.style.display = "none";		    
		    }
		    else
		    {
		    	x.innerHTML     = "URL is invalid";
		        x.style.display = "block";
		    }			
		}	
		function showErrMsg(showMsg) 
		{         	
		    //(show ? x.style.display = "none" : x.style.display = "block");
		    
		    var x = document.getElementById("errMsg");		    
		    if(showMsg==false)
		    {
		    	x.style.display = "none";		    
		    }
		    else
		    {
		        x.style.display = "block";
		    }
		}

		function validateUrlStr(urlStr)
		{
	    	if(!isUrlStrValid(urlStr))
		   	{
		   		handleUrlStrErrMsg(true);
		   		//alert("Validation UrlStr failure");
		   		return false;
		   	}
		   	else
		   	{
		   		// clear 
		   		handleUrlStrErrMsg(false);
		   	}
	    	
			//alert("Validation success")
	    	showErrMsg(false);

	    	// updateBtn is is type='button', not submit
	    	document.forms["updateForm"].submit(); 
	    	return true;
    	}		
		
		function validateInputFields() 
		{
			var tt      = document.forms["updateForm"]["topic"].value;		   
		   	var urlName = document.forms["updateForm"]["urlName"].value;
		   	var urlStr  = document.forms["updateForm"]["urlStr"].value;
		   	var ct      = document.forms["updateForm"]["category"].value;		   	
		   		   	
		   	var btnAction = document.activeElement.getAttribute('value');
		   	console.info("btnAction=" + btnAction)
		   	
		   	if(btnAction === "Cancel")
		   	{
		   		return true; // servlet will check btnAction and redirect to /show
		   	}		   	
		   	
		    if ((tt === null || tt === "") ||
		    	(urlName === null || urlName === "") ||	
		    	(urlStr  === null || urlStr  === "") ||
		    	(ct === null || ct === ""))
		    {
		    	//alert("Validation failure, null field")
		    	showErrMsg(true);
		        return false;
		    }
		    else
		    {
		    	return validateUrlStr(urlStr);
		    }
		}
	</script>

<head>	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Update Bookmark Post</title>
	<style><%@include file="../bookmarkPost.css"%></style>
		
	<style>
		label {
		  width:100px;
		  display: inline-block;
		}
	</style>
</head>

<!-- do not clearFrom fields on load. -->
<body style="margin-top:30px; padding-left: 25px; font-size: 15px;">    		
	<div class="container">
	<%--  <jsp:include page="/header.jsp" /> --%>

    <table style="15px; font-size: 15px;">
      <tr>
		<td style="width:100px;"></td>
      	<!-- 
      	<td style="width:30px;"></td>
        <td><strong><a style="color:blue" href="main.html">Main Page</a></strong></td>
        -->
        <td style="width:50px;"></td>
        <td><strong><a style="color:blue" href="/show">Show All Posts</a></strong></td>
        <td style="width:50px;"></td>
        <td><strong><a style="color:blue" href="logout.jsp">Logout</a></strong></td>
      </tr>
    </table>

	<h2 style="color:blue; margin-top:50px;">Update Bookmark Post</h2>
	
	<form method="POST" name="updateForm" action="/update"  accept-charset="utf-8"> 

		<%
		   	//@SuppressWarnings("unchecked")
			Entity entity = (Entity)request.getAttribute("bookmarkPostEntity");
			
		    if (entity != null)
			{				
				System.out.println("\n------------- updateBookmarkPost.jsp");
				System.out.println("topic     =" + entity.getProperty("topic").toString());
				System.out.println("urlName   =" + entity.getProperty("urlName").toString());
				System.out.println("urlStr    =" + entity.getProperty("urlStr").toString());
				System.out.println("entityKeyId =" + (Long) entity.getProperty("entityKeyId"));				
		%>
		<table>
		    <tr>
		      <td><label style="width: 90px;" for="topic">Topic:</label></td>
		      <td><input type="text" name="topic" 
		      			id="topic" size="60" onkeypress="return noEnter(event)"
		    	  		value='<%= ( (String) entity.getProperty("topic")) %>'	    	  		 	    	  	     
		      			class="form-control" /><td>
		    </tr>
 			
            <tr>            
                <td><label style="width: 90px;" for="urlName">URL Name: </label></td>
                <td> <input type="text" name="urlName" 
                        id="urlName" size="60" onkeypress="return noEnter(event)"  
                        value='<%= ( (String) entity.getProperty("urlName")) %>' 
                        class="form-control" />
                </td>
            	<td></td>
            </tr>
            <tr>
              	<td><label style="width: 90px;" for="urlStr">URL:</label></td>
              	<td><input type="text" name="urlStr" 
                    	id="urlStr" size="60" onkeypress="return noEnter(event)"
                    	value='<%= ( (String) entity.getProperty("urlStr")) %>'
                    	class="form-control" />
               	</td>
              	<td><label id="urlStrErrMsg" style="color:red;">${errorMessage }</label></td>      		
            </tr>	

            <tr>
              	<td><label style="width: 90px;" for="category">Category:</label></td>
              	<td><input type="text" name="category" 
                    	id="category" size="60" onkeypress="return noEnter(event)"
                    	value='<%= ( (String) entity.getProperty("category")) %>'
                		class="form-control" />
               	</td>
            </tr>  			            

		    <tr>
		        <td>
				<input id="entityKeyId" type="hidden"  name="entityKeyId"
		      		     value='<%= ( (Long)  entity.getKey().getId() ) %>'></td>    
		    </tr>            	    		    
		 </table>

<%
	}
	else
	{
		System.out.println("Request entity is null");
	}    
%>
	  	
		<div> 
			<div id="errMsg" style="display:none; font-size:15px; color:red;">
		    	   All input field are required!!!
		    </div>	  
		</div>		

		<div>  
			<table style="15px; font-size: 15px;">
			  <tr style="height: 20px;"></tr>
		      <tr>
		      	<td style="width:50px;">
		        	<input style="background-color: #7FFF00;"  
		        		id="update" type="button" name="btnAction" value="Update"
		        		onclick='validateInputFields(event);'>
		        </td>
		        	
		        <td style="width:15px"></td>
		        
		        <td style="width:50px;">
		        	<input style="background-color: #ff6347;" 
		        		id="cancel" type="submit" name="btnAction" value="Cancel"></td>
		      </tr>
		    </table>
	    </div>
	  </form>	  	  	  
	</div>	
	
	<br><br>
	<%@include file="footer.jsp" %>
	<!--     	
	<br>
    <h4><i style="font-size: 10px;">RRSQRD UCI Cloud Computing, Fall 2018</i></h4>
    -->    
</body>
</html>