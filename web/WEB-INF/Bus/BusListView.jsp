<%-- 
    Document   : BusListView
    Created on : Sep 30, 2025, 4:38:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
     
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BUS LIST</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                width: 80%;
                margin: auto;
                border-collapse: collapse;
                text-align: center;
            }
            table, th, td {
                border: 1px solid #ccc;
                padding: 8px;
            }
            th a {
                text-decoration: none;
                color: inherit;
            }
            .actions {
                width: 80%;
                margin: 10px auto;
                text-align: right;
            }
            .paging {
                width: 80%;
                margin: 10px auto;
                text-align: center;
            }
            .paging a {
                margin: 0 5px;
                text-decoration: none;
                color: #007bff;
            }
            .paging b {
                margin: 0 5px;
            }
            .msg {
                width: 80%;
                margin: 10px auto;
                text-align: center;
                font-weight: bold;
            }
            .msg.success {
                color: green;
            }
            .msg.fail {
                color: red;
            }
        </style>
          <jsp:include page="/WEB-INF/Screen/Menu.jsp"/>
    </head>
    <body>
        <h1 style="text-align:center;">Danh sách xe Bus</h1>
        <!-- Hiển thị thông báo success/fail -->
        <c:if test="${not empty msg}">
            <div class="msg ${msgType}">
                ${msg}
            </div>
        </c:if>

        <!-- Form search -->
        <form action="BusSearchServlet" method="get" style="text-align:center; margin-bottom:10px;">
            Search biển số: <input type="text" name="search" value="${search}"/>
            <input type="hidden" name="sort" value="${sort}"/>
            <button type="submit">Tìm kiếm</button>
        </form>

        <c:if test="${not empty message}">
            <p style="color:red; text-align:center;">${message}</p>
        </c:if>
        <div class="actions">
            <a href="BusAddServlet">Thêm xe mới</a>
        </div>

        <!-- Bảng danh sách -->
        <table>
            <tr>
                <th><a href="BusListServlet?sort=bus_id&search=${search}">ID</a></th>
                <th><a href="BusListServlet?sort=plate_number&search=${search}">Biển số</a></th>
                <th><a href="BusListServlet?sort=capacity&search=${search}">Sức chứa</a></th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="bus" items="${busList}">
                <tr>
                    <td>${bus.busId}</td>
                    <td>${bus.plateNumber}</td>
                    <td>${bus.capacity}</td>
                    <td>
                        <a href="BusEditServlet?id=${bus.busId}">Sửa</a> |
                        <a href="BusDeleteServlet?id=${bus.busId}" onclick="return confirm('Bạn có chắc muốn xóa xe này?')">Xóa</a> |
                        <a href="BusAssignServlet?busId=${bus.busId}">Gán tuyến</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <!-- Phân trang -->
        <div class="paging">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <b>[${i}]</b>
                    </c:when>
                    <c:otherwise>
                        <a href="BusListServlet?page=${i}&search=${search}&sort=${sort}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </body>
</html>
