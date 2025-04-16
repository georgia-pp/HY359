/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetOwnersTable;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.PetOwner;

/**
 *
 * @author georgia
 */
@WebServlet(name = "EditOwner", urlPatterns = {"/EditOwner"})
public class EditOwner extends HttpServlet {

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
        EditPetOwnersTable ep = new EditPetOwnersTable();

        try {
            PetOwner owner = gson.fromJson(user_data, PetOwner.class);
            String username = owner.getUsername();
            if (owner.getPassword() != null) {
                ep.updatePetOwner(username, "password", owner.getPassword());
            }
            if (owner.getLastname() != null) {
                ep.updatePetOwner(username, "lastaname", owner.getLastname());
            }
            if (owner.getBirthdate() != null) {
                ep.updatePetOwner(username, "birthdate", owner.getBirthdate());
            }
            if (owner.getGender() != null) {
                ep.updatePetOwner(username, "gender", owner.getGender());
            }
            if (owner.getJob() != null) {
                ep.updatePetOwner(username, "job", owner.getJob());
            }
            if (owner.getCity() != null) {
                ep.updatePetOwner(username, "city", owner.getCity());
            }
            if (owner.getAddress() != null) {
                ep.updatePetOwner(username, "address", owner.getAddress());
            }
            if (owner.getCountry() != null) {
                ep.updatePetOwner(username, "country", owner.getCountry());
            }
            if (owner.getTelephone() != null) {
                ep.updatePetOwner(username, "telephone", owner.getTelephone());
            }
            if (owner.getPersonalpage() != null) {
                ep.updatePetOwner(username, "personalpage", owner.getPersonalpage());
            }

            response.getWriter().write("PetOwner updated");
            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_OK);
        } catch (ClassNotFoundException e) {
            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(EditOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
