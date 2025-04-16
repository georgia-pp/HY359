/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetsTable;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import mainClasses.JSON_Converter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Pet;

/**
 *
 * @author georgia
 */
@WebServlet(name = "RegisterPet", urlPatterns = {"/RegisterPet"})
public class RegisterPet extends HttpServlet {

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
        EditPetsTable ep = new EditPetsTable();

        try {
            Pet pet = gson.fromJson(user_data, Pet.class);

            ep.addNewPet(pet);
            response.getWriter().write("Pet added");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterPet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
