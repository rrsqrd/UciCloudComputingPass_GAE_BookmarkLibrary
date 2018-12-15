

	function noEnter(e) 
	{		
		e   = e || window.event || {};    					//chrome & IE
	    var charCode = e.charCode || e.keyCode || e.which;  // firefox 
	    
	    if (charCode == 13) 
	    {
	        //e.stopPropagation();
	        return false;	   
	    }
	    		
	    return true;
	}
	
	//-----------------------
	// Prevent back button from re populating the form on page load.
	// Clears all non-action/btn input fields
	//-----------------------
	function clearFormFields() 
	{
		var inputFields = document.getElementsByTagName('input');		
		for(var i = 0; i < inputFields.length;i++) 
		{
			if((inputFields[i].value != "Cancel") && (inputFields[i].value != "Create") &&
			   (inputFields[i].value != "Update") && (inputFields[i].value != "Delete") && 
			   (inputFields[i].value != "Submit") && (inputFields[i].value != "Login"))
			{					
				inputFields[i].value="";
			}
		}	
		
		var txtAreas = document.getElementsByTagName('textarea');
		for(var i = 0; i < txtAreas.length;i++)
		{
			txtAreas[i].value="";
		}
	}

	/**  
	 * --------------------  VALIDATE URL string ----------------- 
	 * @param urlStr
	 * @returns
	 */
	function isUrlStrValid(urlStr)
	{
		var regexp =  /^(?:(?:https?):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
		if (regexp.test(urlStr))
	    {
			console.info("isUrlStrValid Valid url: " + urlStr);
			return true;
	    }
	    else
	    {
	    	console.info("isUrlStrValid Invalid url: " + urlStr);
	    	return false;
	    }
	}
	 
	
	