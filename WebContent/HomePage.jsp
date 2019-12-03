<!DOCTYPE html>
<html lang="en">

<head>
<title>HomePage</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="homepage.css" />
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
			if ("<%=session.getAttribute("Current user")%>" == "null") {
				let el = document.createElement("input");
				el.type = "submit";
				el.name = "logChoice";
				el.value = "Login";
				el.className = "btn btn-default btn-lg mybtn";
				div.appendChild(el);

				let el2 = document.createElement("input");
				el2.type = "submit";
				el2.name = "logChoice";
				el2.value = "Signup";
				el2.className = "btn btn-default btn-lg mybtn";
				div.appendChild(el2);
			} else {
				let ell = document.createElement("input");
				ell.type = "submit";
				ell.name = "logChoice";
				ell.value = "Profile";
				ell.className = "btn btn-default btn-lg mybtn";
				div.appendChild(ell);

				let el = document.createElement("input");
				el.type = "submit";
				el.name = "logChoice";
				el.value = "Signout";
				el.className = "btn btn-default btn-lg mybtn";
				div.appendChild(el);
			}
		}
	</script>

</head>

<body onload="profile();">

	<div class="container-fluid fullwidth">

		<div class="header">
				<div class="row myrow">
					<div class="col-sm-6 d-inline-block">
						<button type="button" class="btn btn-default homeButton" style="pointer-events: none;"
							id="titleHome">in-or-out</button>
					</div>
					<div class="col-sm-6 d-inline-block">
						<div class="full">
							<form action="ServletLogger" method="GET" id="buttonLog"></form>
						</div>
					</div>
				</div>
		</div>
	</div>
	<div id="error_msg">
			<%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%>
		</div>
	<div class="mainBox">
		<div class="container-fluid searchSection">
		
			<form action="Search">
				<div class="row">
					<div class="col-sm-8">
						<div class="form-group">
							<input type="text" class="form-control mytextinput" id="foodSearch"
								placeholder="Find fries, sushi, pizza..." name="searchTerm">
						</div>
					</div>
					<div class="col-sm-4">
						<input type="submit" class="btn btn-default searchButton myinput"
							name="searchType" value="Search by Restaurant"> <input
							type="submit" class="btn btn-default searchButton myinput"
							name="searchType" value="Search by Recipe">
					</div>
				</div>
			</form>
		</div>
		
	</div>

	<script type="text/javascript"
		src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script type="text/javascript" src="jquery.js"></script>
	<script>
	
	if("<%=session.getAttribute("longitude")%>" == "null"){
	
		if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(function (p) {
			    	$.ajax({
	                method: "POST",
	                url: "SetLocation?",
	                data: {
	                    longitude: p.coords.longitude,
	                    latitude: p.coords.latitude,
	                }
	            });

		    });
		}
	}


</script>
</body>

</html>