package com.example.lixiangning.dbtest;

import android.content.Intent;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiangning on 2017/10/30.
 */

public class MapRGBToColor {

    private float h, s, b;
    private String colorName = "";

    public MapRGBToColor(float h, float s, float b) {
        this.h = h;
        this.s = s;
        this.b = b;
    }

    public String getColorName() {

        if(b >= 0.9 && s <= 0.05) {
            colorName = "white";
        }
        else if (b >= 0.6 && b < 0.9 && s < 0.08) {
            colorName = "gray, light gray";
        }
        else if(b >= 0.4 && b < 0.6 && s < 0.15) {
            colorName = "gray";
        }
        else if(b >= 0.2 && b < 0.4 && s < 0.15) {
            colorName = "gray, dark gray";
        }
        else if(b < 0.2) {
            colorName = "black";
        }


        else if(h <= 5 || h > 355) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "red";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "red, dark red";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "red, light red";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "red, dark red";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "red, dark red, gray";
            } else {
                colorName = "red, gray";
            }
        }

        else if(h <= 355 && h > 350) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "red";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "red, dark red";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "red, light red, pink, light pink";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "red, light red, pink, light pink";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "red, dark red";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "red, dark red, gray";
            } else {
                colorName = "red, gray";
            }
        }

        else if(h <= 350 && h > 340) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "red, pink";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "red, dark red, dark pink";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "red, light red, pink, light pink";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "red, light red, pink, light pink";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "red, dark red, pink, dark pink";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "red, dark red, pink, dark pink, gray";
            } else {
                colorName = "red, pink, gray";
            }
        }

        else if(h <= 340 && h > 325) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "pink";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "pink, dark pink";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "pink, light pink";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "pink, dark pink";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "pink, dark pink, gray";
            } else {
                colorName = "pink, gray";
            }
        }

        else if(h <= 325 && h > 305) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "pink";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "pink, dark pink, purple, dark purple";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "pink, light pink";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "pink, dark pink, purple, dark purple";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "pink, dark pink, purple, dark purple, gray";
            } else {
                colorName = "pink, purple, gray";
            }
        }

        else if(h <= 305 && h > 295) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "pink, purple";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "pink, dark pink, purple, dark purple";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "pink, light pink, purple, light purple";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "pink, dark pink, purple, dark purple";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "pink, dark pink, purple, dark purple, gray";
            } else {
                colorName = "pink, purple, gray";
            }
        }

        else if(h <= 295 && h > 260) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "purple";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "purple, dark purple";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "purple, light purple";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "purple, dark purple";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "purple, dark purple, gray";
            } else {
                colorName = "purple, gray";
            }
        }

        else if(h <= 260 && h > 245) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "indigo, blue,purple";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "indigo, dark indigo, blue, dark blue, purple, dark purple";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "indigo, light indigo, purple, light purple";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "indigo, dark indigo, blue, dark blue, purple, dark purple";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "indigo, dark indigo, blue, dark blue, purple, dark purple, gray";
            } else {
                colorName = "indigo, blue, purple, gray";
            }
        }

        else if(h <= 245 && h > 230) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "indigo, blue, dark blue";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "indigo, dark indigo, blue,dark blue";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "indigo, light indigo, blue, light blue";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "indigo, dark indigo, blue, dark blue";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "indigo, dark indigo, blue, dark blue, gray";
            } else {
                colorName = "indigo, blue, gray";
            }
        }

        else if(h <= 230 && h > 200) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "blue";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "blue, dark blue";
            } else if((b >= 0.9 && s >= 0.05 && s < 0.75) || (s < 0.5 && s >= 0.25 && b >= 0.8)) {
                colorName = "blue, light blue";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "blue, dark blue";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "blue, dark blue, gray";
            } else {
                colorName = "blue, gray";
            }
        }

        else if(h <= 200 && h > 190) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "blue, cyan";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "blue, dark blue, cyan, dark cyan";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "blue, light blue";
            } else if (s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "blue, light blue, cyan, light cyan";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "blue, dark blue, cyan, dark cyan";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "blue, dark blue, gray, cyan, dark cyan, blue gray";
            } else {
                colorName = "blue, cyan, gray, blue gray";
            }
        }

        else if(h <= 190 && h > 180) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "cyan";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "cyan, dark cyan";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "cyan, light cyan";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "cyan, light cyan";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "cyan, dark cyan";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "cyan, dark cyan, gray";
            } else {
                colorName = "cyan, gray";
            }
        }

        else if(h <= 180 && h > 165) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "teal, green";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "teal, dark teal, green, dark green";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "teal, light teal, green, light green";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "teal, light teal, green, light green";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "teal, dark teal, green, dark green";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "teal, dark teal, green, dark green, gray";
            } else {
                colorName = "teal, green, gray";
            }
        }

        else if(h <= 165 && h > 75) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "green";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "green, dark green";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "green, light green";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "green, light green";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "green, dark green";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "green, dark green, gray";
            } else {
                colorName = "green, gray";
            }
        }

        else if(h <= 75 && h > 65) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "lime, yellow, green";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "lime, dark lime, green, dark green";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "lime, light lime, yellow, light yellow";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "lime, light lime, green, light green";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "lime, dark lime, green, dark green";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "lime, dark lime, green, dark green, gray";
            } else {
                colorName = "lime, yellow,green, gray";
            }
        }

        else if(h <= 65 && h > 50) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "yellow";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "yellow, dark yellow, brown";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "yellow, light yellow";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "yellow, light yellow";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "yellow, dark yellow";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "yellow, dark yellow, gray";
            } else {
                colorName = "yellow, gray";
            }
        }

        else if(h <= 50 && h > 45) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "yellow, amber";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "yellow, dark yellow, amber, dark amber, brown";
            } else if(b >= 0.9 && s >= 0.5 && s < 0.75) {
                colorName = "yellow, light yellow, amber, light amber";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "yellow, light yellow, amber, light amber";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "yellow, dark yellow, amber, dark amber";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "yellow, dark yellow, amber, dark amber, brown, gray";
            } else if(s >= 0.3) {
                colorName = "yellow, amber, brown";
            } else {
                colorName = "yellow, amber, gray";
            }
        }

        else if(h <= 45 && h > 37) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "orange, amber";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "orange, dark orange, amber, dark amber,brown";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "orange, light orange, amber, light amber";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "orange, light orange, amber, light amber";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "orange, dark orange, amber, dark amber";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "orange, dark orange, gray, amber, dark amber, brown";
            } else if(s >= 0.3) {
                colorName = "orange, amber, brown";
            } else {
                colorName = "orange, amber, gray";
            }
        }

        else if(h <= 37 && h > 10) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "orange";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "orange, dark orange, brown";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "orange, light orange";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "orange, light orange,brown";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "orange, dark orange";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "orange, dark orange, gray, brown";
            } else if(s >= 0.3) {
                colorName = "orange, brown";
            } else {
                colorName = "orange, gray";
            }
        }

        else if(h <= 10 && h > 5) {
            if(b >= 0.65 && s >= 0.75) {
                colorName = "orange, red";
            } else if(b < 0.65 && b >= 0.2 && s >= 0.6) {
                colorName = "orange, dark orange, red, dark red, brown";
            } else if(b >= 0.9 && s >= 0.05 && s < 0.75) {
                colorName = "orange, light orange, red, light red";
            } else if(s < 0.5 && s >= 0.25 && b >= 0.8) {
                colorName = "orange, light orange, red, light red, brown";
            } else if((b >= 0.65 && b < 0.9 && s >= 0.5 && s < 0.75) || (b >= 0.2 && b < 0.9 && s >= 0.5 && s < 0.6)) {
                colorName = "orange, dark orange, red, dark red, brown";
            } else if(b >= 0.15 && b < 0.5 && s >= 0.2 && s < 0.5){
                colorName = "orange, dark orange, red, dark red, brown";
            } else if(s >= 0.3) {
                colorName = "orange, red, brown";
            } else {
                colorName = "orange, red, gray";
            }
        }

        return colorName;
    }

}
