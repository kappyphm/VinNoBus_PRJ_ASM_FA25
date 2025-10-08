/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package BusModule.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import BusModule.services.BusServices;
import java.sql.*;

/**
 *
 * @author Admin
 */
@WebServlet(name = "BusAssignServlet", urlPatterns = {"/BusAssignServlet"})
public class BusAssignServlet extends HttpServlet {

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
            out.println("<title>Servlet BusAssignServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BusAssignServlet at " + request.getContextPath() + "</h1>");
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
        String busIdStr = request.getParameter("busId");
        if (busIdStr != null && !busIdStr.isEmpty()) {
            request.setAttribute("busId", busIdStr);
        }
        request.getRequestDispatcher("WEB-INF/Bus/BusAssign.jsp").forward(request, response);
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
        try {
            int busId = Integer.parseInt(request.getParameter("busId"));
            int tripId = Integer.parseInt(request.getParameter("tripId"));

            boolean success = busServices.assignBusToTrip(busId, tripId);

            if (success) {
                response.sendRedirect("BusListServlet?msg=AssignSuccess");
            } else {
                response.sendRedirect("BusListServlet?msg=AssignFail");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("BusListServlet?msg=InvalidInput");
        } catch (Exception e) {
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
        return "Servlet gán xe buýt cho chuyến";
    }// </editor-fold>

}
