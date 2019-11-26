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
			
			let resF = document.getElementById("restaurantForm");
			
			var bizz = '${business}';
			var rec = '${recipe}';
			
			if (<%=b%>){
				resF.innerHTML += "B";
			}
			else if (<%=r%>){
				resF.innerHTML += " <div class=\"col-sm-4\" id = \"picture\"><input type=\"submit\" class= \"img-thumbnail image\"name= \"restaurant\" value=\"" 
					+ "<%=recipe.getSource()%>"+ "\" style=\"background-image: url('"+ 
					 "<%=recipe.getImage()%>" + "');border-radius:17px;\"></div>";
				resF.innerHTML+="<div class=\"col-sm-3\" id =res>"+"<%=recipe.getLabel()%>" +"</br>"+"Calories: "+"<%=recipe.getCalories()%>"+"</br>"+"Ingredients: "+"</br>";
				
				<%String[] in = recipe.getIngredientLines();
				for (int i=0; i<in.length; i++){%>
					resF.innerHTML += "<%=in[i]%>"+"</br>";
				<%}%>
			
				resF.innerHTML += "</div>";
				
			}
			
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
		    <div id="restaurantDiv">
		    <form id="restaurantForm" action="Detail">
		    </form>
		    
		    
		    </div>

		    </div>
	    	
	    	
	    	</div>
	    </div>
    </div>
	
</div>

</body>
</html>