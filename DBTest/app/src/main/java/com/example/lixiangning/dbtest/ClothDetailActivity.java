package com.example.lixiangning.dbtest;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClothDetailActivity extends AppCompatActivity {
    private Spinner spin_color;
    private Spinner spin_category;
    private String clothCategory;
    private String clothColor;
    private String clothAddDate;
    private Uri imgUri;
    private int clothId;
    private int clothColorPosition;
    private int clothCategoryPosition;

    private ImageView imgCamera;
    private TextView textAddDate;
    private Button btn_save;

    private final String[] categoryMap = {"Tops","Skirts","Pants"};
    private final String[] colorMap = {"White","Black","Grey","Brown","Blue","Green","Purple","Yellow","Pink","Red","Orange","Other"};


    private DBHelper dbHelper; //database class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_detail);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_save = (Button)findViewById(R.id.btn_detail_addClothes);

        ClothDetailActivity.this.setTitle("Details");

        clothId = ClothesListActivity.instance.getClothId();

        imgCamera = (ImageView)findViewById(R.id.img_detail_clothes);
        textAddDate = (TextView)findViewById(R.id.text_detail_add_value);

        spin_color  = (Spinner) findViewById(R.id.spin_detail_color);
        spin_category = (Spinner) findViewById(R.id.spin_detail_category);

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
                ColorList thisColor = colors.get(position);
                btn_save.setBackgroundColor(Color.parseColor(thisColor.getColorValue()));
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clothColor = "";
            }
        });

        dbHelper = new DBHelper(ClothDetailActivity.this);



        dbHelper.getOne(clothId, new DBHelper.GetOneCallback() {
            @Override
            public void getOne(ArrayList<String> data) {
                imgUri = Uri.parse((String)data.get(0));
                clothCategory = data.get(1);
                clothColor = data.get(2);
                clothAddDate = data.get(3);
                Log.e("TAG---------", clothColor);
                imgCamera.setImageURI(imgUri);
                textAddDate.setText(clothAddDate);
                for(int i = 0; i < colorMap.length; i++) {
                    if(colorMap[i].equals(clothColor)) {
                        clothColorPosition = i;
                    }
                }
                for(int i = 0; i < categoryMap.length; i++) {
                    if(categoryMap[i].equals(clothCategory)) {
                        clothCategoryPosition = i;
                    }
                }
                spin_category.setSelection(clothCategoryPosition);
                spin_color.setSelection(clothColorPosition);
                ColorList thisColor = colors.get(clothColorPosition);
                btn_save.setBackgroundColor(Color.parseColor(thisColor.getColorValue()));

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String clothImg = imgUri.toString();
                        String category = clothCategory;
                        String color = clothColor;

                        String sql = "update clothes set clothImg='" + clothImg +
                                "', clothCategory='" + category +
                                "', clothColor='" + color +
                                "' where clothID=" + clothId;

                        //wait for insert thread finish
                        dbHelper.update(sql, new DBHelper.UpdateCallback() {
                            @Override
                            public void onFinished() {

                                //refresh clothes grid list
                                ClothesListActivity.instance.refresh();

                                ClosetFragment.instance.refresh();

                                Toast.makeText(ClothDetailActivity.this, "Update clothes successfully.", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        });
                    }
                });
            }
        });




    }


    /**
     * Action bar button listener
     */
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
