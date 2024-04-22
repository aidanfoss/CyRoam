package coms309.Users;

public class UserCheck {

    private String username;
    private boolean isUser;
    private String message;
    private int userID;
    private int permissions;

    //to be linked with stats table
    private int score;


    public UserCheck(){
        message = "incorrect username/passsword";
        userID = -999999;
        permissions = -999999;
        username = "notarealuserL";
        score = -999999;
    }
    public UserCheck(int userID, String username, boolean isUser, int permissions, int score, String message){
        this.username = username;
        this.permissions = permissions;
        this.userID = userID;
        this.message = message;
        this.score = score;
        this.isUser = isUser;
    }
    public boolean getIsUser() {
        return isUser;
    }
    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public String getMessage() {
        return message;
    }
    public void setScore(String message) {
        this.message = message;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setisUser(Boolean isUser) {
        this.isUser = isUser;
    }


}

