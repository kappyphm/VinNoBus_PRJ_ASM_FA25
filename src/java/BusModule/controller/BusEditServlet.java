/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package BusModule.controller;

import BusModule.model.Bus;
import BusModule.services.BusServices;
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
@WebServlet(name = "BusEditServlet", urlPatterns = {"/BusEditServlet"})
public class BusEditServlet extends HttpServlet {

    private BusServices busServices;

    @Override
    public void init() throws ServletException {
        busServices = new BusServices();
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
            out.println("<title>Servlet BusEditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BusEditServlet at " + request.getContextPath() + "</h1>");
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
        String idStr = request.getParameter("id");
        if ((idStr == null) || idStr.isEmpty()) {
            response.sendRedirect("BusListServlet");
            return;
        }
        try {
            int busId = Integer.parseInt(idStr);
            Bus bus = busServices.getBusById(busId);
            if (bus == null) {
                response.sendRedirect("BusListServlet");
                return;
            }

            request.setAttribute("bus", bus);
            request.getRequestDispatcher("WEB-INF/Bus/BusForm.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("BusListServlet");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String busIdStr = request.getParameter("busId");
        String plateNumber = request.getParameter("plateNumber");
        String capacityStr = request.getParameter("capacity");
        try {
            int busId = Integer.parseInt(busIdStr);
            int capacity = Integer.parseInt(capacityStr);
            Bus updatedBus = new Bus(busId, plateNumber, capacity);
            busServices.updateBus(updatedBus);
            response.sendRedirect("BusListServlet");
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "ID và sức chứa phải là số hợp lệ!");
            request.getRequestDispatcher("WEB-INF/Bus/BusForm.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet sửa thông tin xe buýt";
    }// </editor-fold>

}
