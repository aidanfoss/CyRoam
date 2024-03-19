package com.lg1_1.cyroam.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/*
Unfortunately creating a new pin doesn't add it as a marker on the map.
AFAIK that is not possible, unless I moved all this over to a function underneath the map.
-bossf
 */
public class Pin {
    private double x;
    private double y;
    private int ID;
    private String name;
    private String description;
    //todo add pictures? might be out of scope

    public Pin(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        Log.w("Pin", "Pin With No ID Created, Named: " + name);
        //might be possible to create marker on gmap from here.
    }
    public Pin(double x, double y, String name, int ID) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.ID = ID;
        Log.w("Pin", "Pin With ID: " + String.valueOf(ID) + " Created. Named: " + name);
    }

    public LatLng getPos(){
        Log.w("Pin", "getPos() called, returning LatLng(" + String.valueOf(x)+","+String.valueOf(y)); //delete this later. Kind of fun
        return new LatLng(x,y);
    }

    public double getLat() {return x;}
    public double getLong() {return y;}
    public String getName(){return name;}
    public String getDescription(){
        return "Name: " + name + "(" + x + ", " + y + ")";
    }
}
