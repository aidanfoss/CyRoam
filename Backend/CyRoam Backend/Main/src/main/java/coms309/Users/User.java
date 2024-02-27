package coms309.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    private String username;
    private String password;

    //contructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }
    public User()
    {

    }
    //getters and setters
    public int getuId(){
        return uId;
    }

    public void setId(int uId){
        this.uId = uId;
    }

    public String getUsername(){
        return username;
    }

    public void setName(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }


}
