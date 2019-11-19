<!DOCTYPE html>
<html lang="en">
<head>
  <title>HomePage</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="homepage.css" /> 
  <link rel="shortcut icon" href="#">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

  <div class="header">
	  <div class="container">
	  <div class="row">
	    <div class="col-sm-4">
	    <button type="button" class="btn btn-default homeButton" id="titleHome">in-or-out</button>
	    </div>
	    <div class="col-sm-4"></div>
	    <div class="col-sm-4">
	    <form action="">
	    <button type="submit" class="btn btn-default btn-lg loginbutton" name="login" value="login">Log In</button>
	  	<button type="submit" class="btn btn-default btn-lg signupbutton" name="signup" value="signup">Sign Up</button>
	    </form>
	    </div>
	  </div>
	  </div>
	</div>
  	<div class="jumbotron mainBox">
		  <div class="container searchSection">
		  <form action="">
		  <div class="row">
		  <div class="col-sm-8">
		    <div class="form-group">
		      <input type="text" class="form-control" id="foodSearch" placeholder="Find fries, sushi, pizza..." name="foodSearch">
		    </div>
		  </div>
		    <div class="col-sm-4">
		    	<button type="submit" class="btn btn-default searchButton">Search By <br/>Restaurant</button>
		    	<button type="submit" class="btn btn-default searchButton">Search By <br/>Recipe</button>
		     </div>
		    </div>
		  </form>
		</div>
    </div>
	
</div>

</body>
</html>