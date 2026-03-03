<%@ page session="false" %>
<html>
<head>
    <title>Login - Ocean View Resort</title>
</head>
<body>

<h2>Ocean View Resort - Login</h2>

<form action="login" method="post">

    Username:
    <input type="text" name="username" required>
    <br><br>

    Password:
    <input type="password" name="password" required>
    <br><br>

    <input type="submit" value="Login">

</form>

<br>

<!-- Show error message -->
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
    }
%>

</body>
</html>