<!DOCTYPE html>
<html lang="en">
<head>
  <title>Results</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="searchresults.css" /> 
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
	    <div class="col-sm-2">
	    <a href="HomePage.jsp" class="btn btn-default homeButton" id="titleHome">in-or-out</a>
	    </div>
	    <div class="searchSection">
		  <form action="">
		  <div class="col-sm-3">
		    <div class="form-group">
		      <input type="text" class="form-control" id="foodSearch" placeholder="Find fries, sushi, pizza..." name="foodSearch">
		    </div>
		  </div>
		    <div class="col-sm-3">
		    	<button type="submit" class="btn btn-default searchButton">Search By <br/>Restaurant</button>
		    	<button type="submit" class="btn btn-default searchButton">Search By <br/>Recipe</button>
		     </div>
		  </form>
		  </div>
	    <div class="col-sm-3">
	    <form action="">
	    <button type="submit" class="btn btn-default btn-lg loginbutton" name="login" value="login">Log In</button>
	  	<button type="submit" class="btn btn-default btn-lg signupbutton" name="signup" value="signup">Sign Up</button>
	  	</form>
	    </div>
	  </div>
	  </div>
	</div>
  	<div class="jumbotron mainBox">
		<div class="row">
	    	<div class="col-sm-9"></div>
	    	<div class="col-sm-3">
	    		<div class="jumbotron filterBar"> 
	    		<h3>Filters</h3>
	    		<div class="parentCenter">
	    		<form action="">
	    			<input type="hidden" name="search" value=<%=request.getAttribute("foodSearch") %>>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Highest Rating</button> <br/>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Cost</button> <br/>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Vegetarian</button> <br/>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Vegan</button> <br/>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Gluten-Free</button> <br/>
				    <button type="submit" class="btn btn-default btn-lg filterButton">Lactose-Free</button> 
		  		</form>
		  		</div>
	    		</div>
	    	</div>
	    </div>
    </div>
	
</div>

</body>
</html>