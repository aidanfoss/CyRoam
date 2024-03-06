package com.lg1_1.cyroam.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/*
Unfortunately creating a new pin doesn't add it as a marker on the map.
AFAIK that is not possible, unless I moved all this over to a function underneath the map.
-bossf
 */
public class Pin {
    private final String TAG = "Pin";

    private double x;
    private double y;
    private int ID;
    private String name;
    private String description;
    private boolean discovered; //default to false in each constructor unless told otherwise
    //todo add pictures? might be out of scope
    public Pin(Pin inPin){
        this.x = inPin.x;
        this.y = inPin.y;
        this.description = inPin.description;
        this.name = inPin.name;
        this.ID = inPin.ID;
        this.discovered = inPin.discovered;
    }
    public Pin(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        Log.v(TAG, "Pin With No ID Created, Named: " + name);
        //might be possible to create marker on gmap from here.
        discovered = false;
    }
    public Pin(double x, double y, String name, int ID) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.ID = ID;
        Log.v(TAG, "Pin With ID: " + String.valueOf(ID) + " Created. Named: " + name);
        discovered = false;
    }

    public Pin(double x, double y, String name, String description, int ID, boolean discovered) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.ID = ID;
        this.description = description;
        this.discovered = discovered;
        Log.i(TAG, "Pin With ID: " + String.valueOf(ID) + " Created. Named: " + name + ". Discover Value: " + discovered);
    }

    public LatLng getPos(){
        Log.w("Pin", "getPos() called, returning LatLng(" + String.valueOf(x)+","+String.valueOf(y)); //delete this later. Kind of fun
        return new LatLng(x,y);
    }

    public int getID(){return ID;}
    public double getLat() {return x;}
    public double getLong() {return y;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getDebugDescription(){
        return "Name: " + name + "(" + x + ", " + y + "). Discovered: " + discovered;
    }
    public void setTrue(){discovered = true;}
    public boolean isDiscovered(){return discovered;}
}
