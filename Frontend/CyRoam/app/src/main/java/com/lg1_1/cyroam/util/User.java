package com.lg1_1.cyroam.util;

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
     * @param ignoredPassword
     * @param id
     */

    //todo make this singleton
    //todo make this save on the phone, so you dont need to log in every time
    public User(String username, String ignoredPassword, int id){
        this.id = id;
        this.username = username;

        //use password only in this constructor to verify.
        //if password matches, change a boolean to true or something
        //password is named  ignoredPassword to prevent warning for lack of use
        //todo implement password/login verification somewhere

        //when creating this, you need to call a volley request to get the userID.
        //pass it into this object, which can be used later for verification.
        //could potentially connect to the websocket at that moment too
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
