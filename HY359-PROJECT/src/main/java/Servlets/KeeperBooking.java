/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditBookingsTable;
import DataBase.Tables.EditPetKeepersTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Booking;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "KeeperBooking", urlPatterns = {"/KeeperBooking"})
public class KeeperBooking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        try (PrintWriter out = response.getWriter()) {
            EditPetKeepersTable eut = new EditPetKeepersTable();
            EditBookingsTable su = new EditBookingsTable();
            PetKeeper keeper = eut.databaseToPetKeepers(username, password);
            int id = keeper.getKeeper_id();
            Booking book = su.getRequest(id);
            if (book == null) {
                response.setStatus(403);
            } else {

                String json = su.bookingToJSON(book);
                out.println(json);
                response.setStatus(200);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginKeeper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
