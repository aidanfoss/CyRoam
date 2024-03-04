package com.lg1_1.cyroam.util;

public class User {
    private int id;
    private String username;
    private String password;
    //we dont want to store the password, unless we deem its easier to verify everything with it later.

    public User(String username, String password, int id){
        this.id = id;
        this.username = username;
        this.password = password;

        //when creating this, you need to call a volley request to get the userID.
        //pass it into this object, which can be used later for verification.
        //could potentially connect to the websocket at that moment too
    }

    public String getUsername() {return username;}
    public int getID() {return id;}

    //DO NOT MAKE RETURN PASSWORD unless theres a really good reason later.
    //(maybe if websocket connection is lost then use it to reconnect?)
    //Aidan, 3/4/2024

}
