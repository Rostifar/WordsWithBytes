package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * Created by ross on 3/18/16.
 */
public class UserManager implements HttpSession {

    private List<String> listOfUsers = new ArrayList<>();

    HttpSessionEvent g = new HttpSessionEvent()
    private void createNewSession() {
    }

    @Override
    public String getId() {

    }

    @Override
    public long getLastAccessedTime() {

    }

    private void addUserToSession(String userName) {
        listOfUsers.add(userName);
    }

}
