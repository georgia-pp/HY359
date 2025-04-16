/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditBookingsTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author georgia
 */
@WebServlet(name = "NewStatus", urlPatterns = {"/NewStatus"})
public class NewStatus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String borrowing_id = request.getParameter("borrowing_id");
        String petStatus = request.getParameter("petStatus");
        if (borrowing_id != null && petStatus != null) {
            EditBookingsTable eut = new EditBookingsTable();
            try {
                System.out.println("Borrowing id:" + borrowing_id);
                eut.updateBooking(borrowing_id, petStatus);
                response.setStatus(200);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.setStatus(400);
        }
    }

}
