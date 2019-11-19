<%@ page import ="inORout.Business" %>
<%@ page import ="inORout.Recipe" %>
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
		var type = "<%=request.getAttribute("searchType") %>>";
		type = type.substring(0, type.length-1);
		if(type =="Search by Restaurant")
			{
				let el = document.getElementById("restaurantTab");
				el.className = "active";
				let div = document.getElementById("restaurantDiv");
				div.className = "tab-pane fade in active";
				let div2 = document.getElementById("recipeDiv");
				div2.className = "tab-pane fade";
				
				}
		
			else
				{
				let el = document.getElementById("recipeTab");
				el.className = "active";
				let div = document.getElementById("recipeDiv");
				div.className = "tab-pane fade in active";
				let div2 = document.getElementById("restaurantDiv");
				div2.className = "tab-pane fade";
			}
		var searchTerm = "<%=request.getAttribute("searchTerm") %>>";
		searchTerm = searchTerm.substring(0,searchTerm.length-1);
		var searchType = "<%=request.getAttribute("searchType") %>>";
		searchType = searchType.substring(0,searchType.length-1);
		<% Business[] restaurants = (Business[]) request.getAttribute("YelpResults");
			Recipe[] recipes = (Recipe[]) request.getAttribute("EdamamResults");
		%>
			let recF = document.getElementById("recipeForm");
			let resF = document.getElementById("restaurantForm");
		
		recF.innerHTML ="<input type=\"hidden\" name=\"searchTerm\"value = \"" + searchTerm + "\"> ";
		recF.innerHTML += "<input type=\"hidden\" name=\"searchType\"value = \"" + searchType + "\"> ";
		resF.innerHTML ="<input type=\"hidden\" name=\"searchTerm\"value = \"" + searchTerm + "\"> ";
		resF.innerHTML += "<input type=\"hidden\" name=\"searchType\"value = \"" + searchType + "\"> ";
		<%
		int max = restaurants.length;
		if(max > 10){
			max =10;
		}
		for(int i =0; i < max; i++)
		{%>
			resF.innerHTML += "<div class=\"row\">";
			resF.innerHTML += " <div class=\"col-sm-6\"> <input type=\"submit\" class= \"img-thumbnail image\"name= \"restaurant\" value=\"" 
				+ "<%=restaurants[i].getId()%>" + "\" style=\"background-image: url('"+ 
				"<%=restaurants[i].getImageUrl()%>" + "');\">";
			resF.innerHTML+= "</div>";
			resF.innerHTML += " <div class=\"col-sm-6\">";
			resF.innerHTML += "<h4>" + "<%=restaurants[i].getName()%>" + "</h4> <br/>";
			resF.innerHTML += "<h5>" + "<%=restaurants[i].getRating()%>" + "</h5><br/>";
			resF.innerHTML += "<h5>" + "<%=restaurants[i].getPrice()%>" + "</h5><br/>";
			
			resF.innerHTML+= "<br/> </div> </div>";
		<%}%>
		
		<%
		max = recipes.length;
		System.out.print(recipes.length);
		if(max > 10){
			max =10;
		}
		for(int i =0; i < max; i++)
		{%>
			recF.innerHTML += "<div class=\"row\"> <div class=\"col-sm-6 \">";
			recF.innerHTML += "<input type=\"submit\" class= \"img-thumbnail image\"name= \"restaurant\" value=\"" 
				+ "<%=recipes[i].getSource()%>" + "\" style=\"background-image: url('"+ 
				"<%=recipes[i].getImage()%>" + "');\">";
			recF.innerHTML+= "</div>";
			recF.innerHTML += " <div class=\"col-sm-6\">";
			recF.innerHTML += "<h3>" + "<%=recipes[i].getLabel()%>" + "</h3> <br/>";
			recF.innerHTML += "<h4>" + "<%=recipes[i].getCalories()%>" + "</h4><br/>";
			
			recF.innerHTML+= "<br/> </div>";
		<%}%>
		
			
			
		
		
	}
	</script>
</head>
<body onload="profile(); loadResults();">

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
	    	<div class="col-sm-9" id="mainBlock">
	    	<ul class="nav nav-tabs" id="tabs"> 
	    	<li id="restaurantTab"><a data-toggle="tab" href="#restaurantDiv">Restaurants</a></li>
    		<li id="recipeTab"><a data-toggle="tab" href="#recipeDiv">Recipes</a></li> 
	    	</ul>
	    	
	    	<div class="tab-content">
		    <div id="restaurantDiv">
		    <form id="restaurantForm" action="Detail">
		    </form>
		    </div>
		    <div id="recipeDiv">
		    <form id="recipeForm" action="Detail">
		    </form>
		    </div>
		    </div>
	    	
	    	
	    	</div>
	    	<div class="col-sm-3">
	    		<div class="jumbotron filterBar"> 
	    		<h3>Filters</h3>
	    		<div class="parentCenter">
	    		<form action="">
	    			<input type="hidden" name="searchTerm" value=searchTerm>
	    			<input type="hidden" name="searchType" value=searchType>
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