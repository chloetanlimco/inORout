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
  <script>
	function profile() {
		let div = document.getElementById("buttonLog");
		if("<%=session.getAttribute("Current user")%>" == "null")
		{
			let el = document.createElement("input");
			el.name = "logChoice";
			el.value = "Login";
			el.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el);
			
			let el2 = document.createElement("input");
			el2.name = "logChoice";
			el2.value = "Signup";
			el2.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el2);
			
		}
		else
		{
			let ell = document.createElement("input");
			ell.name = "logChoice";
			ell.value = "Profile";
			ell.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(ell);
			
			let el = document.createElement("input");
			el.name = "logChoice";
			el.value = "Signout";
			el.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el);
		}
	}
	function loadResults()
	{
		if("<%=request.getAttribute("searchTerm")%>" == "Search by Restauarant")
			{
				
			}
	}
	</script>
</head>
<body onload="profile();">

<div class="container-fluid">

  <div class="header">
	  <div class="container">
	  <div class="row">
	    <div class="col-sm-2">
	    <a href="HomePage.jsp" class="btn btn-default homeButton" id="titleHome">in-or-out</a>
	    </div>
	    <div class="searchSection">
		  <form action="Search.java">
		  <div class="col-sm-3">
		    <div class="form-group">
		      <input type="text" class="form-control" id="foodSearch" placeholder="Find fries, sushi, pizza..." name="foodSearch">
		    </div>
		  </div>
		    <div class="col-sm-3">
		    	<input type="submit" class="btn btn-default searchButton" name="searchType" value="Search by Restaurant">
		    	<input type="submit" class="btn btn-default searchButton" name="searchType" value="Search by Recipe">
		     </div>
		  </form>
		  </div>
	    <div class="col-sm-3">
	    <form action="Logger.java" id="buttonLog">
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
	    			<input type="hidden" name="search" value=<%=request.getAttribute("searchTerm") %>>
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