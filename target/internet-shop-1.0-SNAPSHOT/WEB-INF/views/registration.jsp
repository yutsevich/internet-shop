<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Register!</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Enter your name please: <input type="text" name="name" required placeholder="name"> <br>
    Enter your login please: <input type="text" name="login" required placeholder="login"> <br>
    Enter your password please: <input type="password" name="psw" required placeholder="password"> <br>
    Repeat your password please: <input type="password" name="repeatedPsw" required placeholder="password"> <br>

    <button type="submit">Register</button>
</form>

</body>
</html>
