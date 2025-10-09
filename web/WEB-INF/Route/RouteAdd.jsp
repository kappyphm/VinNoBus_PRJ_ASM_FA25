<%-- 
    Document   : RouteAdd
    Created on : Oct 8, 2025, 7:05:20 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm tuyến đường</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 30px;
            }
            form {
                width: 400px;
                margin: auto;
            }
            input, select, button {
                width: 100%;
                padding: 8px;
                margin: 8px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button {
                background-color: #007bff;
                color: white;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            .message {
                text-align: center;
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h1 style="text-align:center;">Thêm Tuyến Đường Mới</h1>
        <form action="RouteAddServlet" method="post">
            <label>Tên tuyến đường:</label>
            <input type="text" name="route_name" required>
            <label>Loại:</label>
            <select name="type">
                <option value="Bus">ROUND_TRIP</option>
                <option value="Train">CIRCULAR</option>
             
            </select>
            <label>Tần suất (chuyến/ngày):</label>
            <input type="number" name="frequency" min="1" required>
            <button type="submit">Thêm tuyến</button>
        </form>
        <div class="message">${message}</div>
        <div style="text-align:center; margin-top:10px;">
            <a href="RouteListServlet">← Quay lại danh sách</a>
        </div>
    </body>
</html>
