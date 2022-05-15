<%@ page import="model.Power"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    



<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<meta charset="ISO-8859-1">
<title>Power Service Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/power.js"></script>
</head>
<body>
<div class = "container">
<div class = "row">
<div class = "col">
	<h1>Power Service Management</h1>
	
	<form id="formPower" name="formPower" method="post" action="index.jsp">
	
		 Meter No:
		 <input id="meterNo" name="meterNo" type="text"
 						class="form-control form-control-sm">
 						
		<br> Meter Reading:
		<input id="meterReading" name="meterReading" type="text"
 						class="form-control form-control-sm">
 						
		<br> Units:
		<input id="units" name="units" type="text"
 						class="form-control form-control-sm">
 										
		<br> Reading Date:
		<input id="readingDate" name="readingDate" type="text"
						 class="form-control form-control-sm">
						 
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Save"
						 class="btn btn-primary">
						 
		<input type="hidden" id="hidPowerIDSave" name="hidPowerIDSave" value="">
	</form>
	
	<br/>
	<!-- Show output -->

	<div id= "alertSuccess" class="alert alert-success">
 	
 		
 	</div>
 	<div id = "alertError" class="alert-danger"></div>
	
	<br>
	<br>
	
	<div id ="divPowerGrid">
	<%
	 Power userObj = new Power(); 
	 out.print(userObj.readPower()); 
	%>
	</div>

</body>
</html> 