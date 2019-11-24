<!DOCTYPE html>
<html lang="en">

<head>
	<title>HomePage</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="homepage.css" />
	<link rel="shortcut icon" href="#">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
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
	</script>

</head>

<body onload="profile();">

	<div class="container-fluid">

		<div class="header">
			<div class="container">
				<div class="row">
					<div class="col-sm-4">
						<button type="button" class="btn btn-default homeButton" id="titleHome">in-or-out</button>
					</div>
					<div class="col-sm-4"></div>
					<div class="col-sm-4">
						<form action="Logger" id="buttonLog">
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="jumbotron mainBox">
			<div class="container searchSection">
				<form action="Search">
					<div class="row">
						<div class="col-sm-8">
							<div class="form-group">
								<input type="text" class="form-control" id="foodSearch" placeholder="Find fries, sushi, pizza..." name="searchTerm">
							</div>
						</div>
						<div class="col-sm-4">
							<input type="submit" class="btn btn-default searchButton" name="searchType" value="Search by Restaurant">
							<input type="submit" class="btn btn-default searchButton" name="searchType" value="Search by Recipe">
						</div>
					</div>
				</form>
			</div>
			<div id="error_msg">
				<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
			</div>
		</div>

	</div>

</body>

</html>