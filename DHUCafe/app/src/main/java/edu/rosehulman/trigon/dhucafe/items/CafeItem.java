package edu.rosehulman.trigon.dhucafe.items;

/**
 * Created by Trigon on 1/29/2017.
 */

public class CafeItem {
    String name;
    String detail;
    String key;
    String link;
    int price = 0;


    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getLink(){
        return link;
    }

    public String getDetail() {
        return detail;
    }

    public String getKey() {
        return key;
    }

    public int getPrice() {
        return price;
    }

    public void update(CafeItem newitem){
        detail = newitem.getDetail();
        link = newitem.getLink();
        price = newitem.getPrice();
        name = newitem.getName();
    }
    public CafeItem(String name,String detail,String link,int price) {
        this.name = name;
        this.detail = detail;
        this.link = link;
        this.price = price;
    }
    public CafeItem(){};

}