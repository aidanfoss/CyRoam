package coms309.Users;

public class UserScore {
    private String user;

    private int score;


    public UserScore(String user, int score){
        this.user = user;
        this.score = score;
    }
    public UserScore(){
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}

