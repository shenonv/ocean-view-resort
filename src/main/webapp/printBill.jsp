<%@ page import="com.oceanview.model.Reservation" %>
<html>
<head>
  <title>Print Bill</title>
</head>
<body>

<h2>Ocean View Resort - Invoice</h2>

<%
  Reservation res = (Reservation) request.getAttribute("reservation");
  if (res != null) {
%>

Reservation ID: ${reservationId} <br>
Guest Name: <%= res.getGuestName() %><br>
Room Type: <%= res.getRoomType() %><br>
Check-in Date: <%= res.getCheckInDate() %><br>
Check-out Date: <%= res.getCheckOutDate() %><br>

<hr>

<button onclick="window.print()">Print</button>

<%
} else {
%>
<p style="color:red;">Reservation not found.</p>
<%
  }
%>

<br><br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>