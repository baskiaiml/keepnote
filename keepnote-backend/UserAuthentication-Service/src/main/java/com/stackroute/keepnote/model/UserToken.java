package com.stackroute.keepnote.model;

/**
 * Created by baskaran.murugesan on 11/23/2018.
 */
public class UserToken {
    private String usertoken;
    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }
public UserToken(String usertoken, String currentUser){
    this.usertoken = usertoken;
    this.currentUser = currentUser;
}
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }
}
