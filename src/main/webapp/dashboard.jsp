<%@ page session="true" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<h2>Welcome, ${sessionScope.username}</h2>

<hr>

<h3>Menu</h3>

<a href="addReservation.jsp">Add New Reservation</a><br><br>
<a href="viewReservation.jsp">View Reservation</a><br><br>
<a href="logout">Logout</a>

</body>
</html>