package com.lg1_1.cyroam.objects;

/**
 * Object that stores user data.
 * information is gathered and stored when logging in, and cant be
 * edited unless they log out.
 *
 * Information is used for verification
 */
public class User {
    private final int id;
    private final String username;
    //we dont want to store the password, unless we deem its easier to verify everything with it later.

    /**
     * @author Aidan Foss
     * Constructor that takes in username, password, and ID from
     * login step and saves it.
     *
     * In the future, needs to be singleton to prevent multiple accounts
     * from being logged in at the same time.
     *
     * doesn't store the password in the client
     *
     * @param username
     * @param id
     */
    public User(String username, int id){
        this.id = id;
        this.username = username;
        //add info that should be accessible to any user about any user (people should be able to see
    }

    /**
     * constructor that calls password for no reason.
     * Logging in should be done through the login manager
     * @author Aidan Foss
     * @deprecated
     * @param username
     * @param ignoredPassword
     * @param id
     */
    public User(String username, String ignoredPassword, int id){
        this.id = id;
        this.username = username;
    }

    public String getName(){
        return username;
    }


    public String getUsername() {return username;}


    public int getID() {return id;}

    //DO NOT MAKE RETURN PASSWORD unless theres a really good reason later.
    //(maybe if websocket connection is lost then use it to reconnect?)
    //Aidan, 3/4/2024

}
