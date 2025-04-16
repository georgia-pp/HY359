/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
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
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "EditKeeper", urlPatterns = {"/EditKeeper"})
public class EditKeeper extends HttpServlet {
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
        EditPetKeepersTable ep = new EditPetKeepersTable();

        try {
            PetKeeper keeper = gson.fromJson(user_data, PetKeeper.class);
            String username = keeper.getUsername();
            if (keeper.getPassword() != null) {
                ep.updatePetKeeper(username, "password", keeper.getPassword());
            }
            if (keeper.getLastname() != null) {
                ep.updatePetKeeper(username, "lastaname", keeper.getLastname());
            }
            if (keeper.getBirthdate() != null) {
                ep.updatePetKeeper(username, "birthdate", keeper.getBirthdate());
            }
            if (keeper.getGender() != null) {
                ep.updatePetKeeper(username, "gender", keeper.getGender());
            }
            if (keeper.getJob() != null) {
                ep.updatePetKeeper(username, "job", keeper.getJob());
            }
            if (keeper.getCity() != null) {
                ep.updatePetKeeper(username, "city", keeper.getCity());
            }
            if (keeper.getAddress() != null) {
                ep.updatePetKeeper(username, "address", keeper.getAddress());
            }
            if (keeper.getCountry() != null) {
                ep.updatePetKeeper(username, "country", keeper.getCountry());
            }
            if (keeper.getTelephone() != null) {
                ep.updatePetKeeper(username, "telephone", keeper.getTelephone());
            }
            if (keeper.getPersonalpage() != null) {
                ep.updatePetKeeper(username, "personalpage", keeper.getPersonalpage());
            }
            if (keeper.getProperty() != null) {
                ep.updatePetKeeper(username, "property", keeper.getProperty());
            }
            if (keeper.getPropertydescription() != null) {
                ep.updatePetKeeper(username, "propertydescription", keeper.getPropertydescription());
            }
            if (keeper.getCatkeeper() != null && keeper.getDogkeeper() != null) {
                ep.updatePetKeeper(username, "catkeeper", keeper.getCatkeeper());
                ep.updatePetKeeper(username, "dogkeeper", keeper.getDogkeeper());
            }
            if (keeper.getCatprice() > 0) {
                ep.updatePetKeeper(username, "catprice", String.valueOf(keeper.getCatprice()));
            }
            if (keeper.getDogprice() > 0) {
                ep.updatePetKeeper(username, "dogprice", String.valueOf(keeper.getDogprice()));
            }

            response.getWriter().write("PetKeeper edited");
            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_OK);
        } catch (ClassNotFoundException e) {
            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
