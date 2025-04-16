/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author georgia
 */
@WebServlet(name = "DeleteKeeper", urlPatterns = {"/DeleteKeeper"})
public class DeleteKeeper extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keeper_id = request.getParameter("keeper_id");
        if (keeper_id != null) {
            EditPetKeepersTable eut = new EditPetKeepersTable();
            try {
                eut.deleteKeeper(keeper_id);
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
