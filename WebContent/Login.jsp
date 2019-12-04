<%@ page session="true" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Login</title>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="star-rating-svg.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<link href="https://fonts.googleapis.com/css?family=Lustria&display=swap" rel="stylesheet">

	<style>
		body {
			font-size: 20px;
			height: 100%;
			font-family: 'Lustria', 'Lucida', 'Arial', sans-serif;
			color: white;
			padding: 0;
			margin: 0;
			background-size: cover;
			background-image: url(homepagefood.png);
			background-repeat: no-repeat;
		}

		#filter {
			width: 100%;
			height: 100vh;
			margin: 0;
			padding: 0;
			background-size: cover;
			background-color: rgba(0, 0, 0, 0.3);
		}

		.mainTextParent {
			margin: 0 auto;
			width: 1200px;
			height: 500px;
			display: table;
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

		.inputPassword {
			color: white;
		}
		
		a:hover {
			text-decoration: none;
		}

	</style>

</head>

<body>
	<div id="filter">
		<div id="header">
			<div style="padding-top: 25px;">
				<a href="HomePage.jsp">
					<span style="margin-left: 10%; margin-top: 100px; width: 10%; font-size: 50px; color: black;">in-or-out</span>
				</a>
			</div>
		</div>
		<!-- This div made vertically centering easier -->
		<div class="mainTextParent">
			<div class="mainText" style="margin-left: 80px;">

				<form name="myform" method="POST" action="Login">
					<div class="form-group row">
						<label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="inputUsername" placeholder="Username" name="username">
						</div>
					</div>
					<div class="form-group row">
						<label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword" placeholder="Password" name="password">
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-10 offset-sm-2">
							<button type="submit" class="btn btn-primary btn-block">Login</button><% String error = ""; if (request.getAttribute("login-error") != null && !request.getAttribute("login-error").equals("")) error = (String)(request.getAttribute("login-error")); %> <%= error %>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>

	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>

</body>

</html>
