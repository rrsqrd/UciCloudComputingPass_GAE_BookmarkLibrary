<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
	<script><%@include file="/WEB-INF/js/bookmarkPost.js"%></script>
	
	<script>
	</script>
  	  
<head>
	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Project:	Google App Engine</title>   
</head>

<!-- call to clearingFormFields() on page load was added for back button issues... -->
<body style=" color:blue; font-size: 15px; margin-top: 100px;"  
	onload="clearFormFields()">
				
<div class="container">  
    <br>
    <h1 style="padding-left: 100px;" >Welcome to your Library of Bookmark Posts!</h1>
    <h2 style="padding-left: 130px;">Login</h2>
    <div>
    	<table style="padding-left: 130px;">
      	<tr><td>
		    <form method="POST" name="loginForm" action="/login"  accept-charset="utf-8"> 		
			    <table>
			    <tr>
			    	<td style="width: 100px;">User Name</td>
			    	<td>		    		
			    	    <input type="text" name="userName" id="userName" onkeypress="return noEnter(event)"  
		      					value="${fn:escapeXml(userName)}" class="form-control" />
			    	</td>
			    </tr>
			    <tr>
			    	<td style="width: 100px;">Password</td>
			      	<td>
						<input type="text" name="passWord" id="passWord" onkeypress="return noEnter(event)"  
		      					value="${fn:escapeXml(passWord)}" class="form-control">		      		
			      	</td>
			    </tr>
				<tr>			
					<td><input id="submit" type="submit" name="btnAction" value="Login">												 			
					</td>
		  		<tr>
		  		</table>
			</form></td>
	  	</tr>   
	    </table>
    </div>
    
    <div id="errMsgParent">    	
<%
	  String loginErrMessage = (String) request.getAttribute("errorMessage");
	  if(loginErrMessage != null)
	  {	
%>
		
		<div id="errMsg" style="display:block; font-size:15px; color:red; padding-left: 140px;">
		    	   ${errorMessage}
		</div>
<%
	  }
%>
    </div>
    
    <br><br><br><br>
    <h4 style="padding-left: 100px; font-size: 10px;"><small><i>RRSQRD UCI Cloud Computing, Fall 2018</i></small></h4>

    <!--
    <br><br>
	<%@include file="footer.jsp" %>
	-->
	    
 </div>
 </body>
</html>