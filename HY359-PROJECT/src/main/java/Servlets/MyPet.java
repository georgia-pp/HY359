/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetOwnersTable;
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
import mainClasses.PetOwner;


/**
 *
 * @author georgia
 */
@WebServlet(name = "MyPet", urlPatterns = {"/MyPet"})
public class MyPet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            EditPetOwnersTable eut = new EditPetOwnersTable();
            EditPetsTable c = new EditPetsTable();
            PetOwner su = eut.databaseToPetOwners(username, password);
            if (su != null) {
                int owner_id = su.getOwner_id();
                Pet pet = c.petOfOwner(String.valueOf(owner_id));
                if (pet == null) {
                    response.setStatus(403);
                } else {
                    String json = c.petToJSON(pet);
                    out.println(json);
                    response.setStatus(200);
                }
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
