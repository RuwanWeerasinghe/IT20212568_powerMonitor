$(document).ready(function()
{

	$("#alertSuccess").hide();
	$("#alertError").hide();
});


$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------

	
	 $("#alertSuccess").text("");
 	 $("#alertSuccess").hide();
 	 $("#alertError").text("");
 	 $("#alertError").hide();
 	 
 	 
   	// Form validation-------------------
  	
	var status = validateItemForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 
         return;
    }
 
	// If valid------------------------
	
	
	var type = ($("#hidPowerIDSave").val() == "") ? "POST" : "PUT";

	 $.ajax(
 	 {
 		url : "PowerAPI",
 		type : type,
 		data : $("#formPower").serialize(),
 		dataType : "text",
	    complete : function(response, status)
        {
   
      			onItemSaveComplete(response.responseText, status);
	    }
	    
     });
     
});
function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		 var resultSet = JSON.parse(response);
		 
	 	 if (resultSet.status.trim() == "success")
		 {
 				$("#alertSuccess").text("Successfully saved.");
		    	$("#alertSuccess").show();
 				$("#divPowerGrid").html(resultSet.data);
 			
 	 	  } else if (resultSet.status.trim() == "error")
 	 	  {
 	 
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
		  }
		  
    } else if (status == "error")
    {
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 			
 	} else
 	{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
    } 

 	$("#hidPowerIDSave").val("");
	 $("#formPower")[0].reset();
}
$(document).on("click", ".btnUpdate", function(event)
{
		var id = event.target.id;
		$("#hidPowerIDSave").val(id.substring(0, id.length-1));
 		$("#meterNo").val($(this).closest("tr").find('td:eq(0)').text());
 		$("#meterReading").val($(this).closest("tr").find('td:eq(1)').text());
 		$("#units").val($(this).closest("tr").find('td:eq(2)').text());
 		$("#readingDate").val($(this).closest("tr").find('td:eq(3)').text());
});
$(document).on("click", ".btnRemove", function(event)
{
	 $.ajax(
 	{
 		url : "PowerAPI",
 		type : "DELETE",
	    data : "monitorId=" + $(this).data("UserId"),
 		dataType : "text",
 		complete : function(response, status)
		{
			 onItemDeleteComplete(response.responseText, status);
 		}
	 });
});



function onItemDeleteComplete(response, status)
{
	if (status == "success")
    {
 			var resultSet = JSON.parse(response);
 			
		   if (resultSet.status.trim() == "success")
 		   {
 		   
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				
			    $("#divPowerGrid").html(resultSet.data);
			    
			    setTimeout( (function(){alert(43)}, 1000));
 			} else if (resultSet.status.trim() == "error")
 			{
				 $("#alertError").text(resultSet.data);
 				 $("#alertError").show();
		    }
 	} else if (status == "error")
    {
		 $("#alertError").text("Error while deleting.");
 		 $("#alertError").show();
    } else
    {
 		$("#alertError").text("Unknown error while deleting..");
 		$("#alertError").show();
 	}
}
function validateItemForm()
{
	// SERVICE NAME
	if ($("#meterNo").val().trim() == "")
 	{
		 return "Insert Metre Number.";
    }
    
    
	// SERVICE TYPE
	if ($("#meterReading").val().trim() == "")
    {
		 return "Insert Meter Reading.";
 	} 
 	

	// UNIT COST-------------------------------
	if ($("#units").val().trim() == "")
    {
 		return "Insert Units.";
 	}
 	
 	
	
	// MAX UNIT------------------------
	if ($("#readingDate").val().trim() == "")
   {
		 return "Insert Reading Date.";
   }
   

   return true;
}
