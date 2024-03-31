package com.lg1_1.cyroam.util;

public class Friend {
    private String name;
    private int theirScore;
    private int theirId;
    public Friend(String username, int score, int Id){
        this.name = username;
        theirScore = score;
        theirId = Id;
    }

    public String getName(){
        return name;
    }

    public int getTheirScore(){
        return theirScore;
    }

    public int getTheirId(){
        return theirId;
    }
}
