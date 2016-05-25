package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.Main;
import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.gamecontrol.ScrabbleGameManager;
import com.rostifar.scrabbleproject.GameIds;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ross on 4/24/16.
 */
public class StartScrabbleGameServlet extends HttpServlet {
    ScrabbleGameManager gameManager;

}
