<%@ page import="inORout.Business"%>
<%@ page import="inORout.Recipe"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Results</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="#">
<link rel="stylesheet" type="text/css" href="searchresults.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Lustria&display=swap"
	rel="stylesheet">
<script>
	<%
	if(request.getAttribute("searchType")==null){
		request.getRequestDispatcher("HomePage.jsp").forward(request, response);
	}
	
	%>
		function profile() {
			let div = document.getElementById("buttonLog");
			if ("<%=session.getAttribute("Current user")%>" == "null") {
				let el = document.createElement("input");
				el.type = "submit";
				el.name = "logChoice";
				el.value = "Login";
				el.className = "btn btn-default btn-lg loginbutton";
				div.appendChild(el);
		
				let el2 = document.createElement("input");
				el2.type = "submit";
				el2.name = "logChoice";
				el2.value = "Signup";
				el2.className = "btn btn-default btn-lg loginbutton";
				div.appendChild(el2);
			} else {
				let ell = document.createElement("input");
				ell.type = "submit";
				ell.name = "logChoice";
				ell.value = "Profile";
				ell.className = "btn btn-default btn-lg loginbutton";
				div.appendChild(ell);
		
				let el = document.createElement("input");
				el.type = "submit";
				el.name = "logChoice";
				el.value = "Signout";
				el.className = "btn btn-default btn-lg loginbutton";
				div.appendChild(el);
			}
		}

		function loadResults() {
			var type = "<%=request.getAttribute("searchType") %>";

			//type = type.substring(0, type.length - 1);
			if (type == "Search by Restaurant") {
				let el = document.getElementById("restaurantTab");
				el.className = "active";
				let div = document.getElementById("restaurantDiv");
				div.className = "tab-pane fade in active";
				let div2 = document.getElementById("recipeDiv");
				div2.className = "tab-pane fade";

			} else {
				let el = document.getElementById("recipeTab");
				el.className = "active";
				let div = document.getElementById("recipeDiv");
				div.className = "tab-pane fade in active";
				let div2 = document.getElementById("restaurantDiv");
				div2.className = "tab-pane fade";
			}

			var searchTerm = "<%=request.getAttribute("searchTerm ") %>";
			searchTerm = searchTerm.substring(0, searchTerm.length - 1);
			var searchType = "<%=request.getAttribute("searchType ") %>";
			searchType = searchType.substring(0, searchType.length - 1); 
			<% 
			Business[] restaurants = (Business[]) request.getAttribute("YelpResults");
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
		{
			if(restaurants[i].getName().contains("\""))
			{
				continue;
			}
			String[] addy = restaurants[i].getDisplayAddress().split("\n");%>
			resF.innerHTML += "<div class=\"row\">";
			resF.innerHTML += " <div class=\"col-sm-4 labelling\"> <input type=\"submit\" class= \"img-thumbnail image\"name= \"restaurant\" value=\"" 
				+ "<%=restaurants[i].getId()%>" + "\" style=\"background-image: url('"+ 
				"<%=restaurants[i].getImageUrl()%>" + "');\">";
			resF.innerHTML+= "</div>";
			
			var stars = "<%=restaurants[i].getRating()%>";
			stars = parseInt(stars, 10);
   			var rate ="";
       		for(var j=0; j < stars; j++)
       				{
       			rate += "<img class= \"star\" src=\"redstar.png\">"; 
       				}
       		for(var j=stars; j < 5; j++)
				{
			rate += "<img class= \"star\" src=\"greystar.png\">"; 
				}
       		rate += "       ";
       		rate += "<%=restaurants[i].getReviewCount()%>";
       		rate += " Reviews";
			resF.innerHTML += " <div class=\"col-sm-4\"><h4>" + "<%=restaurants[i].getName()%>" + 
			"</h4><h5 id=\"rate\">" + rate + "</h5><h5>" + 
			"<%=restaurants[i].getPrice()%>" + "</h5>";
   			
       		resF.innerHTML += "</div>";
			resF.innerHTML += " <div class=\"col-sm-4\"> <h6>" + "<%=addy[0]%>" +
			"</h6> <h6> Phone: " + "<%=restaurants[i].getDisplayPhone()%>" + 
			"</h6> <br/> <br/></div> </div>";
			
			
		<%}%>
		
		<%
		max = recipes.length;
		System.out.print(recipes.length);
		if(max > 10){
			max =10;
		}
		for(int i =0; i < max; i++)
		{
			if(recipes[i].getLabel().contains("\""))
			{
				continue;
			}
		%>
			recF.innerHTML += "<div class=\"row\">";
			recF.innerHTML += " <div class=\"col-sm-4 labelling\"> <input type=\"submit\" class= \"img-thumbnail image\"name= \"recipe\" value=\"" 
				+ "<%=recipes[i].getUri()%>" + "\" style=\"background-image: url('"+ 
				"<%=recipes[i].getImage()%>" + "');\">";
			recF.innerHTML+= "</div>";
			recF.innerHTML += " <div class=\"col-sm-8\">";
			recF.innerHTML += "<h4>" + "<%=recipes[i].getLabel()%>" + "</h4> <br/>";
			recF.innerHTML += "<h5> Calories: " + "<%=(int) recipes[i].getCalories()%>" + "</h5>";
			recF.innerHTML += "<h5> Servings: " + "<%=recipes[i].getServings()%>" + "</h5><br/>";
			
			recF.innerHTML+= "<br/> <br/></div></div>";
		<%}%>
	}
