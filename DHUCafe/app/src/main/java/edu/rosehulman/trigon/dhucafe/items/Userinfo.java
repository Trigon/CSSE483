package edu.rosehulman.trigon.dhucafe.items;

/**
 * Created by Trigon on 2/5/2017.
 */

public class Userinfo {
    int credit;

    public Userinfo(){

    }
    public Userinfo(int credit){
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
