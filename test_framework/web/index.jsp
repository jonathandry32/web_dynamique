<%@ page import='etu2040.framework.servlet.*' %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> fs </title>
</head>
<body>
	<h1> Page accueil vers meme servlet </h1>
	<form action="url/Emp-save" method="GET"> 
		Id :<input type="text" name="id" required> </input>
		Name :<input type="text" name="name" required> </input>
		<input type="submit" value="Insert"> </body>
	</form>
	<button> <a href="url/Emp-pist?idEmp=aiza&&ageEmp=15"> Voir ady </a> </button>
	<h1> Upload </h1>
	<form action="url/upload" method="POST" enctype="multipart/form-data"> 
		fichier :<input type="file" name="file" required> </input>
		<input type="submit" value="Upload"> </body>
	</form>
</body>
</html>