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
    String clothTexture;
    ArrayList<String> clothTages;
    Date addDate;
    Date checkDate;
    int checkTimes;
    int likeTimes;

    public Clothes(int clothID, String clothImg, String clothCategory, String clothColor, String clothTexture,
                   String clothTages, String addDate, String checkDate, int checkTimes, int likeTimes) {

        this.clothID = clothID;
        this.clothImg = clothImg;
        this.clothCategory = clothCategory;
        this.clothColor = clothColor;
        this.clothTexture = clothTexture;
        String[] tagsList = clothTages.split(",");
        for(int i = 0; i < tagsList.length; i++) {
            this.clothTages.add(tagsList[i]);
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

}
