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
			boolean fav = (boolean) request.getAttribute("favorite");%>
			
			let recDetailsDiv = document.getElementById("detailsDiv1");
			let busDetailsDiv = document.getElementById("detailsDiv2");
			
			var bizz = '${business}';
			var rec = '${recipe}';
			var rec = "<%=request.getAttribute("recipe")%>";
			var res = "<%=request.getAttribute("restaurant")%>";
			
			<% if(request.getAttribute("recipe") == null) //BUSINESS
			{%>
			
				var busDetails="";
				
				busDetails += " <div class=\"col-sm-4\" id = \"picture\">";
				busDetails += "<img src=\""+"<%=business.getImageUrl()%>" 
					+"\" alt=\"" + "<%=business.getName()%>" +"\" style=\"border-radius:17px;\"></div>";
				
				busDetails += "<div class=\"col-sm-6\" id = \"det\">";
				busDetails += "<div style=\"font-weight:bold;font-size:200%\">"+"<%=business.getName()%></div>";
				busDetails+="<br>Rating: "+"<%=business.getRating()%>"+"<br>Price: "+"<%=business.getPrice()%>"+"<br>";
				
				//busDetails += "<div class=\"col-sm-6\" id = \"address\">";
				
				busDetails += "<%=business.getDisplayAddress().substring(0,business.getDisplayAddress().indexOf("\n"))%>"+"<br>";
				busDetails += "<%=business.getDisplayAddress().substring(business.getDisplayAddress().indexOf("\n")+1)%>"+"<br>";
				
				busDetails +="<%=business.getDisplayPhone()%>";
				
				<% if (session.getAttribute("Current user")!=null){%>
					busDetails+= "<br><br><form action = \"AddRemFav\" method = \"GET\">";
					busDetails += "<input type=\"hidden\" name=\"restaurant\" value=\""+"<%=business.getId()%>"+"\" />";
					busDetails+= "<input id = \"favButton\" type =\"submit\" value = \"FAVORITE\"></form>";
				<%}%>
				
				busDetails +="</div></div>";
				
				busDetailsDiv.innerHTML+=busDetails;
	
			<%}
			else {//RECIPE%>
				var recipeDetails="";
				recipeDetails += " <div class=\"col-sm-4\" id = \"picture\">";
				recipeDetails += "<a id=\"recipePic\" href=\""+"<%=recipe.getUrl()%>"+"\"><img src=\""+"<%=recipe.getImage()%>" 
					+"\" alt=\"" + "<%=recipe.getLabel()%>" +"\" style=\"border-radius:17px;\"></a></div>";
				
				recipeDetails += "<div class=\"col-sm-6\" id = \"ingredients\">";
				recipeDetails += "<div style=\"font-weight:bold;font-size:200%\">"+"<%=recipe.getLabel()%>"+"</div>";
				recipeDetails+="<br>Calories: "+"<%=recipe.getCalories()%>"+"<br><br>Ingredients: <br>";
				
				<%
				if (r){
					String[] in = recipe.getIngredientLines();
					for (int i=0; i<in.length; i++){%>
					recipeDetails += "  - "+"<%=in[i]%>"+"</br>";
					<%}
				}%>
				
					
				<% if (session.getAttribute("Current user")!=null){%>
					recipeDetails+= "<br><form action = \"AddRemFav\" method = \"GET\">";
					recipeDetails += "<input type=\"hidden\" name=\"recipe\" value=\""+"<%=recipe.getUri()%>"+"\" />";
					recipeDetails+= "<input id = \"favButton\" type =\"submit\" value = \"FAVORITE\"></form>";
				<%}%>
				
				recipeDetails +="</div>";
				
				recDetailsDiv.innerHTML+=recipeDetails;

			<%}
			%>
			
			 
		}
		
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
		  <form action="Search">
		  <div class="col-sm-3">
		    <div class="form-group">
		      <input type="text" class="form-control" id="foodSearch" placeholder="Find fries, sushi, pizza..." name="searchTerm">
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
  	<div class="main">
  	<div id="error"></div>
		<div class="row">
	    	<div id="mainBlock">
	    	
	    	<div class="tab-content">
		    <div id="detailsDiv1">
		    <div id="detailsDiv2">
		    
		    
		    
		    </div>

		    </div>
	    	
	    	
	    	</div>
	    </div>
    </div>
	
</div>

</body>
</html>