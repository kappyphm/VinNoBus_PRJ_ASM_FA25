<%--
    Document   : BusAssign
    Created on : Sep 30, 2025
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Assign Bus</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .form-container {
                width: 400px;
                margin: auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
            }
            label {
                display: block;
                margin-top: 10px;
            }
            select, button {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
            }
            .msg {
                margin-top: 15px;
                font-weight: bold;
                text-align: center;
            }
            .success {
                color: green;
            }
            .fail {
                color: red;
            }
        </style>
    </head>
    <body>

        <div class="form-container">
            <h2 style="text-align:center;">Assign Bus to Trip</h2>

            <!-- Hiển thị thông báo success/fail -->
            <c:if test="${not empty param.msg}">
                <p class="msg">
                    <c:if test="${not empty param.msg}">
                    <p class="msg">
                        <c:choose>
                            <c:when test="${param.msg == 'AssignSuccess'}">
                                <span class="success">Assigned successfully!</span>
                            </c:when>
                            <c:when test="${param.msg == 'AssignFail'}">
                                <span class="fail">Assignment failed!</span>
                            </c:when>
                            <c:when test="${param.msg == 'InvalidInput'}">
                                <span class="fail">Invalid input!</span>
                            </c:when>
                        </c:choose>
                    </p>
                </c:if>

            </p>
        </c:if>

        <!-- Form gán xe -->
        <c:if test="${not empty param.msg}">
            <p class="msg">
                <c:choose>
                    <c:when test="${param.msg == 'AssignSuccess'}">
                        <span class="success">Assigned successfully!</span>
                    </c:when>
                    <c:when test="${param.msg == 'AssignFail'}">
                        <span class="fail">Assignment failed!</span>
                    </c:when>
                    <c:when test="${param.msg == 'InvalidInput'}">
                        <span class="fail">Invalid input!</span>
                    </c:when>
                </c:choose>
            </p>
        </c:if>

        <br/>
        <a href="BusListServlet">Back to Bus List</a>
    </div>

</body>
</html>

