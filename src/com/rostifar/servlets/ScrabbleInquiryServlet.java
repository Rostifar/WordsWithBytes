package com.rostifar.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Git:Lazy (Dad) on 3/21/2016.
 * Servlet called by from end to process a player playing a word using back end.
 */
public class ScrabbleInquiryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String inquiryType = request.getParameter("inquiryType");
        System.out.println(this.getClass().getName() + " - Inquiry Action: " + inquiryType);
        getJSONforCurrentScrabbleBoard(request, response);
    }

    private void getJSONforCurrentScrabbleBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
        String json = ScrabbleServletHelper.getJSONforScrabbleBoard(ScrabbleServletHelper.getGameManagerFromSession(request));
        System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
