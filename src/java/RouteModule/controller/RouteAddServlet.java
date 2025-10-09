/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package RouteModule.controller;

import RouteModule.model.Route;
import RouteModule.services.RouteServices;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RouteAddServlet", urlPatterns = {"/RouteAddServlet"})
public class RouteAddServlet extends HttpServlet {

    private RouteServices routeServices;

    @Override
    public void init() throws ServletException {
        routeServices = new RouteServices();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RouteAddServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RouteAddServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/Route/RouteAdd.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override  // Xử lý thêm tuyến mới (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String routeName = request.getParameter("route_name");
        String type = request.getParameter("type");
        String frequencyStr = request.getParameter("frequency");
        String message;
        try {
            // Kiểm tra hợp lệ
            if (routeName == null || routeName.isEmpty()
                    || type == null || type.isEmpty()
                    || frequencyStr == null || frequencyStr.isEmpty()) {
                message = "Vui lòng nhập đầy đủ thông tin!";
                request.setAttribute("message", message);
                request.getRequestDispatcher("WEB-INF/Route/RouteAdd.jsp").forward(request, response);
                return;
            }
            int frequency = Integer.parseInt(frequencyStr);

            // Tạo đối tượng Route
            Route route = new Route();
            route.setRouteName(routeName);
            route.setType(type);
            route.setFrequency(frequency);

            // Gọi service để thêm vào DB
            boolean success = routeServices.addRoute(route);
            if (success) {
                message = "Thêm tuyến đường thành công!";
            } else {
                message = "Không thể thêm tuyến đường. Vui lòng thử lại.";
            }
            request.setAttribute("message", message);
            request.getRequestDispatcher("WEB-INF/Route/RouteAdd.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            message = "Tần suất phải là số nguyên!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("WEB-INF/Route/RouteAdd.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi SQL khi thêm tuyến đường", e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
