package com.example.lixiangning.dbtest;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class Styles {
    int styleID;
    String styleImg;

    public Styles(int id, String img) {
        this.styleID = id;
        this.styleImg = img;
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
}
