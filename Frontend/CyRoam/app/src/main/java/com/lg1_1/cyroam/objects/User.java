package com.lg1_1.cyroam.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Object that stores user data.
 * information is gathered and stored when logging in, and cant be
 * edited unless they log out.
 * Information is used for verification
 */
public class User {
    private final int id;
    private final String username;
    private final int permission;
    private final JSONObject jsonObject;
    //we don't want to store the password, unless we deem its easier to verify everything with it later.

    /**
     * @author Aidan Foss
     * Constructor that takes in username, password, and ID from
     * login step and saves it.
     * In the future, needs to be singleton to prevent multiple accounts
     * from being logged in at the same time.
     * doesn't store the password in the client
     *
     * @param username stores username
     * @param id stores user ID
     * @param jsonObject Stores the raw input from the server for handling when appropriate
     */
    public User(String username, int id, JSONObject jsonObject){
        this.id = id;
        this.username = username;
        this.jsonObject = jsonObject;
        //add info that should be accessible to any user about any user (people should be able to see
        try {
            if (jsonObject.has("permission")){
                permission = jsonObject.getInt("permission");
            }
            else{
                //todo fix this
                permission = 2;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public String getName(){
        return username;
    }


    public String getUsername() {return username;}
    public int getPermission() {
        return 2;
    }


    public int getID() {return id;}

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    //DO NOT MAKE RETURN PASSWORD unless theres a really good reason later.
    //(maybe if websocket connection is lost then use it to reconnect?)
    //Aidan, 3/4/2024

}
