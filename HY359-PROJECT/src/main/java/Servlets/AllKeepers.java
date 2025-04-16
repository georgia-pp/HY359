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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AllKeepers", urlPatterns = {"/AllKeepers"})
public class AllKeepers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EditPetKeepersTable c = new EditPetKeepersTable();
        try {
            ArrayList<PetKeeper> array = c.getAllKeepers();
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
