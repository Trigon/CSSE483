package edu.rosehulman.trigon.dhucafe.items;

/**
 * Created by Trigon on 1/29/2017.
 */

public class NewsItem {
    String name;
    String detail;
    String link;
    String key;
    String date="";

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {

        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getLink() {
        return link;
    }

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }
    public NewsItem(){

    }

    public void update(NewsItem newsItem){
        name =newsItem.getName();
        detail=newsItem.getDetail();
        date = newsItem.getDate();
        link = newsItem.getLink();

    }

    public NewsItem(String name,String detail,String link){
        this.name=name;
        this.detail =detail;
        this.link = link;
    }
}
