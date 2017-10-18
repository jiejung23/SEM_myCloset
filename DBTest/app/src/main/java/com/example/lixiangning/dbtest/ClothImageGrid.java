package com.example.lixiangning.dbtest;

/**
 * Created by lixiangning on 2017/10/17.
 */

public class ClothImageGrid {

    private int img_file;
    private String cloth_id;
    private String cloth_categort;
    private String cloth_color;

    public ClothImageGrid() {

    }

    public ClothImageGrid(int file, String id) {
        this.img_file = file;
        this.cloth_id = id;
    }

    public ClothImageGrid(String id, String cloth_categort, String cloth_color) {
        this.cloth_id = id;
        this.cloth_categort = cloth_categort;
        this.cloth_color = cloth_color;
    }

    public int getClothImageGrid() {
        return this.img_file;
    }

    public void setClothImageGrid(int file) {
        this.img_file = file;
    }

    public String getClothGridID() {
        return this.cloth_id;
    }

    public String getClothGridCategory() {
        return this.cloth_categort;
    }

    public String getClothGridColor() {
        return this.cloth_color;
    }

}
