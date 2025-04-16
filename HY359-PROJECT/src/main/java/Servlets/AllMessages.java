/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditMessagesTable;
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
import mainClasses.Message;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AllMessages", urlPatterns = {"/AllMessages"})
public class AllMessages extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        EditPetKeepersTable su = new EditPetKeepersTable();
        EditMessagesTable c = new EditMessagesTable();
        try {
            PetKeeper keeper = su.databaseToPetKeepers(username, password);
            int keeper_id = keeper.getKeeper_id();
            ArrayList<Message> array = c.Messages_to_keeper(String.valueOf(keeper_id));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            List<String> keeperStrings = array.stream()
                    .map(mess -> (mess instanceof Message) ? ((Message) mess).toString() : mess.toString())
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
