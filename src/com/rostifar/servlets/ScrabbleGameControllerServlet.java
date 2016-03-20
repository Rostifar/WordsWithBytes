package com.rostifar.servlets;

import com.rostifar.scrabbleproject.Player;

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
        System.out.println(this.getClass().getName() + "DO GET Called!");
        String action = request.getParameter("Action");
        //JSObject
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println(this.getClass().getName() + "Starting Scrabble Game Engine on Backend...");

            // GameManager gameManager = new ScrabbleGameManager();
            Player aPlayer = getPlayerFromSession(request);
            //gameManager.runGame();

    }

    protected Player getPlayerFromSession(HttpServletRequest request) {
        // GameManager gameManager = new ScrabbleGameManager();
        HttpSession session = request.getSession();
        Player aPlayer;
        aPlayer = (Player) session.getAttribute("Player");
        //gameManager.runGame();
        return aPlayer;
    }

    protected void storePlayerOnSession(HttpServletRequest request, Player aPlayer) {
        // GameManager gameManager = new ScrabbleGameManager();
        HttpSession session = request.getSession();
        session.setAttribute("Player", aPlayer);
    }


}
