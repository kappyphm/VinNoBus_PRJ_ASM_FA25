<%-- 
    Document   : BusDetail
    Created on : Sep 30, 2025
    Author     : Admin
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Bus Detail</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            h1 {
                text-align: center;
            }
            table {
                border-collapse: collapse;
                width: 50%;
                margin: auto;
                text-align: left;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ccc;
            }
            th {
                background-color: #f2f2f2;
            }
            .actions {
                text-align: center;
                margin-top: 20px;
            }
            .actions a {
                margin: 0 10px;
                text-decoration: none;
                color: #007bff;
            }
            .actions a:hover {
                text-decoration: underline;
            }
            .error {
                color: red;
                text-align: center;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <h1>Bus Detail</h1>

        <c:choose>

            <c:when test="${bus != null}">
                <table>
                    <tr>
                        <th>ID</th>
                        <td>${bus.busId}</td>
                    </tr>
                    <tr>
                        <th>Plate Number</th>
                        <td>${bus.plateNumber}</td>
                    </tr>
                    <tr>
                        <th>Capacity</th>
                        <td>${bus.capacity}</td>
                    </tr>
                </table>

                <div class="actions">
                    <a href="BusListServlet">Back to Bus List</a>
                    <a href="BusEditServlet?busId=${bus.busId}">Edit Bus</a>
                </div>
            </c:when>


            <c:otherwise>
                <p class="error">Bus not found!</p>
                <div class="actions">
                    <a href="BusListServlet">Back to Bus List</a>
                </div>
            </c:otherwise>
        </c:choose>

    </body>
</html>
