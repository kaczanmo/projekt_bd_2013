package model;

import java.io.Serializable;


public class LoginModel implements LoginModelI, Serializable {

    String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}