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

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georgia
 */
@WebServlet(name = "Income", urlPatterns = {"/Income"})
public class Income extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try (PrintWriter out = response.getWriter()) {
            EditBookingsTable c = new EditBookingsTable();

            int sum = c.total_income();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String total = String.valueOf(sum);
            System.out.println(total);
            response.getWriter().write(total);

            response.setStatus(200);

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
