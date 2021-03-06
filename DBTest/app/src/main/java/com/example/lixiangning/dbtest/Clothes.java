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
    String clothTags;
    Date addDate;
    Date checkDate;
    Date thinkDate;
    int checkTimes;
    int likeTimes;

    public Clothes(String clothImg, String clothCategory, String clothColor, String clothColorLabel,
                   int clothR, int clothG, int clothB, String clothTexture, String clothTags,
                   String addDate, String checkDate, String thinkDate, int checkTimes, int likeTimes) {

        this.clothImg = clothImg;
        this.clothCategory = clothCategory;
        this.clothColor = clothColor;
        this.clothColorLabel = clothColorLabel;
        this.clothR = clothR;
        this.clothG = clothG;
        this.clothB = clothB;
        this.clothTexture = clothTexture;

        this.clothTags = clothTags;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.addDate = sdf.parse(addDate);
            this.checkDate = sdf.parse(checkDate);
            if(thinkDate.equals("")) {
                this.thinkDate = null;
            } else {
                this.thinkDate = sdf.parse(thinkDate);
            }
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
    public String getClothTages() {
        return this.clothTags;
    }
    public Date getAddDate() {
        return this.addDate;
    }
    public Date getCheckDate() {
        return this.checkDate;
    }
    public Date getThinkDate() {
        return this.thinkDate;
    }
    public int getCheckTimes() {
        return this.checkTimes;
    }
    public int getLikeTimes() {
        return this.likeTimes;
    }



    public String getDBClothTages() {
        return this.clothTags;
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

    public String getDBThinkDate() {
        if(this.thinkDate == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strThink = sdf.format(this.thinkDate);
            return strThink;
        }
    }


}
