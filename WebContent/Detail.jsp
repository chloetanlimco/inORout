<%@ page import ="inORout.Business" %>
<%@ page import ="inORout.Recipe" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Detail</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="detail.css" /> 
  <link rel="shortcut icon" href="#">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <link href="https://fonts.googleapis.com/css?family=Lustria&display=swap" rel="stylesheet">
  <script>
	
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
	
  function loadResults()
	{
		
		if ("<%=request.getAttribute("detail-error")%>"=="null"){
			document.getElementById("error").innerHTML = "<%=request.getAttribute("detail-error")%>";
		}
		else {
		
			<% 
			Business business = null;
			Recipe recipe = null;
			boolean b = false;
			boolean r = false;
			if (request.getAttribute("business")!=null){
				business = (Business) request.getAttribute("business");
				b = true;
			}
			else if (request.getAttribute("recipe")!=null){
				recipe = (Recipe) request.getAttribute("recipe");
				r = true;
			}
			System.out.println(b);
			System.out.println(r);
			boolean fav = (boolean) request.getAttribute("favorite");%>
			
			let recDetailsDiv = document.getElementById("detailsDiv1");
			let busDetailsDiv = document.getElementById("detailsDiv2");
			
			var bizz = '${business}';
			var rec = '${recipe}';
			var rec = "<%=request.getAttribute("recipe")%>";
			var res = "<%=request.getAttribute("restaurant")%>";
			
			<% if (fav){System.out.println("IM IN FAVORITES");}
			else {System.out.println("I AM NOT IN FAVORITES");}
			if(request.getAttribute("recipe") == null) //BUSINESS
			{%>
			
				var busDetails="";
				
				busDetails += " <div class=\"col-sm-5\" id = \"picture\">";
				busDetails += "<img src=\""+"<%=business.getImageUrl()%>"
					+"\" alt=\"" + "<%=business.getName()%>" +"\" style=\"border-radius:17px;width:100%;\"></div>";
				
	
				
					
					
					var stars = "<%=(int)business.getRating()%>";
		   			var rate ="";
		       		for(var j=0; j < stars; j++)
		       		{
		       			rate += "<img class= \"star\" src=\"redstar.png\">"; 
		       		}
		       		for(var k=0; k<5-stars; k++){
		       			rate += "<img class= \"star\" src=\"greystar.png\">";
		       		}
				
					
				busDetails += "<div class=\"col-sm-3\" id = \"det\">";
				busDetails += "<div style=\"font-weight:bold;font-size:200%\">"+"<%=business.getName()%></div>";
				busDetails+="<br> "+rate+"<%=business.getReviewCount()%>"+" Reviews <br><br>  "+"<%=business.getPrice()%>"+"<br><br>";
				
				busDetails +="Categories: "+"<%=business.getCategories()%>"+"<br>";
				
				<% if (session.getAttribute("Current user")!=null && !fav){
				System.out.println("in the if statement");%>
				busDetails+= "<br><br><form id=\"myform\">";
				busDetails += "<input type=\"hidden\" id=\"keyid\" value=\""+"<%=business.getId()%>"+"\"></input>";
				busDetails+= "<input id = \"favButton\" type =\"submit\" value = \"Add to favorites\"></input></form>";
			<%}
			else if (session.getAttribute("Current user")!=null){
				System.out.println("in the else statement");%>
				busDetails+= "<br><br><form id=\"myform\">";
				busDetails += "<input type=\"hidden\" id=\"keyid\" value=\""+"<%=business.getId()%>"+"\"></input>";
				busDetails+= "<input id = \"favButton\" type =\"submit\" value = \"Remove from favorites\"></input></form>";
			<%}%>
				
				busDetails += "</div>";
				
	       		//busDetails += " <div class=\"col-sm-4\">" + rate + "<br>" +
				
				
				//busDetails += "<div class=\"col-sm-6\" id = \"address\">";
				
				busDetails += "<div class=\"col-sm-3\" id = \"det1\">";
				busDetails +="Distance: "+"<%=Math.abs(business.getDistance())%>"+" miles<br><br>";
				
				busDetails += "Address: "+"<%=business.getDisplayAddress().substring(0,business.getDisplayAddress().indexOf("\n"))%>"+"<br><br>";
				
				busDetails +="<img class= \"star\" src=\"phone.png\">"+"<%=business.getDisplayPhone()%>";
				
				
				busDetails +="</div>";
				
				busDetailsDiv.innerHTML+=busDetails;
				
				<%System.out.println("business id: "+business.getId());%>
	
			<%}
			else {//RECIPE%>
				var recipeDetails="";
				recipeDetails += "<div class=\"col-sm-4\" id = \"picture\">";
				recipeDetails += "<a id=\"recipePic\" href=\""+"<%=recipe.getUrl()%>"+"\"><img src=\""+"<%=recipe.getImage()%>" 
					+"\" alt=\"" + "<%=recipe.getLabel()%>" +"\" style=\"border-radius:17px;width:100%;\"></a></div>";
				
				recipeDetails += "<div class=\"col-sm-3\" id = \"ingredients\">";
				recipeDetails += "<div style=\"font-weight:bold;font-size:200%\">"+"<%=recipe.getLabel()%>"+"</div>";
				recipeDetails +="<br>Ingredients: <br>"
				
				<%
				if (r){
					String[] in = recipe.getIngredientLines();
					for (int i=0; i<in.length; i++){%>
					recipeDetails += "  - "+"<%=in[i]%>"+"</br>";
					<%}
				}%>
				recipeDetails += "</div>";
				
				
				recipeDetails += "<div class=\"col-sm-3\" id = \"moredetails\">";
				//recipeDetails+="Ingredients: <br>";
				
				recipeDetails+="<br>Calories per serving: "+"<%=(int) recipe.getCalories()%>"+"<br>";
				recipeDetails+="<br>Number of servings: "+"<%=recipe.getServings()%>"+"<br>";
				recipeDetails+="<br><br>This recipe is: <br>";
				
				<%
				if (r){
					String[] health = recipe.getHealthLabels();
					for (int i=0; i<health.length; i++){%>
					recipeDetails += " - "+"<%=health[i]%>"+"</br>";
					<%}
				}%>
				
				recipeDetails+="<br>Health Cautions: <br>";
				<%
				if (r){
					String[] cautions = recipe.getCautions();
					for (int i=0; i<cautions.length; i++){%>
					recipeDetails += "  - "+"<%=cautions[i]%>"+"</br>";
					<%}
				}%>
				
				<% if (session.getAttribute("Current user")!=null && !fav){%>
				recipeDetails+= "<br><form id=\"myform\">";
				recipeDetails += "<input type=\"hidden\" id=\"keyid\" value=\""+"<%=recipe.getUri()%>"+"\"></input>";
				recipeDetails+= "<input id = \"favButton\" type =\"submit\" value = \"Add to favorites\"></input></form>";
			<%}
			else if (session.getAttribute("Current user")!=null){%>
				recipeDetails+= "<br><form id=\"myform\">";
				recipeDetails += "<input type=\"hidden\" id=\"keyid\" value=\""+ "<%=recipe.getUri()%>"+"\"></input>";
				recipeDetails+= "<input id = \"favButton\" type =\"submit\" value = \"Remove from favorites\"></input></form>";
			<%}%>
				recipeDetails +="</div>";
					
				
				
				recipeDetails +="</div>";
				
				recDetailsDiv.innerHTML+=recipeDetails;
				
				<%System.out.println("recipe id: "+recipe.getUri());
				System.out.println(recipe.getHealthLabels());%>

			<%}
			%>
			 
		}
		<%System.out.println("r is " + r);%>
		document.getElementById("myform").onsubmit = function(e){
			e.preventDefault();
			console.log("good");
			$.ajax({
				method : "GET",
				url : "AddRemFav?",
				data : {
					recipe: "<%=r == true ? "true" : "false"%>",
					id: e.target.firstElementChild.value
				},
			success: function(result){
				if($("#favButton").val() === "Remove from favorites"){
					$("#favButton").val("Add to favorites");
				}else{
					$("#favButton").val("Remove from favorites");
				}
			}
			});
			
			return false;
		};
		console.log(<%= request.getAttribute("favorite")%> + "mycode")
		
	}
	</script>
</head>
<body onload="profile(); loadResults(); ">
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
					<form action="ServletLogger" id="buttonLog"></form>
				</div>
			</div>
		</div>
	</div>
	<div id = "detailsDiv1"></div>
	<div id = "detailsDiv2"></div>

</body>
</html>