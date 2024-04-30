package com.lg1_1.cyroam.objects;

public class Friend  {
    private final String name;
    private final int theirScore;
    private final int theirId;
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
