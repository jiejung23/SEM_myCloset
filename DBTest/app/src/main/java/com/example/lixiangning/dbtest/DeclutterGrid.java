package com.example.lixiangning.dbtest;

import android.net.Uri;

/**
 * Created by junjun on 11/10/17.
 */

public class DeclutterGrid {

    private Uri img_file;
    private String style_id;
    private String notification;

    public DeclutterGrid() {

    }

    public DeclutterGrid(Uri file, String id, String notification) {
        this.img_file = file;
        this.style_id = id;
        this.notification = notification;
    }

    public Uri getDeclutterImage() {
        return this.img_file;
    }

    public void setDeclutterImage(Uri file) {
        this.img_file = file;
    }

    public String getDeclutterID() {
        return this.style_id;
    }

    public String getNotification() {
        return this.notification;
    }
}
