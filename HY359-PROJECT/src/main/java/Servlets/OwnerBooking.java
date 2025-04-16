/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditBookingsTable;
import DataBase.Tables.EditPetOwnersTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Booking;
import mainClasses.PetOwner;

/**
 *
 * @author georgia
 */
@WebServlet(name = "OwnerBooking", urlPatterns = {"/OwnerBooking"})
public class OwnerBooking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        try (PrintWriter out = response.getWriter()) {
            EditPetOwnersTable eut = new EditPetOwnersTable();
            EditBookingsTable su = new EditBookingsTable();
            PetOwner owner = eut.databaseToPetOwners(username, password);
            int id = owner.getOwner_id();
            Booking book = su.getBooking(id);
            if (book == null) {
                response.setStatus(403);
            } else {

                String json = su.bookingToJSON(book);
                out.println(json);
                response.setStatus(200);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginOwner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
