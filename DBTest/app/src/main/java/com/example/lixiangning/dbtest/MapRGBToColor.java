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
    double res;
    private Map<Double, String> map;

    public MapRGBToColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        map = new HashMap<>();

    }

    public void initRGBMap() {
        map.put( (Math.pow((r-244), 2) + Math.pow((g-67), 2) + Math.pow((b-54), 2)), "Red 500" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-235), 2) + Math.pow((b-238), 2)), "Red 50" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-205), 2) + Math.pow((b-210), 2)), "Red 100" );
        map.put( (Math.pow((r-239), 2) + Math.pow((g-154), 2) + Math.pow((b-154), 2)), "Red 200" );
        map.put( (Math.pow((r-229), 2) + Math.pow((g-115), 2) + Math.pow((b-115), 2)), "Red 300" );
        map.put( (Math.pow((r-239), 2) + Math.pow((g-83), 2) + Math.pow((b-80), 2)), "Red 400" );
        map.put( (Math.pow((r-229), 2) + Math.pow((g-57), 2) + Math.pow((b-53), 2)), "Red 600" );
        map.put( (Math.pow((r-211), 2) + Math.pow((g-47), 2) + Math.pow((b-47), 2)), "Red 700" );
        map.put( (Math.pow((r-198), 2) + Math.pow((g-40), 2) + Math.pow((b-40), 2)), "Red 800" );
        map.put( (Math.pow((r-183), 2) + Math.pow((g-28), 2) + Math.pow((b-28), 2)), "Red 900" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-138), 2) + Math.pow((b-128), 2)), "Red A100" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-82), 2) + Math.pow((b-82), 2)), "Red A200" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-23), 2) + Math.pow((b-68), 2)), "Red A400" );
        map.put( (Math.pow((r-213), 2) + Math.pow((g-0), 2) + Math.pow((b-0), 2)), "Red A700" );

        map.put( (Math.pow((r-233), 2) + Math.pow((g-30), 2) + Math.pow((b-99), 2)), "Pink 500" );
        map.put( (Math.pow((r-252), 2) + Math.pow((g-228), 2) + Math.pow((b-236), 2)), "Pink 50" );
        map.put( (Math.pow((r-248), 2) + Math.pow((g-187), 2) + Math.pow((b-208), 2)), "Pink 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-143), 2) + Math.pow((b-177), 2)), "Pink 200" );
        map.put( (Math.pow((r-240), 2) + Math.pow((g-98), 2) + Math.pow((b-146), 2)), "Pink 300" );
        map.put( (Math.pow((r-236), 2) + Math.pow((g-64), 2) + Math.pow((b-122), 2)), "Pink 400" );
        map.put( (Math.pow((r-216), 2) + Math.pow((g-27), 2) + Math.pow((b-96), 2)), "Pink 600" );
        map.put( (Math.pow((r-194), 2) + Math.pow((g-24), 2) + Math.pow((b-91), 2)), "Pink 700" );
        map.put( (Math.pow((r-173), 2) + Math.pow((g-20), 2) + Math.pow((b-87), 2)), "Pink 800" );
        map.put( (Math.pow((r-136), 2) + Math.pow((g-14), 2) + Math.pow((b-79), 2)), "Pink 900" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-128), 2) + Math.pow((b-171), 2)), "Pink A100" );
        map.put( (Math.pow((r-255), 2) + Math.pow((g-64), 2) + Math.pow((b-129), 2)), "Pink A200" );
        map.put( (Math.pow((r-245), 2) + Math.pow((g-0), 2) + Math.pow((b-87), 2)), "Pink A400" );
        map.put( (Math.pow((r-197), 2) + Math.pow((g-17), 2) + Math.pow((b-98), 2)), "Pink A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Purple A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepPurple A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Indigo A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Blue A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightBlue A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Cyan A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Teal A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Green A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "LightGreen A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Lime A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Yellow A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Amber A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Orange A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange 900" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange A100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange A200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange A400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "DeepOrange A700" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Grey 900" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "BlueGrey 900" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 500" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 50" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 100" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 200" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 300" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 400" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 600" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 700" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 800" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Brown 900" );

        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "Black" );
        map.put( (Math.pow((r-244), 2) + Math.pow((g-244), 2) + Math.pow((b-244), 2)), "White" );

    }


    public void initHSBMap() {

    }

    public String getColorName() {
        String color = "";

        double min = Double.MAX_VALUE;

        for (Map.Entry<Double, String> entry : map.entrySet()) {

            if(entry.getKey() < min) {
                min = entry.getKey();
            }

        }

        color = map.get(min);

        return color;
    }

}
