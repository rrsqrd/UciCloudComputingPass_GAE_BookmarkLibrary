<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
		   		return false;
		   	}
		   	else
		   	{
		   		// clear 
		   		handleUrlStrErrMsg(false);
		   	}
	    	
	    	showErrMsg(false);
	    	
	    	// createBtn is type='button', not submit	    	
	    	document.forms["createForm"].submit(); 
	    	return true;
    	}
		
		function validateInputFields(event) 
		{			
			var tt      = document.forms["createForm"]["topic"].value;
		   	var urlName = document.forms["createForm"]["urlName"].value;
		   	var urlStr  = document.forms["createForm"]["urlStr"].value;
		   	var ct      = document.forms["createForm"]["category"].value;		   	
		   				
			var btnAction = document.activeElement.getAttribute('value');	
			
		   	if(btnAction === "Cancel")
		   	{
		   		return true; // servlet will check btnAction and redirect to /show
		   	}

			if ((tt === null || tt === "") ||						         
			    (urlName === null || urlName === "") ||	
			    (urlStr  === null || urlStr  === "") ||
			    (ct === null || ct === "") )
			
		    {
				alert("Validation failure, null field")
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
	<title>Create BookMark</title>
	<style><%@include file="../bookmarkPost.css"%></style>
		
	<style>
		label {
		  width:100px;
		  display: inline-block;
		}
	</style>
</head>

<body style="margin-top:30px; padding-left: 50px; font-size: 15px" onload="clearFormFields()">			
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
		
	  <br>
	  <h3 style="color:blue">Create New Bookmark Post </h3>
	
	  <form method="POST" name="createForm" action="/create"  accept-charset="utf-8"> 
        <table>

            <tr>            
                <td><label style="width: 90px" for="topic">Topic:</label></td>
                <td> <input type="text" name="topic" 
                        id="topic" size="60" onkeypress="return noEnter(event)"  
                        value="${fn:escapeXml(topic)}" class="form-control" />
                </td>
                <td><small>(must be unique)</small></td>
            </tr>            
                 
            <tr>            
                <td><label style="width: 90px" for="urlName">URL Name: </label></td>
                <td><input type="text" name="urlName" 
                    	id="urlName" size="60" onkeypress="return noEnter(event)"  
                    	value="${fn:escapeXml(urlName)}" class="form-control" />
                </td>
                <td></td>
            </tr>
            <tr>
              <td><label style="width: 90px" for="urlStr">URL:</label></td>
              <td><input type="text" name="urlStr" 
                    	id="urlStr" size="60" onkeypress="return noEnter(event)"
                    	value="${fn:escapeXml(urlStr)}" class="form-control" /></td>
              <td><label id="urlStrErrMsg" style="color:red;">${errorMessage }</label></td>      		
            </tr>                
                        
            <tr>
              <td><label style="width: 90px" for="category">Category:</label></td>
              <td><input type="text" name="category" 
                    	id="category" size="45" onkeypress="return noEnter(event)"
                    	value="${fn:escapeXml(category)}" class="form-control" /></td>
                <td>
                <td></td>
            </tr>
                        
		</table>            
	    
	    <br>
        <div> 
			<div id="errMsg" style="display:none; font-size:15px; color:red;">
		    	   All input field are required!!!</div>
		</div>	            
	    <br>
        <div>
            <table style="15px; font-size: 15px;">
              <tr>
                <td style="width:50px;">
                    <input style="background-color: #7FFF00;" 
                        id="create" type="button" name="btnAction" value="Create"
                        onclick='validateInputFields(event);'>
                </td>
                <td style="width:15px"></td>
                <td style="width:50px;">
                    <input style="background-color: #ff6347;" 
                        id="cancel" type="submit" name="btnAction" value="Cancel">
                </td>
              </tr>
            </table>
        </div>	          
	  </form>	  
	</div>	
	
	<br><br>    	
    <%@include file="footer.jsp" %>        
</body>
</html>