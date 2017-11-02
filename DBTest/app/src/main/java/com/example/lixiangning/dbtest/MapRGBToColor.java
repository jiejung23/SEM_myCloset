package com.example.lixiangning.dbtest;

import android.content.Intent;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiangning on 2017/10/30.
 */

public class MapRGBToColor {

    private double r, g, b;
    private Map<Double, Integer> map;

    public MapRGBToColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        map = new HashMap<>();
        double res = (r-255)*(r-255) + (g-255)*(g-255) + (b-255)*(b-255);
        map.put(res, 0);

        res = (r-255)*(r-255) + (g-235)*(g-235) + (b-59)*(b-59);
        map.put(res, 1);

        res = (r-255)*(r-255) + (g-152)*(g-152) + (b-0)*(b-0);
        map.put(res, 2);

        res = (r-255)*(r-255) + (g-138)*(g-138) + (b-128)*(b-128);
        map.put(res, 3);

        res = (r-244)*(r-244) + (g-67)*(g-67) + (b-54)*(b-54);
        map.put(res, 4);

        res = (r-183)*(r-183) + (g-28)*(g-28) + (b-28)*(b-28);
        map.put(res, 5);

        res = (r-255)*(r-255) + (g-128)*(g-128) + (b-171)*(b-171);
        map.put(res, 6);

        res = (r-233)*(r-233) + (g-30)*(g-30) + (b-99)*(b-99);
        map.put(res, 7);

        res = (r-156)*(r-156) + (g-39)*(g-39) + (b-176)*(b-176);
        map.put(res, 8);

        res = (r-139)*(r-139) + (g-195)*(g-195) + (b-74)*(b-74);
        map.put(res, 9);

        res = (r-76)*(r-76) + (g-175)*(g-175) + (b-80)*(b-80);
        map.put(res, 10);

        res = (r-51)*(r-51) + (g-105)*(g-105) + (b-30)*(b-30);
        map.put(res, 11);

        res = (r-79)*(r-79) + (g-195)*(g-195) + (b-247)*(b-247);
        map.put(res, 12);

        res = (r-33)*(r-33) + (g-150)*(g-150) + (b-243)*(b-243);
        map.put(res, 13);

        res = (r-26)*(r-26) + (g-35)*(g-35) + (b-126)*(b-126);
        map.put(res, 14);

        res = (r-0)*(r-0) + (g-150)*(g-150) + (b-136)*(b-136);
        map.put(res, 15);

        res = (r-158)*(r-158) + (g-158)*(g-158) + (b-158)*(b-158);
        map.put(res, 16);

        res = (r-96)*(r-96) + (g-125)*(g-125) + (b-139)*(b-139);
        map.put(res, 17);

        res = (r-121)*(r-121) + (g-85)*(g-85) + (b-72)*(b-72);
        map.put(res, 18);

        res = (r-0)*(r-0) + (g-0)*(g-0) + (b-0)*(b-0);
        map.put(res, 19);
    }

    public int getColorName() {
        int color = 0;

        double min = Double.MAX_VALUE;

        for (Map.Entry<Double, Integer> entry : map.entrySet()) {

            if(entry.getKey() < min) {
                min = entry.getKey();
            }

        }

        color = map.get(min);

        return color;
    }

}
