/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetOwnersTable;
import DataBase.Tables.EditBookingsTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import mainClasses.PetOwner;

/**
 *
 * @author georgia
 */
@WebServlet(name = "FinishBooking", urlPatterns = {"/FinishBooking"})
public class FinishBooking extends HttpServlet {

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
            EditPetOwnersTable su = new EditPetOwnersTable();
            PetOwner owner = su.databaseToPetOwners(username, password);
            if (owner != null) {
                int id = owner.getOwner_id();
                EditBookingsTable eut = new EditBookingsTable();

                eut.updateBooking_byOwner(String.valueOf(id), "finished");
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
