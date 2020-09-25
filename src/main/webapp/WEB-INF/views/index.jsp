<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="/WEB-INF/views/header.jsp"%>
<h1>Internet shop</h1>
<a href="${pageContext.request.contextPath}/inject">Inject test data</a> <br>
<a href="${pageContext.request.contextPath}/registration">Registration</a> <br>
<a href="${pageContext.request.contextPath}/users/all">All users</a> <br>
<a href="${pageContext.request.contextPath}/products/add">Add product</a> <br>
<a href="${pageContext.request.contextPath}/products/all">All products</a> <br>
<a href="${pageContext.request.contextPath}/shopping-carts">Shopping Cart</a> <br>
<a href="${pageContext.request.contextPath}/orders/all">All orders</a> <br>
<a href="${pageContext.request.contextPath}/admin/products">All products (Admin)</a> <br>
<a href="${pageContext.request.contextPath}/logout">Logout</a> <br>
</body>
</html>
