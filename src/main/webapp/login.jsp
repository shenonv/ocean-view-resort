<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Ocean View Resort</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
        }

        .container {
            width: 350px;
            margin: 100px auto;
            padding: 25px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            width: 100%;
            padding: 8px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .error {
            color: red;
            font-size: 13px;
            margin-bottom: 10px;
        }

        .server-error {
            color: red;
            text-align: center;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>

    <script>
        function validateForm() {

            let isValid = true;

            let username = document.forms["loginForm"]["username"].value.trim();
            let password = document.forms["loginForm"]["password"].value.trim();

            document.getElementById("userError").innerHTML = "";
            document.getElementById("passError").innerHTML = "";

            // Username validation
            if (username === "") {
                document.getElementById("userError").innerHTML = "Username is required.";
                isValid = false;
            } else if (username.length < 4) {
                document.getElementById("userError").innerHTML = "Username must be at least 4 characters.";
                isValid = false;
            } else if (!/^[a-zA-Z0-9]+$/.test(username)) {
                document.getElementById("userError").innerHTML = "Username can contain only letters and numbers.";
                isValid = false;
            }

            // Password validation
            if (password === "") {
                document.getElementById("passError").innerHTML = "Password is required.";
                isValid = false;
            } else if (password.length < 6) {
                document.getElementById("passError").innerHTML = "Password must be at least 6 characters.";
                isValid = false;
            }

            return isValid;
        }
    </script>

</head>
<body>

<div class="container">

    <h2>Ocean View Resort Login</h2>

    <form name="loginForm" action="login" method="post"
          onsubmit="return validateForm();" novalidate>

        <label>Username:</label>
        <input type="text" name="username">
        <div id="userError" class="error"></div>

        <label>Password:</label>
        <input type="password" name="password">
        <div id="passError" class="error"></div>

        <br>
        <input type="submit" value="Login">

    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="server-error"><%= error %></div>
    <%
        }
    %>

</div>

</body>
</html>