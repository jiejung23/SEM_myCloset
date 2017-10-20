package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddClothesActivity extends AppCompatActivity {

    private Spinner spin_color;
    private Spinner spin_category;
    private String clothCategory;
    private String clothColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spin_color  = (Spinner) findViewById(R.id.spin_color);
        spin_category = (Spinner) findViewById(R.id.spin_category);

        List<ColorList> categories = new ArrayList<ColorList>();
        categories.add(new ColorList("Tops", "#01000000"));
        categories.add(new ColorList("Skirts", "#01000000"));
        categories.add(new ColorList("Pants", "#01000000"));
        SpinnerAdapter categoryAdapter = new SpinnerAdapter(this, categories);
        spin_category.setAdapter(categoryAdapter);

        List<ColorList> colors = new ArrayList<ColorList>();
        colors.add(new ColorList("White", "#ffffff"));
        colors.add(new ColorList("Black", "#000000"));
        colors.add(new ColorList("Grey", "#9e9e9e" ));
        colors.add(new ColorList("Brown", "#795548"));
        colors.add(new ColorList("Blue", "#2196f3"));
        colors.add(new ColorList("Green", "#4caf50"));
        colors.add(new ColorList("Purple", "#9c27b0"));
        colors.add(new ColorList("Yellow", "#ffeb3b"));
        colors.add(new ColorList("Pink", "#e91e63"));
        colors.add(new ColorList("Red", "#f44336"));
        colors.add(new ColorList("Orange", "#ff9800"));
        colors.add(new ColorList("Other", "#ffffff"));
        SpinnerAdapter colorAdapter = new SpinnerAdapter(this, colors);
        spin_color.setAdapter(colorAdapter);

        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clothCategory = categories.get(position).getColorName();
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clothCategory = "";
            }
        });

        spin_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clothColor = colors.get(position).getColorName();
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clothColor = "";
            }
        });


        Button btn_add = (Button)findViewById(R.id.btn_addClothes);

        //add cloth to database
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clothImg = "";
                String category = clothCategory;
                String color = clothColor;
                String clothTexture = "";
                String clothTags = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());
                String addDate = sdf.format(curDate);
                String checkDate = sdf.format(curDate);
                int checkTimes = 0;
                int likeTimes = 0;

                Clothes clothes = new Clothes(clothImg, category, color, clothTexture, clothTags, addDate, checkDate, checkTimes, likeTimes);

                DBHelper dbHelper = new DBHelper(AddClothesActivity.this);

                //wait for insert thread finish
                dbHelper.insert(clothes, new DBHelper.InsertCallback() {
                    @Override
                    public void onFinished() {

                        //refresh clothes grid list
                        ClothesListActivity.instance.refresh();

                        Toast.makeText(AddClothesActivity.this, "Add clothes successfully.", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
