/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import DataBase.Tables.EditPetOwnersTable;
import DataBase.Tables.EditPetsTable;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mainClasses.Pet;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AvailableKeepers", urlPatterns = {"/AvailableKeepers"})
public class AvailableKeepers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        EditPetOwnersTable su = new EditPetOwnersTable();
        EditPetKeepersTable c = new EditPetKeepersTable();
        EditPetsTable eu = new EditPetsTable();
        try {
            PetOwner owner = su.databaseToPetOwners(username, password);
            if (owner != null) {
                int owner_id = owner.getOwner_id();
                Pet pet = eu.petOfOwner(String.valueOf(owner_id));
                if (pet != null) {
                    String type = pet.getType();

                    ArrayList<PetKeeper> array = c.getAvailableKeepers(type);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                    List<String> keeperStrings = array.stream()
                            .map(keepers -> (keepers instanceof PetKeeper) ? ((PetKeeper) keepers).toString() : keepers.toString())
                            .collect(Collectors.toList());

                    String jsonWithKeepers = gson.toJson(keeperStrings);
                    System.out.println(jsonWithKeepers);
                    response.getWriter().write(jsonWithKeepers);
                    response.setStatus(200);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
