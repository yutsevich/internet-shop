<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/shopping-carts/products/add">
    Enter product name please: <input type="text" name="name" required placeholder="name"> <br>
    Enter product price please: <input type="number" name="price" required placeholder="price"> <br>

    <button type="submit">Add</button>
</form>
</body>
</html>
