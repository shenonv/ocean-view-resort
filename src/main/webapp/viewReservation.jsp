<html>
<head>
    <title>View Reservation</title>
</head>
<body>

<h2>View Reservation</h2>

<form action="viewReservation" method="post">
    Enter Reservation ID:
    <input type="number" name="reservationId" required>
    <input type="submit" value="Search">
</form>

<hr>

<%
    String error = (String) request.getAttribute("errorMessage");
    if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
    }

    com.oceanview.model.Reservation res =
            (com.oceanview.model.Reservation)
                    request.getAttribute("reservation");

    if (res != null) {
%>

<h3>Reservation Details:</h3>
Guest Name: <%= res.getGuestName() %><br>
Address: <%= res.getAddress() %><br>
Contact: <%= res.getContactNumber() %><br>
Room Type: <%= res.getRoomType() %><br>
Check-in: <%= res.getCheckInDate() %><br>
Check-out: <%= res.getCheckOutDate() %><br>

<%
    }
%>
<br>
<br>
<a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>