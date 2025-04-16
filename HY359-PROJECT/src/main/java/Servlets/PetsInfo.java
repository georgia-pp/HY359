/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import DataBase.Tables.EditPetsTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Pet;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "PetsInfo", urlPatterns = {"/PetsInfo"})
public class PetsInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            EditPetKeepersTable eut = new EditPetKeepersTable();
            EditPetsTable c = new EditPetsTable();
            PetKeeper su = eut.databaseToPetKeepers(username, password);

            if (su != null) {
                int keeper_id = su.getKeeper_id();
                Pet pet = c.petOfKeeper(String.valueOf(keeper_id));
                if (pet == null) {
                    response.setStatus(403);
                } else {
                    String json = c.petToJSON(pet);
                    out.println(json);
                    response.setStatus(200);
                }
            } else {
                // Handle the case when PetKeeper is null
                response.setStatus(403);
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
