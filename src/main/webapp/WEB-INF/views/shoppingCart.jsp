<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Shopping cart</h1>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${shoppingCart.products}">
        <tr>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/order?userId=${shoppingCart.userId}">Complete order</a>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
