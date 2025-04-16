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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mainClasses.PetOwner;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AllOwners", urlPatterns = {"/AllOwners"})
public class AllOwners extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EditPetOwnersTable c = new EditPetOwnersTable();
        try {
            ArrayList<PetOwner> array = c.getAllOwners();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            List<String> ownerStrings = array.stream()
                    .map(owners -> (owners instanceof PetOwner) ? ((PetOwner) owners).toString() : owners.toString())
                    .collect(Collectors.toList());

            String jsonWithOwners = gson.toJson(ownerStrings);
            System.out.println(jsonWithOwners);
            response.getWriter().write(jsonWithOwners);
            response.setStatus(200);

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
