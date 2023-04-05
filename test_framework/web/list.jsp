<%@page import="java.util.*" %>
<%@page import="etu2040.framework.app.modele.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> fs </title>
</head>
<body>
	<h1> Redirection </h1>
	<h3> Bienvenu blabla</h3>
	<% ArrayList<Emp> listEmp = (ArrayList<Emp>) request.getAttribute("listEmp"); 
        for (int i=0; i<listEmp.size(); i++) { %>
            <h5>
                <span> <%= listEmp.get(i).get_id() %> </span>
                <span> <%= listEmp.get(i).get_name() %> </span>
            </h5>
    <% } %>
</body>
</html>