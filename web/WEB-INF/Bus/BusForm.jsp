<%-- 
    Document   : BusForm
    Created on : Sep 30, 2025
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:choose>
                <c:when test="${bus != null}">Edit Bus</c:when>
                <c:otherwise>Add New Bus</c:otherwise>
            </c:choose>
        </title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            h1 {
                text-align: center;
            }
            form {
                width: 400px;
                margin: auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                box-shadow: 2px 2px 8px #aaa;
            }
            label {
                display: inline-block;
                width: 120px;
            }
            input {
                width: 220px;
                padding: 5px;
                margin-bottom: 10px;
            }
            button {
                padding: 5px 15px;
                margin-top: 10px;
            }
            .msg {
                text-align: center;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .success {
                color: green;
            }
            .fail {
                color: red;
            }
            a {
                display: block;
                text-align: center;
                margin-top: 20px;
                text-decoration: none;
                color: #007bff;
            }
            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>
            <c:choose>
                <c:when test="${bus != null}">Edit Bus</c:when>
                <c:otherwise>Add New Bus</c:otherwise>
            </c:choose>
        </h1>

        <!-- Hiển thị thông báo success/fail -->
        <c:if test="${param.msg == 'plateExists'}">
            <p style="color:red;">Biển số đã tồn tại, vui lòng nhập biển số khác!</p>
        </c:if>

        <form action="${bus != null ? 'BusEditServlet' : 'BusAddServlet'}" method="post">
            <input type="hidden" name="busId" value="${bus != null ? bus.busId : ''}" />

            <label>Plate Number:</label>
            <input type="text" name="plateNumber" value="${bus != null ? bus.plateNumber : ''}" required /><br/>

            <label>Capacity:</label>
            <input type="number" name="capacity" value="${bus != null ? bus.capacity : ''}" min="1" required /><br/>

            <button type="submit">
                <c:choose>
                    <c:when test="${bus != null}">Update</c:when>
                    <c:otherwise>Add</c:otherwise>
                </c:choose>
            </button>
        </form>

        <a href="BusListServlet">Back to Bus List</a>
    </body>
</html>

