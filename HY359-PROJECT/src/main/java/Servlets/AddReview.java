/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DataBase.Tables.EditReviewsTable;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import mainClasses.JSON_Converter;

import java.io.IOException;
import mainClasses.Review;

/**
 *
 * @author georgia
 */
@WebServlet(name = "AddReview", urlPatterns = {"/AddReview"})
public class AddReview extends HttpServlet {

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
        EditReviewsTable ep = new EditReviewsTable();
        try {
            Review review = gson.fromJson(user_data, Review.class);

            ep.createNewReview(review);
            response.getWriter().write("Review added");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("Caught Class not Found Exception in Register Servlet: " + e.getMessage());
        }
    }
}
