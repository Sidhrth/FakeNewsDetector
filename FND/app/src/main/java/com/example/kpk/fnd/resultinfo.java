package com.example.kpk.fnd;

/**
 * Created by KPK on 26-06-2017.
 */

public class resultinfo {

   private String title,link;

    public resultinfo (String title,String link){

        setTitle(title);
        setLink(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
