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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mainClasses.Booking;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "FinishedBookings", urlPatterns = {"/FinishedBookings"})
public class FinishedBookings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        EditPetKeepersTable su = new EditPetKeepersTable();
        EditBookingsTable eut = new EditBookingsTable();
        try {
            PetKeeper keeper = su.databaseToPetKeepers(username, password);
            int keeper_id = keeper.getKeeper_id();
            ArrayList<Booking> array = eut.GetFinished(keeper_id);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            List<String> bookingStrings = array.stream()
                    .map(Keepers -> (Keepers instanceof Booking) ? ((Booking) Keepers).toString() : Keepers.toString())
                    .collect(Collectors.toList());

            String jsonWithBooking = gson.toJson(bookingStrings);
            System.out.println(jsonWithBooking);
            response.getWriter().write(jsonWithBooking);
            response.setStatus(200);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


}
