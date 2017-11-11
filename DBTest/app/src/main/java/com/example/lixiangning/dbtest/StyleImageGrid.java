package com.example.lixiangning.dbtest;

import android.net.Uri;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class StyleImageGrid {

    private Uri img_file;
    private String style_id;

    public StyleImageGrid() {

    }

    public StyleImageGrid(Uri file, String id) {
        this.img_file = file;
        this.style_id = id;
    }

    public Uri getStyleImage() {
        return this.img_file;
    }

    public void setStyleImage(Uri file) {
        this.img_file = file;
    }

    public String getStyleID() {
        return this.style_id;
    }

}
