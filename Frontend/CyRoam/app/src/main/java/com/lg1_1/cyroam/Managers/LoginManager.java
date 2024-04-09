package com.lg1_1.cyroam.Managers;

import android.util.Log;

import com.lg1_1.cyroam.objects.User;

public class LoginManager {
    //Needs to be singleton
    private static LoginManager instance;
    private User user;
    boolean isLoggedIn;
    private int score;

    private LoginManager(){
        //pretty sure this is supposed to be empty
        //get request for score
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public void logIn(String username, String password) {
        if (!isLoggedIn){
            int id = -999;

            //(username, password);
            user = new User(username, id);
        }
    }

    public void logOut(){
        //wipe user variable
        user = null;
    }

    public User getUser(){
        return user;
    }

}
