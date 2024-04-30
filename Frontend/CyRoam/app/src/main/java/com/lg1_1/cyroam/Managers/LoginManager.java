package com.lg1_1.cyroam.Managers;

import android.content.Context;
import android.util.Log;

import com.lg1_1.cyroam.objects.User;
import com.lg1_1.cyroam.volley.userVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is singleton and manages all the relevant user information.
 * We can store everything from permission data to score here, specifically about the user that is logged in.
 * @author Aidan Foss
 */

public class  LoginManager {
    private static String TAG = "LoginManager";
    private static LoginManager instance;
    private User user; //stores username and ID. likely not useful tbh besides storing username and ID.
    boolean isLoggedIn;
    private int score;
    private int permission;
    private int id;

    /*
    * 0 is basic user
    * 1 is user that can make pins
    * 2 is admin/debug
    */

    private LoginManager(){
        //pretty sure this is supposed to be empty
        //get request for score
    }
    public User setUser(){
        //bypass function to ensure always logged into bossf
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "bossf");
            jsonObject.put("isUser", true);
            jsonObject.put("message", "correct");
            jsonObject.put("userID", 1);
            jsonObject.put("permissions", 2);
            jsonObject.put("score", 123);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        user = new User("bossf",1,jsonObject);
        return user;
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }
    public boolean logInBypass(String username, String password, Context context){
        JSONObject jsonObject = new JSONObject();
        id = 1;
        permission = 2;
        try {
            jsonObject.put("username", username);
            jsonObject.put("isUser", true);
            jsonObject.put("message", "correct");
            jsonObject.put("userID", 1);
            jsonObject.put("permissions", 2);
            jsonObject.put("score", 123);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //store hardcoded user as logged in user
        user = new User(username, id, jsonObject);
        return true;
    }
    public void logIn(String username, String password, Context context) {
        try {
            if (!isLoggedIn) {
                int id = -999;

                //(username, password); make a login request here, receive permission value
                userVolley.getInstance(context).logInRequest(username, password, new userVolley.logInCallback() {
                    @Override
                    public void loggedIn(User inUser){
                        user = inUser;
                    }

                    @Override
                    public void logInFailure(String failResponse) {
                        user = null; //make sure user cant be handled at all
                    }

                    @Override
                    public void systemFailure(String errorMessage) {
                        Log.e(TAG, errorMessage);
                        //print "unknown error. Try again" to message box on login screen if the user is there
                    }
                });

                //begin login pseudo
                int receivedPermission = 2; //set temp user to be debug/admin
                int receivedScore = 0;
                JSONObject receivedJson = new JSONObject().put("name", username);
                receivedJson.put("score", receivedScore);
                receivedJson.put("permission", receivedPermission);
                //end login pseudo

                user = new User(username, id, receivedJson);
                permission = receivedPermission;
                score = 0;
            } else {
                //this means they are already logged in, which shouldn't be possible to attempt.
                Log.w(TAG, "Attempt to log in despite already being logged in");
            }
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    public void logOut(){
        //wipe user variable
        user = null;
    }

    public User getUser(){
        return user;
    }

    public int getPermission(){
        //hardcode in admin perms here
        permission = 2;
        return permission;
    }

}
