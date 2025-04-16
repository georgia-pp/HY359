/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "LoginKeeper", value = "/LoginKeeper")
public class LoginKeeper extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            EditPetKeepersTable eut = new EditPetKeepersTable();
            PetKeeper su = eut.databaseToPetKeepers(username, password);
            if (su == null) {
                response.setStatus(403);
            } else {
                String json = eut.petKeeperToJSON(su);
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