</script>
</head>

<body onload="profile(); loadResults();">

	<div class="container-fluid mycontainer">

		<div class="header">
			<div class="row">
				<div class="col-sm-2">
					<a href="HomePage.jsp" class="btn btn-default homeButton"
						id="titleHome">in-or-out</a>
				</div>
				<div class="searchSection">
					<form action="Search">
						<div class="col-sm-3">
							<div class="form-group">
								<input type="text" class="form-control" id="foodSearch"
									placeholder="Find fries, sushi, pizza..." name="searchTerm">
							</div>

						</div>
						<div class="col-sm-3">
							<input type="submit" class="btn btn-default searchButton"
								name="searchType" value="Search by Restaurant"> <input
								type="submit" class="btn btn-default searchButton"
								name="searchType" value="Search by Recipe">
						</div>
					</form>
				</div>
				<div class="col-sm-4">
					<div class="col-sm-4">
						<form action="ServletLogger" method="GET" id="buttonLog"></form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="jumbotron mainBox">
		<div class="row">
			<div class="col-sm-9" id="mainBlock">
				<ul class="nav nav-tabs" id="tabs">
					<li id="restaurantTab"><a data-toggle="tab"
						href="#restaurantDiv">Restaurants</a></li>
					<li id="recipeTab"><a data-toggle="tab" href="#recipeDiv">Recipes</a></li>
				</ul>

				<div class="tab-content">
					<div id="restaurantDiv">
						<form id="restaurantForm" action="Detail"></form>
					</div>
					<div id="recipeDiv">
						<form id="recipeForm" action="Detail"></form>
					</div>
				</div>


			</div>
			<div class="col-sm-3">
				<div class="jumbotron filterBar">
					<div class="parentCenter">
						<form action="">
							<input type="hidden" name="searchTerm"
								value="<%=request.getAttribute("searchTerm")%>"> <input
								type="hidden" name="searchType"
								value="<%=request.getAttribute("searchType")%>">

							<h3 class="text-center">Filter By:</h3>

							<div class="btn-group-toggle" data-toggle="buttons">
								<label class="btn btn-default btn-lg filterButton"> <input
									type="checkbox" name="option" autocomplete="off"
									value="vegetarian"> Vegetarian
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="checkbox" name="option" autocomplete="off" value="vegan">
									Vegan
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="checkbox" name="option" autocomplete="off"
									value="gluten_free"> Gluten-Free
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="checkbox" name="option" autocomplete="off"
									value="dairy-free"> Lactose-Free
								</label>
							</div>

							<div class="btn-group-toggle" data-toggle="buttons">
								<h3 class="text-center">Price:</h3>
								<label class="btn btn-default btn-lg filterButton price">
									<input type="radio" name="price" autocomplete="off" value="0">
									No Preference
								</label> <br> <label
									class="btn btn-default btn-lg filterButton price"> <input
									type="radio" name="price" autocomplete="off" value="1">
									$
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="price" autocomplete="off" value="2">
									$$
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="price" autocomplete="off" value="3">
									$$$
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="price" autocomplete="off" value="4">
									$$$$
								</label>
							</div>

							<div class="btn-group-toggle" data-toggle="buttons">
								<h3 class="text-center">Sort By:</h3>
								<label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="sort" autocomplete="off" value="none">
									No Sorting
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="sort" autocomplete="off" value="distance">
									Distance
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="sort" autocomplete="off" value="rating">
									Rating
								</label> <label class="btn btn-default btn-lg filterButton"> <input
									type="radio" name="sort" autocomplete="off"
									value="review_count"> Review Count
								</label>
							</div>




							<button type="submit" class="btn btn-lg btn-primary">Submit
								Search</button>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script type="text/javascript" src="jquery.js"></script>
	<script>
	
	if("<%=session.getAttribute("longitude")%>"
		  == "null") {

			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(p) {
					$.ajax({
						method : "POST",
						url : "SetLocation?",
						data : {
							longitude : p.coords.longitude,
							latitude : p.coords.latitude,
						}
					});

				});
			}
		}
	</script>

</body>

</html>
