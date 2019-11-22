<%@ page session="true" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="star-rating-svg.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Lustria&display=swap" rel="stylesheet">

<style>

* {
	color: #343838
}

body {
	font-size: 20px;
	font-family: 'Lustria', 'Lucida', 'Arial', sans-serif;
	background-size: cover; 
	background-repeat: no-repeat;
}
 
  .mainTextParent {
	 margin: 0 auto;
	 width: 1200px;
	 height: 500px;
	 display: table;
	 border-bottom: 1px dashed #ccc;
 }

#header {
	height: 140px;
	background-color: #fc9d90;
	width: 100%;
	margin-bottom: 100px;
} 

.btn {
	background-color: #9aadf2;
	border: grey;
	color: black;
}

</style>

</head>
<body>
	<div>
		<div id="header">
		<div style="padding-top: 25px;">
			<a href="HomePage.jsp">
			
		    	<span style="margin-left: 5%; width: 10%; font-size: 50px">in-or-out</text>
		    
		    </a>
		    </div>
		    
		
	    </div>
	    <div class="mainTextParent">
		    <div class="mainText">
		    
		    
		    
			<form name="myform" method="GET" action="Register">
			    <div class="form-group row">
			        <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
			        <div class="col-sm-10">
			            <input type="text" class="form-control" id="inputEmail" placeholder="Username" name="username">
			        </div>
			    </div>
			    <div class="form-group row">
			        <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
			        <div class="col-sm-10">
			            <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="password">
			        </div>
			    </div>
			    <div class="form-group row">
			        <label for="inputPassword" class="col-sm-2 col-form-label">Confirm Password</label>
			        <div class="col-sm-10">
			            <input type="password" class="form-control" id="inputConfirmPassword" placeholder="Confirm Password" name="confirmpass">
			        </div>
			    </div>
			    <div class="form-group row">
			        <div class="col-sm-10 offset-sm-2">
			        	<!-- <input type="submit" name="submit" value="Submit"> -->
			        	
			            <button role="button" type="submit" class="btn btn-primary btn-block">Register</button><% String error = ""; if (request.getAttribute("error") != null) error = (String)(request.getAttribute("error")); %> <%= error %>
			        </div>
			    </div>
			</form>
		    
			</div>
		</div>
		
	</div>
		
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>

</body>
</html>
