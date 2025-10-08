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
@WebServlet(name = "BusAddServlet", urlPatterns = {"/BusAddServlet"})
public class BusAddServlet extends HttpServlet {

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
            out.println("<title>Servlet BusAddServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BusAddServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("WEB-INF/Bus/BusForm.jsp").forward(request, response);
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

        try {
            String plateNumber = request.getParameter("plateNumber");
            String capacityStr = request.getParameter("capacity");

            if (plateNumber == null || plateNumber.isEmpty()) {
                response.sendRedirect("BusAddServlet?msg=plateEmpty");
                return;
            }

            int capacity;
            try {
                capacity = Integer.parseInt(capacityStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("BusAddServlet?msg=invalidCapacity");
                return;
            }

            // Kiểm tra trùng biển số
            if (busServices.isPlateNumberExists(plateNumber)) {
                response.sendRedirect("BusAddServlet?msg=plateExists");
                return;
            }

            // Thêm mới xe
            Bus newBus = new Bus();
            newBus.setPlateNumber(plateNumber);
            newBus.setCapacity(capacity);

            busServices.addBus(newBus);
            response.sendRedirect("BusListServlet?msg=addSuccess");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi thêm xe: " + e.getMessage(), e);
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
