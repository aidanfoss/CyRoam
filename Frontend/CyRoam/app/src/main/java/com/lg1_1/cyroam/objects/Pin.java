package com.lg1_1.cyroam.objects;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 * Pin object that stores information on individual pins.
 *
 * Location, ID, name, description, are global variables
 * discovered is information for individual users.
 *
 * pins ARE NOT markers on the map, its just a class i use to store relevant information
 * @author Aidan Foss
 */
public class Pin {
    private final String TAG = "Pin";

    private final double x;
    private final double y;
    private int ID;
    private final String name;
    private String description;
    private String snippet;
    private boolean discovered; //default to false in each constructor unless told otherwise
    //todo add pictures? might be out of scope

    /**
     * @author Aidan Foss
     * Takes pin in as a constructor
     * @param inPin
     */
    public Pin(Pin inPin){
        this.x = inPin.x;
        this.y = inPin.y;
        this.description = inPin.description;
        this.snippet = inPin.snippet;
        this.name = inPin.name;
        this.ID = inPin.ID;
        this.discovered = inPin.discovered;
    }

    /**
     * @author Aidan Foss
     * @deprecated
     * @param x
     * @param y
     * @param name
     */
    public Pin(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        Log.v(TAG, "Pin With No ID Created, Named: " + name);
        //might be possible to create marker on gmap from here.
        discovered = false;
    }

    /**
     * @author Aidan Foss
     * @deprecated
     * @param x
     * @param y
     * @param name
     * @param ID
     */
    public Pin(double x, double y, String name, int ID) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.ID = ID;
        Log.v(TAG, "Pin With ID: " + ID + " Created. Named: " + name);
        discovered = false;
    }

    /**
     * @author Aidan Foss
     * Full Constructor for pin information.
     * @param x x coordinate
     * @param y y coordinate
     * @param name name of the location displayed by the pin
     * @param description description of the location
     * @param ID internal ID in the SQL database
     * @param discovered boolean that stores if the pin has been seen before
     */
    public Pin(double x, double y, String name, String snippet, String description, int ID, boolean discovered) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.ID = ID;
        this.description = description;
        this.snippet = snippet;
        this.discovered = discovered;
        Log.i(TAG, "Pin With ID: " + ID + " Created. Named: " + name + ". Discover Value: " + discovered);
    }

    /**
     * @author Aidan Foss
     * returns position for map usage
     * @return position in LatLng format
     */
    public LatLng getPos(){
        Log.w("Pin", "getPos() called, returning LatLng(" + x +","+ y); //delete this later. Kind of fun
        return new LatLng(x,y);
    }

    public int getID(){return ID;}
    public double getLat() {return x;}
    public double getLong() {return y;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getSnippet(){return snippet;}
    public String getDebugDescription(){
        return "Name: " + name + "(" + x + ", " + y + "). Discovered: " + discovered;
    }
    public void setTrue(){discovered = true;}
    public boolean getDiscovered(){return discovered;}
}
