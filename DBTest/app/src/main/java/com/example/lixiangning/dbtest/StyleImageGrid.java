package com.example.lixiangning.dbtest;

import android.net.Uri;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class StyleImageGrid {

    private Uri img_file;
    private String style_id;
    private int style_like;

    public StyleImageGrid() {

    }

    public StyleImageGrid(Uri file, String id) {
        this.img_file = file;
        this.style_id = id;
    }

    public StyleImageGrid(Uri file, String id, int like) {
        this.img_file = file;
        this.style_id = id;
        this.style_like = like;
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

    public void setStyleLike(int like) {
        this.style_like = like;
    }
    public int getStyleLike() {
        return this.style_like;
    }

}
