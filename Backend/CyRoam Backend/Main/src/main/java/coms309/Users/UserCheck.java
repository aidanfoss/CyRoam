package coms309.Users;

public class UserCheck {
    private boolean isUser;
//THIS MIGHT MESS STUFF UP CASUE isUSer IS NOT CAMELCASED CORRECTLY
    public UserCheck(boolean isUser){
        this.isUser = isUser;
    }
    public UserCheck(){
        this.isUser = false;
    }
    public boolean getIsUser() {
        return isUser;
    }
    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }
}
