/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetOwnersTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author georgia
 */
@WebServlet(name = "DeleteOwner", urlPatterns = {"/DeleteOwner"})
public class DeleteOwner extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String owner_id = request.getParameter("owner_id");
        if (owner_id != null) {
            EditPetOwnersTable eut = new EditPetOwnersTable();
            try {
                eut.deleteOwner(owner_id);
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
