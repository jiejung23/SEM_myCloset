package com.example.lixiangning.dbtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lixiangning on 2017/10/13.
 */

public class Clothes {
    int clothID;
    String clothImg;
    String clothCategory;
    String clothColor;
    String clothColorLabel;
    int clothR;
    int clothG;
    int clothB;
    String clothTexture;
    ArrayList<String> clothTags;
    Date addDate;
    Date checkDate;
    int checkTimes;
    int likeTimes;

    public Clothes(String clothImg, String clothCategory, String clothColor, String clothColorLabel,
                   int clothR, int clothG, int clothB, String clothTexture, String clothTags,
                   String addDate, String checkDate, int checkTimes, int likeTimes) {

        this.clothImg = clothImg;
        this.clothCategory = clothCategory;
        this.clothColor = clothColor;
        this.clothColorLabel = clothColorLabel;
        this.clothR = clothR;
        this.clothG = clothG;
        this.clothB = clothB;
        this.clothTexture = clothTexture;

        this.clothTags = new ArrayList<String>() ;
        String[] strTags = clothTags.split(", ");
        for(int i = 0; i < strTags.length; i++) {
            this.clothTags.add(strTags[i]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.addDate = sdf.parse(addDate);
            this.checkDate = sdf.parse(checkDate);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        this.checkTimes = checkTimes;
        this.likeTimes = likeTimes;
    }

    public void setClothID(int clothID) {
        this.clothID = clothID;
    }
    public int getClothID() {
      return this.clothID;
    }


    public String getClothImg() {
        return this.clothImg;
    }
    public String getClothCategory() {
        return this.clothCategory;
    }
    public String getClothColor() {
        return this.clothColor;
    }
    public String getClothColorLabel() {
        return this.clothColorLabel;
    }
    public int getClothR() {
        return this.clothR;
    }
    public int getClothG() {
        return this.clothG;
    }
    public int getClothB() {
        return this.clothB;
    }
    public String getClothTexture() {
        return this.clothTexture;
    }
    public ArrayList<String> getClothTages() {
        return this.clothTags;
    }
    public Date getAddDate() {
        return this.addDate;
    }
    public Date getCheckDate() {
        return this.checkDate;
    }
    public int getCheckTimes() {
        return this.checkTimes;
    }
    public int getLikeTimes() {
        return this.likeTimes;
    }



    public String getDBClothTages() {
        String tags = "";
        for(int i = 0; i < this.clothTags.size(); i++) {
            tags = tags + this.clothTags.get(i) + ", ";
        }
        return tags;
    }
    public String getDBAddDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strAdd = sdf.format(this.addDate);
        return strAdd;
    }
    public String getDBCheckDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strCheck = sdf.format(this.checkDate);
        return strCheck;
    }




}
