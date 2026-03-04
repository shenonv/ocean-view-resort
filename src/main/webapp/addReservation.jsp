<!DOCTYPE html>
<html>
<head>
  <title>Add Reservation</title>

  <script>
    function validateForm() {

      let isValid = true;

      let name = document.forms["resForm"]["guestName"].value.trim();
      let address = document.forms["resForm"]["address"].value.trim();
      let contact = document.forms["resForm"]["contactNumber"].value.trim();
      let checkIn = document.forms["resForm"]["checkInDate"].value;
      let checkOut = document.forms["resForm"]["checkOutDate"].value;

      document.getElementById("errorBox").innerHTML = "";

      // Guest name validation
      if (name.length < 3) {
        document.getElementById("errorBox").innerHTML =
                "Guest name must be at least 3 characters.";
        return false;
      }

      if (!/^[a-zA-Z ]+$/.test(name)) {
        document.getElementById("errorBox").innerHTML =
                "Guest name can contain only letters and spaces.";
        return false;
      }

      // Address validation
      if (address === "") {
        document.getElementById("errorBox").innerHTML =
                "Address cannot be empty.";
        return false;
      }

      // Contact validation (10 digits)
      if (!/^[0-9]{10}$/.test(contact)) {
        document.getElementById("errorBox").innerHTML =
                "Contact number must be exactly 10 digits.";
        return false;
      }

      // Date validation
      let today = new Date().toISOString().split("T")[0];

      if (checkIn < today) {
        document.getElementById("errorBox").innerHTML =
                "Check-in date cannot be in the past.";
        return false;
      }

      if (checkOut <= checkIn) {
        document.getElementById("errorBox").innerHTML =
                "Check-out date must be after check-in date.";
        return false;
      }

      return true;
    }
  </script>

</head>
<body>

<h2>Add New Reservation</h2>

<form name="resForm" action="addReservation" method="post"
      onsubmit="return validateForm();" novalidate>

  <table>

    <tr>
      <td>Guest Name:</td>
      <td><input type="text" name="guestName"></td>
    </tr>

    <tr>
      <td>Address:</td>
      <td><input type="text" name="address"></td>
    </tr>

    <tr>
      <td>Contact Number:</td>
      <td><input type="text" name="contactNumber"></td>
    </tr>

    <tr>
      <td>Room Type:</td>
      <td>
        <select name="roomType">
          <option value="Standard">Standard</option>
          <option value="Deluxe">Deluxe</option>
          <option value="Suite">Suite</option>
        </select>
      </td>
    </tr>

    <tr>
      <td>Check-in Date:</td>
      <td><input type="date" name="checkInDate"></td>
    </tr>

    <tr>
      <td>Check-out Date:</td>
      <td><input type="date" name="checkOutDate"></td>
    </tr>

    <tr>
      <td colspan="2" align="center">
        <br>
        <input type="submit" value="Book Now">
      </td>
    </tr>

  </table>

</form>

<p id="errorBox" style="color:red;"></p>

<br>
<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>