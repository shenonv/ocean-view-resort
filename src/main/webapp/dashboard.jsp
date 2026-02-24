<%@ page session="true" %>
<!DOCTYPE html>
<html>
<body>
<h2>Welcome to Ocean View Resort Management System</h2>
<p>Logged in as: <strong><%= session.getAttribute("username")%></strong></p>
<ul>
    <li><a href="addReservation.jsp">Add New Reservation</a></li>
    <li><a href="viewReservations.jsp">Display Reservation Details</a></li>
    <li><a href="logout">Exit System</a></li>
</ul>
</body>
</html>