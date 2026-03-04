<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Ocean View Resort</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 40px;
        }

        .box {
            max-width: 500px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }

        h2 {
            margin-top: 0;
        }

        a {
            display: block;
            margin: 10px 0;
            padding: 10px;
            text-decoration: none;
            background-color: #e9ecef;
            color: #333;
            border-radius: 4px;
        }

        a:hover {
            background-color: #0d6efd;
            color: white;
        }

        .logout {
            margin-top: 15px;
            background-color: #dc3545;
            color: white;
        }

        .logout:hover {
            background-color: #bb2d3b;
        }

    </style>
</head>
<body>

<div class="box">

    <h2>Welcome, ${sessionScope.username}</h2>

    <hr>

    <a href="addReservation.jsp">Add New Reservation</a>
    <a href="viewReservation.jsp">View Reservation</a>
    <a href="help.jsp">Help Section</a>
    <a href="logout" class="logout">Logout</a>

</div>

</body>
</html>