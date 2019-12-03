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
		
		
				let busDetailsDiv = document.getElementById("detailsDiv1");
			
			
				var busDetails="";
				
				busDetails += " <div class=\"col-sm-5\" id = \"picture\">";
				busDetails += "<img src=\""+"homepagefood.png" 
					+"\" alt=\"" + "Five Guys" +"\" style=\"border-radius:17px;width:100%;\"></div>";
				
	
				
					
					
					var stars = 3;
		   			var rate ="";
		       		for(var j=0; j < stars; j++)
		       		{
		       			rate += "<img class= \"star\" src=\"redstar.png\">"; 
		       		}
		       		for(var k=0; k<5-stars; k++){
		       			rate += "<img class= \"star\" src=\"greystar.png\">";
		       		}
				
					
				busDetails += "<div class=\"col-sm-3\" id = \"det\">";
				busDetails += "<div style=\"font-weight:bold;font-size:200%\">"+"Five Guys</div>";
				busDetails+="<br>"+rate+"<br><br>  "+"   $"+"<br><br>";
				
				<%
				//if (b){
					String[] cat = {"American","Fast food","Burgers"};
					for (int i=0; i<cat.length-1; i++){%>
					busDetails += "<%=cat[i]%>"+",  ";
					<%}
				//}%>
				busDetails += "<%=cat[cat.length-1]%>"+"</br>";
				
				<%// if (session.getAttribute("Current user")!=null && !fav){%>
				busDetails+= "<br><br><form action = \"AddRemFav\" method = \"GET\">";
				busDetails += "<input type=\"hidden\" name=\"restaurant\" value=\""+"o"+"\"></input>";
				busDetails+= "<input id = \"favButton\" type =\"submit\" value = \"Add to favorites\"></input></form>";
			<%//}%>
				busDetails += "</div>";
				
	       		//busDetails += " <div class=\"col-sm-4\">" + rate + "<br>" +
				
				
				//busDetails += "<div class=\"col-sm-6\" id = \"address\">";
				
				busDetails += "<div class=\"col-sm-3\" id = \"det1\">";
				busDetails +="Distance: "+"3.6 miles"+"<br><br>";
				
				busDetails += "Address: "+"780 Beechwood Ave<br><br>";
				
				busDetails +="<img class= \"star\" src=\"phone.png\">"+"  1234567890";
				
				
				
				busDetails +="</div>";
				
				busDetailsDiv.innerHTML+=busDetails;
				
				
	
			
		
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