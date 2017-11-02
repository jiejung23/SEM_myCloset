package com.example.lixiangning.dbtest;

/**
 * Created by lixiangning on 2017/11/1.
 */

public class CategoryList {
    String categoryName;

    public CategoryList(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
