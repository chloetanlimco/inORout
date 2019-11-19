<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="inORout.Business" %>
<%@ page import ="inORout.Recipe" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="search" method="GET" action="Search">
		<input type="submit" name="submit" value="Search!">
	</form>
	<% Business[] list = (Business[]) request.getAttribute("YelpResults");
	if (list != null) {
		System.out.println(list[0].getId());
	}
	Recipe[] recipe = (Recipe[]) request.getAttribute("EdamamResults");
	if (recipe != null) {
		System.out.println(recipe.length);
	}%>
	
</body>
</html>
