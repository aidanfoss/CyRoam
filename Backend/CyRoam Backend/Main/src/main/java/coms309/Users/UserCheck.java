package coms309.Users;

public class UserCheck {
    private boolean isUSer;
//THIS MIGHT MESS STUFF UP CASUE isUSer IS NOT CAMELCASED CORRECTLY
    public UserCheck(boolean isUSer){
        this.isUSer = isUSer;
    }
    public UserCheck(){
        this.isUSer = false;
    }
    public boolean getIsUser() {
        return isUSer;
    }
    public void setIsUser(boolean isUSer) {
        this.isUSer = isUSer;
    }
}
