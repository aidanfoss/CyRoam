package com.lg1_1.cyroam.util;

public class LoginManager {
    //Needs to be singleton
    private static LoginManager instance;
    private User user;
    boolean isLoggedIn;

    private LoginManager(){
        //pretty sure this is supposed to be empty
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
            //post request to log in, receive ID, and log it.
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
