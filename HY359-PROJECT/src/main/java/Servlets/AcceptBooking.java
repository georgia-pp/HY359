/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import DataBase.Tables.EditBookingsTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AcceptBooking", urlPatterns = {"/AcceptBooking"})
public class AcceptBooking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            EditPetKeepersTable su = new EditPetKeepersTable();
            PetKeeper keeper = su.databaseToPetKeepers(username, password);
            if (keeper != null) {
                int id = keeper.getKeeper_id();
                EditBookingsTable eut = new EditBookingsTable();

                eut.updateBooking(String.valueOf(id), "accepted");
                response.setStatus(200);

            } else {
                response.setStatus(400);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
