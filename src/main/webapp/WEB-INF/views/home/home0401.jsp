<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0401</title>
</head>
<body>
	<h3>HOME0401</h3>
	
	<table border="1">
		<tr>
			<td>\${coin == 1000 && userId == "hongkd"}</td>
			<td>${coin == 1000 && userId == "hongkd"}</td>
		</tr>
		<tr>
			<td>\${coin == 1000 and userId eq "hongkd"}</td>
			<td>${coin == 1000 and userId eq "hongkd"}</td>
		</tr>
		<tr>
			<td>\${not empty member && userId eq "hongkd"}</td>
			<td>${not empty member && userId eq "hongkd"}</td>
		</tr>
		<tr>
			<td>\${! empty member && userId eq "hongkd"}</td>
			<td>${! empty member && userId eq "hongkd"}</td>
		</tr>
	</table>
</body>
</html>