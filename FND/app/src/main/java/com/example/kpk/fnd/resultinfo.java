package com.example.kpk.fnd;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

/**
 * Created by KPK on 26-06-2017.
 */

public class resultinfo {

   private String title,link;
   private Uri image;

    public resultinfo (String title,String link,Uri image){


        setTitle(title);
        setLink(link);
    }

    public Uri getUri(){return image;}

    public void setImage(Uri image){this.image = image;}

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
