package edu.rosehulman.trigon.dhucafe.items;

import java.util.Date;

/**
 * Created by Trigon on 1/22/2017.
 */

public  class User {
    private String username = "N/A";
    private Date lastlogin;
    private Userinfo info;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, Date currentDate){
        this.username=username;
        this.lastlogin=currentDate;
    }

    public Userinfo getInfo(){
        return this.info;
    }

    public void setInfo(Userinfo Info){
        this.info = Info;
    }




}
