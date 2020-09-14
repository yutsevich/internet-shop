<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>

<table border="1">
    <tr>
        <th>Id</th>
        <th>UserId</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.userId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/delete?id=${order.userId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
