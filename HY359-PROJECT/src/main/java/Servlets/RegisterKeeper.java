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

@WebServlet(name = "RegisterKeeper", value = "/RegisterKeeper")
public class RegisterKeeper extends HttpServlet {

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
        EditPetKeepersTable ep = new EditPetKeepersTable();
        EditPetOwnersTable op = new EditPetOwnersTable();
        try {
            PetKeeper keeper = gson.fromJson(user_data, PetKeeper.class);
            String username = keeper.getUsername();
            String email = keeper.getEmail();
            
            PetKeeper keep = ep.findPetKeeper(username, email);
            PetOwner own = op.findPetOwner(username, email);

            if(keep == null && own == null){
                ep.addNewPetKeeper(keeper);
                response.getWriter().write("PetKeeper added");
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
