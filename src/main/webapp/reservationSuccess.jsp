<%@ page import="com.oceanview.model.Reservation" %>
<html>
<head>
    <title>Reservation Success</title>
</head>
<body>

<h2>Reservation Added Successfully!</h2>

<h3>Reservation ID: ${reservationId}</h3>
<h3>Total Bill: LKR ${totalBill}</h3>

<hr>

<!-- 🔥 Print Bill Form -->
<form action="printBill" method="post">
    <input type="hidden" name="reservationId" value="${reservationId}">
    <input type="hidden" name="totalBill" value="${totalBill}">
    <input type="submit" value="Print Bill">
</form>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>