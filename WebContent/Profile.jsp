<%@ page import="inORout.Business"%>
<%@ page import="inORout.Recipe"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Profile</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="profile.css" />
<link rel="shortcut icon" href="#">
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
	function profile() {
		let div = document.getElementById("buttonLog");
		if("<%=session.getAttribute("Current user")%>" == "null")
		{
			let el = document.createElement("input");
			el.type = "submit";
			el.name = "logChoice";
			el.value = "Login";
			el.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el);
			
			let el2 = document.createElement("input");
			el.type = "submit";
			el2.name = "logChoice";
			el2.value = "Signup";
			el2.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el2);
		}
		else
		{
			let el = document.createElement("input");
			el.type = "submit";
			el.name = "logChoice";
			el.value = "Signout";
			el.className = "btn btn-default btn-lg loginbutton";
			div.appendChild(el);
		}
	}
	function loadResults()
	{
		var type = "<%=request.getAttribute("searchType") %>";
		type = type.substring(0, type.length-1);
		var searchTerm = "<%=request.getAttribute("searchTerm") %>";
		searchTerm = searchTerm.substring(0,searchTerm.length-1);
		var searchType = "<%=request.getAttribute("searchType") %>";
		searchType = searchType.substring(0,searchType.length-1);
		let resF = document.getElementById("favorites");
		
		var rSug = '${RecipesSuggestions}';
		var bizz = '${Businesses}';
		var num = '${numBusinesses}';
		var bSug = '${BusinessSuggestions}';
		
		//parse business suggestions
		//var rs = JSON.parse(rSug);
		bSug = bSug.replace(/\\n/g, "\\n")  
               .replace(/\\'/g, "\\'")
               .replace(/\\"/g, '\\"')
               .replace(/\\&/g, "\\&")
               .replace(/\\r/g, "\\r")
               .replace(/\\t/g, "\\t")
               .replace(/\\b/g, "\\b")
               .replace(/\\f/g, "\\f");
		// remove non-printable and other non-valid JSON chars
		bSug = bSug.replace(/[\u0000-\u0019]+/g,""); 
		
		var bs = JSON.parse(bSug);
		
		
		//parse recipe suggestions
		rSug = rSug.replace(/\\n/g, "\\n")  
        .replace(/\\'/g, "\\'")
        .replace(/\\"/g, '\\"')
        .replace(/\\&/g, "\\&")
        .replace(/\\r/g, "\\r")
        .replace(/\\t/g, "\\t")
        .replace(/\\b/g, "\\b")
        .replace(/\\f/g, "\\f");
		// remove non-printable and other non-valid JSON chars
		rSug = rSug.replace(/[\u0000-\u0019]+/g,""); 
	
		var rs = JSON.parse(rSug);
		
		<% 
		Business[] restaurants = (Business[]) request.getAttribute("Businesses");
		Recipe[] recipes = (Recipe[]) request.getAttribute("Recipes");
		int numRecipes = (int)(request.getAttribute("numRecipes"));
		//Business[] restaurants = null;
		//Recipe[] recipes = null;
		
		int numBusinesses = (int)(request.getAttribute("numBusinesses"));
		int rows = numRecipes+numBusinesses+1;
		%>
		resF.innerHTML ="<input type=\"hidden\" name=\"searchTerm\"value = \"" + searchTerm + "\"> ";
		resF.innerHTML += "<input type=\"hidden\" name=\"searchType\"value = \"" + searchType + "\"> ";
		
		var main = document.getElementById("main");
		<%for (int i=0; i<rows; i++){%>
			main.innerHTML += "<div id=\"r"+"<%=i%>"+"header\"></div><div id=\"row"+"<%=i%>"+"\">";
	    	main.innerHTML += "<div class=\"container testimonial-group\">";
	    	main.innerHTML += "<div class=\"row\" style=\"overflow-x:auto;white-space:nowrap;\" id=\"r"+"<%=i%>"+"\"></div></div></div>";
		<%} %>
		var curr;
		var temp;
	
		if (<%=rows%>!=1){ //if there are favorites, make a favorites row
			curr = document.getElementById("r0");
			temp = document.getElementById("r0header");
			temp.innerHTML += "<h4>Your favorites</h4>";
			<% 
			for (int j=0; j<numBusinesses; j++){%>

				curr.innerHTML += "<div class=\"col elementBlock\" style=\"display:inline-block;float:none;\"><form id=\"details\" action=\"Detail\"><input type=\"submit\" class= \"image thumb\"name= \"restaurant\" value=\"" 
				+ "<%=restaurants[j].getId()%>"+ "\" style=\"background-image: url('"+ 
				"<%=restaurants[j].getImageUrl()%>" + "');border-radius:17px; color:rgba(0,0,0,0);\">"+"<span class=\"name\"></form>" + "<%=restaurants[j].getName()%></span></div>";
			<%}
			
			for (int j=0; j<numRecipes; j++){
				System.out.println(j);
				System.out.println(recipes[j].getUri());%>
			curr.innerHTML += "<div class=\"col elementBlock\" style=\"display:inline-block;float:none;\"><form id=\"details\" action=\"Detail\"><input type=\"submit\" class= \"image thumb\" name= \"recipe\" value=\"" 
			+ "<%=recipes[j].getUri()%>"+ "\" style=\"background-image: url('"+ 
			"<%=recipes[j].getImage()%>" + "');border-radius:17px; color:rgba(0,0,0,0);\"><span class=\"name\"></form>" + "<%=recipes[j].getLabel()%></span></div>";
			
			<%}%>
		}
		
		<%int row = 1;
			for (int k = 0; k < numBusinesses; k++) {%>
			var bus = bs["<%=restaurants[k].getName()%>"];

			curr = document.getElementById("r"+"<%=row%>");
			temp = document.getElementById("r"+"<%=row%>"+"header");
			temp.innerHTML += "<h4>Because you liked "+ "<%=restaurants[k].getName()%>" + "</h4>";
		<%row++;%>
		bus.forEach(function(element) {

			curr.innerHTML += "<div class=\"col elementBlock\" style=\"display:inline-block;float:none;\"><form id=\"details\" action=\"Detail\"><input type=\"submit\" class= \"image thumb\"name= \"restaurant\" value=\""
					+ element["id"]
					+ "\" style=\"background-image: url('"
					+ element["image_url"]
					+ "');border-radius:17px; color:rgba(0,0,0,0);\"><span class=\"name\"></form>"
					+ element["name"] + "</span></div>";
	
			});
		<%}%>
		
		<%
		for (int k = 0; k < numRecipes; k++) {%>
		var rec = rs["<%=recipes[k].getLabel()%>"];

		curr = document.getElementById("r"+"<%=row%>");
		temp = document.getElementById("r"+"<%=row%>"+"header");
		temp.innerHTML += "<h4>Because you liked "+ "<%=recipes[k].getLabel()%>" + "</h4>";
		<%row++;%>
		rec.forEach(function(element) {

		curr.innerHTML += "<div class=\"col elementBlock\" style=\"display:inline-block;float:none;\"><form id=\"details\" action=\"Detail\"><input type=\"submit\" class= \"image thumb\"name= \"recipe\" value=\""
				+ element["uri"]
				+ "\" style=\"background-image: url('"
				+ element["image"]
				+ "');border-radius:17px; color:rgba(0,0,0,0);\"><span class=\"name\"></form>"
				+ element["label"] + "</span></div>";

		});
	<%}%>
	}
</script>
</head>
<body onload="profile(); loadResults();">

	<div class="container-fluid mycontainer">

		<div class="header">
			<div class="row align-items-center">
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
					<form action="ServletLogger" method="GET" id="buttonLog"></form>
				</div>
			</div>

		</div>

		<div class="main" id="main"></div>
	</div>

	<div id="restaurantDiv">
		<div class="scrollmenu">
			<div id="favorites"></div>
		</div>
	</div>

</body>
</html>