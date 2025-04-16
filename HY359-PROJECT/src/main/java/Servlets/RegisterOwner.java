package Servlets;

import DataBase.Tables.EditPetKeepersTable;
import DataBase.Tables.EditPetOwnersTable;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import mainClasses.JSON_Converter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

@WebServlet(name = "RegisterOwner", value = "/RegisterOwner")
public class RegisterOwner extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet Site:");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JSON_Converter jsonRequest = new JSON_Converter();
        String user_data = jsonRequest.getJSONFromAjax(request.getReader());

        System.out.println("User Data: " + user_data);
        EditPetOwnersTable ep = new EditPetOwnersTable();
        EditPetKeepersTable op = new EditPetKeepersTable();

        try {
            PetOwner Owner = gson.fromJson(user_data, PetOwner.class);
            String username = Owner.getUsername();
            String email = Owner.getEmail();

            PetKeeper keep = op.findPetKeeper(username, email);
            PetOwner own = ep.findPetOwner(username, email);

            if (keep == null && own == null) {
                ep.addNewPetOwner(Owner);
                response.getWriter().write("PetOwner added");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterKeeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
