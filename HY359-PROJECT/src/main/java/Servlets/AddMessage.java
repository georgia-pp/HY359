/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditBookingsTable;
import DataBase.Tables.EditMessagesTable;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import mainClasses.JSON_Converter;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Booking;
import mainClasses.Message;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AddMessage", urlPatterns = {"/AddMessage"})
public class AddMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Servlet Site:");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JSON_Converter jsonRequest = new JSON_Converter();
        String user_data = jsonRequest.getJSONFromAjax(request.getReader());

        System.out.println("User Data: " + user_data);
        EditMessagesTable ep = new EditMessagesTable();
        EditBookingsTable su = new EditBookingsTable();

        try {
            Message message = gson.fromJson(user_data, Message.class);

            int booking_id = message.getBooking_id();
            Booking book = su.databaseToBooking(booking_id);
            if (book != null) {
                if ("accepted".equals(book.getStatus())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    String currentTimestamp = dateFormat.format(now);

                    message.setDatetime(currentTimestamp);
                    ep.createNewMessage(message);
                    response.getWriter().write("Message added");
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(AddMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
