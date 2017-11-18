package com.example.lixiangning.dbtest;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class ColorList {
    String colorName;
    String colorValue;
    public ColorList(String colorName, String colorValue) {
        this.colorName = colorName;
        this.colorValue = colorValue;
    }
    public String getColorName() {
        return this.colorName;
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public String getColorValue() {
        return this.colorValue;
    }
    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }
}
