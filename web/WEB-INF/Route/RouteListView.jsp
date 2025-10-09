<%-- 
    Document   : RouteListView
    Created on : Oct 9, 2025, 6:15:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách tuyến</title>
    </head>
    <body>
        <h1>Danh sách tuyến xe Bus</h1>

        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>ID</th>
                <th>Tên tuyến</th>
                <th>Loại</th>
                <th>Tần suất</th>
            </tr>
            <c:forEach var="r" items="${routeList}">
                <tr>
                    <td>${r.routeId}</td>
                    <td>${r.routeName}</td>
                    <td>${r.type}</td>
                    <td>${r.frequency}</td>
                </tr>
            </c:forEach>
        </table>

        <p style="color:red;">${error}</p>
    </body>
</html>
