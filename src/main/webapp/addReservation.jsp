<html>
<head>
  <title>Add Reservation</title>
</head>
<body>

<h2>Add New Reservation</h2>

<form action="addReservation" method="post">

  Guest Name:
  <input type="text" name="guestName" required><br><br>

  Address:
  <input type="text" name="address" required><br><br>

  Contact Number:
  <input type="text" name="contactNumber" required><br><br>

  Room Type:
  <select name="roomType">
    <option value="Standard">Standard</option>
    <option value="Deluxe">Deluxe</option>
    <option value="Suite">Suite</option>
  </select><br><br>

  Check-in Date:
  <input type="date" name="checkInDate" required><br><br>

  Check-out Date:
  <input type="date" name="checkOutDate" required><br><br>

  <input type="submit" value="Book Now">

</form>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>