package com.example.lixiangning.dbtest;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class Styles {
    int styleID;
    String styleImg;
    int styleLike;

    public Styles(String img, int like) {
        this.styleImg = img;
        this.styleLike = like;
    }

    public int getStyleID() {
        return this.styleID;
    }

    public void setStyleID(int id) {
        this.styleID = id;
    }

    public String getStyleImg() {
        return this.styleImg;
    }

    public void setStyleImg(String img) {
        this.styleImg = img;
    }

    public int getStyleLike() {
        return this.styleLike;
    }

    public void setStyleLike(int like) {
        this.styleLike = like;
    }
}
