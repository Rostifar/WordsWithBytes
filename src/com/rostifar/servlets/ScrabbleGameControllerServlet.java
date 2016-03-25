package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  This Servlet can be used to "talk" to the ScrabbleGame Java based domain model from a UI.
 *  Created by GitLazy (Dad) on 3/4/2016.
 */
public class ScrabbleGameControllerServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println(this.getClass().getName() + "DO Post Called!");
       // String action = request.getParameter("Action");
        //JSObject
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println(this.getClass().getName() + "Starting Scrabble Game Engine on Backend...");

            //GameManager gameManager = new ScrabbleGameManager();
            //Player aPlayer = getPlayerFromSession(request);
            //gameManager.runGame();

    }

    protected ScrabbleGameManager getGameManager(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession();
        ScrabbleGameManager scrabbleGameManager = (ScrabbleGameManager) session.getAttribute("ScrabbleGameManager");

        if (scrabbleGameManager == null) {
            try {
                scrabbleGameManager = new ScrabbleGameManager();
            } catch (ScrabbleGameException exp) {
                throw new ServletException(exp.getMessage());
            }
        }
        return scrabbleGameManager;
    }

   protected void storeGameManager(HttpServletRequest request, ScrabbleGameManager scrabbleGameManager) {
        // GameManager gameManager = new ScrabbleGameManager();
        HttpSession session = request.getSession();
        session.setAttribute("ScrabbleGameManager", scrabbleGameManager);
    }


}
