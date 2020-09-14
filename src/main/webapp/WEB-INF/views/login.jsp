<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h4 style="color: red">${errorMsg}</h4>

<form method="post" action="${pageContext.request.contextPath}/login">
    Enter your login please: <input type="text" name="login" required placeholder="login"> <br>
    Enter your password please: <input type="password" name="psw" required placeholder="password"> <br>

    <button type="submit">Login</button>
</form>

</body>
</html>
