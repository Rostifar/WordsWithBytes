package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameManager;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ross on 4/24/16.
 */
public class SkipTurnServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
    }

}
