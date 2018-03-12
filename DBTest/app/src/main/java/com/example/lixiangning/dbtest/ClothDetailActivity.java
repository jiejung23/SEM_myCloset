package com.example.lixiangning.dbtest;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClothDetailActivity extends AppCompatActivity {
    private Spinner spin_category;
    private String clothCategory;
    private String clothColor;
    private String clothColorLabel;
    private String clothTags;
    private int rValue;
    private int gValue;
    private int bValue;

    private float hue = (float)0.0;
    private float saturation = (float)1.0;
    private float brightness = (float)1.0;

    private int colorClicked = 0;

    private String clothAddDate;
    private Uri imgUri;
    private int clothId;
    private int clothCategoryPosition;

    private LinearLayout detail_main;
    private ImageView imgCamera;
    private TextView textAddDate;
    private Button btn_save;
    private TextView text_choose_color;
    private EditText text_tag;
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;
    private LinearLayout color_picker;
    private EditText colorTags;

    private final String[] categoryMap = {"Tops","Skirts","Pants"};

    private DBHelper dbHelper; //database class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_detail);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ClothDetailActivity.this.setTitle("Details");

        btn_save = (Button)findViewById(R.id.btn_detail_addClothes);
        detail_main = (LinearLayout) findViewById(R.id.detail_main);

        detail_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                detail_main.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text_tag.getWindowToken(), 0);
                return false;
            }
        });

        clothId = HomeFragment.instance.getDetailID();

        imgCamera = (ImageView)findViewById(R.id.img_detail_clothes);
        text_tag = (EditText) findViewById(R.id.editText_detail_tages);
        textAddDate = (TextView)findViewById(R.id.text_detail_add_value);
        text_choose_color = (TextView) findViewById(R.id.text_detail_choose_color);
        color_picker = (LinearLayout) findViewById(R.id.color_picker);
        spin_category = (Spinner) findViewById(R.id.spin_detail_category);
        seekR = (SeekBar) findViewById(R.id.seekBar_R);
        seekG = (SeekBar) findViewById(R.id.seekBar_G);
        seekB = (SeekBar) findViewById(R.id.seekBar_B);
        colorTags = (EditText) findViewById(R.id.editText_detail_color_tages);

        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hue = (float)progress * 359 / 100;
                float[] hsbArray = {hue, saturation, brightness};
                float[] rgbArray = hsb2rgb(hsbArray);
                rValue = (int)rgbArray[0];
                gValue = (int)rgbArray[1];
                bValue = (int)rgbArray[2];

                if((saturation <= 0.2 && brightness >= 0.9) || (brightness >= 0.7 && saturation <= 0.05)) {
                    text_choose_color.setTextColor(Color.rgb(66,66,66));
                    btn_save.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_save.setTextColor(Color.rgb(255,255,255));
                }

                btn_save.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
                text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
            }
        });

        seekG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sat = (float)progress / 100;
                saturation = 1 - sat;
                float[] hsbArray = {hue, saturation, brightness};
                float[] rgbArray = hsb2rgb(hsbArray);
                rValue = (int)rgbArray[0];
                gValue = (int)rgbArray[1];
                bValue = (int)rgbArray[2];

                if((saturation <= 0.2 && brightness >= 0.9) || (brightness >= 0.7 && saturation <= 0.05)) {
                    text_choose_color.setTextColor(Color.rgb(66,66,66));
                    btn_save.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_save.setTextColor(Color.rgb(255,255,255));
                }

                btn_save.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
                text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
            }
        });

        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float bri = (float)progress / 100;
                brightness = 1 - bri;
                float[] hsbArray = {hue, saturation, brightness};
                float[] rgbArray = hsb2rgb(hsbArray);
                rValue = (int)rgbArray[0];
                gValue = (int)rgbArray[1];
                bValue = (int)rgbArray[2];

                if((saturation <= 0.2 && brightness >= 0.9) || (brightness >= 0.7 && saturation <= 0.05)) {
                    text_choose_color.setTextColor(Color.rgb(66,66,66));
                    btn_save.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_save.setTextColor(Color.rgb(255,255,255));
                }

                btn_save.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
                text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
            }
        });

        List<CategoryList> categories = new ArrayList<CategoryList>();
        categories.add(new CategoryList("Tops"));
        categories.add(new CategoryList("Skirts"));
        categories.add(new CategoryList("Pants"));
        CategorySpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(this, categories);
        spin_category.setAdapter(categoryAdapter);
        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clothCategory = categories.get(position).getCategoryName();
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clothCategory = "";
            }
        });

        //color onClickListener
        text_choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colorClicked == 0) {
                    color_picker.setVisibility(View.VISIBLE);
                    text_choose_color.setText("Done");
                    colorClicked = 1;
                } else {
                    color_picker.setVisibility(View.GONE);
                    MapRGBToColor map = new MapRGBToColor(hue, saturation, brightness);
                    colorTags.setText(map.getColorName());
                    text_choose_color.setText("Click to change color");
                    colorClicked = 0;
                }
            }
        });


        dbHelper = new DBHelper(ClothDetailActivity.this);
        dbHelper.getOne(clothId, new DBHelper.GetOneCallback() {
            @Override
            public void getOne(ArrayList<String> data) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());
                String checkDate = sdf.format(curDate);
                String sql = "update clothes set checkDate='" + checkDate + "' where clothID=" + clothId;
                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                    @Override
                    public void onFinished() {
                        if(DeclutterFragment.instance != null) {
                            DeclutterFragment.instance.refresh();
                        }

                        Log.i("------Detail------", "After Refresh Declutter.");
                        Log.i("------Detail id------", clothId + "");
                        Log.i("------Detail size------", data.size() + "");

                        imgUri = Uri.parse((String)data.get(0));
                        clothCategory = data.get(1);
                        clothColor = data.get(2);
                        clothColorLabel = data.get(3);
                        rValue = Integer.parseInt(data.get(4));
                        gValue = Integer.parseInt(data.get(5));
                        bValue = Integer.parseInt(data.get(6));
                        clothTags = data.get(7);
                        clothAddDate = data.get(8);

                        imgCamera.setImageURI(imgUri);

                        colorTags.setText(clothColor);

                        Log.i("---Detail R---", rValue+"");
                        Log.i("---Detail G---", gValue+"");
                        Log.i("---Detail B---", bValue+"");

                        Log.i("---Detail Color---", clothColor);

                        text_tag.setText(clothTags);

                        textAddDate.setText("You added this cloth on " + clothAddDate);

                        for(int i = 0; i < categoryMap.length; i++) {
                            if(categoryMap[i].equals(clothCategory)) {
                                clothCategoryPosition = i;
                            }
                        }
                        spin_category.setSelection(clothCategoryPosition);

                        float[] Array = {(float)rValue, (float)gValue, (float)bValue};
                        float[] hsbArray = rgb2hsb(Array);
                        hue = hsbArray[0];
                        saturation = hsbArray[1];
                        brightness = hsbArray[2];

                        if((saturation <= 0.2 && brightness >= 0.9) || (brightness >= 0.7 && saturation <= 0.05)) {
                            text_choose_color.setTextColor(Color.rgb(66,66,66));
                            btn_save.setTextColor(Color.rgb(66,66,66));
                        } else {
                            text_choose_color.setTextColor(Color.rgb(255,255,255));
                            btn_save.setTextColor(Color.rgb(255,255,255));
                        }

                        seekR.setProgress((int)(hue * 100 / 359));
                        seekG.setProgress(100 - (int)(saturation * 100));
                        seekB.setProgress(100 - (int)(brightness * 100));

                        text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));

                        btn_save.setBackgroundColor(Color.rgb(rValue, gValue, bValue));

                        btn_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String clothImg = imgUri.toString();
                                String category = clothCategory;
                                String color = colorTags.getText().toString();
                                String tags = text_tag.getText().toString();
                                String colorLabel = clothColorLabel;
                                int colorR = rValue;
                                int colorG = gValue;
                                int colorB = bValue;


                                String sql = "update clothes set clothImg='" + clothImg +
                                        "', clothCategory='" + category +
                                        "', clothColor='" + color +
                                        "', clothColorLabel='" + colorLabel +
                                        "', clothR=" + colorR +
                                        ", clothG=" + colorG +
                                        ", clothB=" + colorB +
                                        ", clothTags='" + tags +
                                        "' where clothID=" + clothId;

                                //wait for insert thread finish
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {

                                        //refresh clothes grid list
                                        if(ClothesListActivity.instance != null) {
                                            ClothesListActivity.instance.refresh();
                                        }

                                        if(ClosetFragment.instance != null) {
                                            ClosetFragment.instance.refresh();
                                        }


                                        Toast.makeText(ClothDetailActivity.this, "Update clothes successfully.", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                });
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


    public float[] hsb2rgb(float[] hsb) {
        float[] rgb= new float[3];
        //先令饱和度和亮度为100%，调节色相h
        for(int offset=240,i=0;i<3;i++,offset-=120) {
            //算出色相h的值和三个区域中心点(即0°，120°和240°)相差多少，然后根据坐标图按分段函数算出rgb。但因为色环展开后，红色区域的中心点是0°同时也是360°，不好算，索性将三个区域的中心点都向右平移到240°再计算比较方便
            float x=Math.abs((hsb[0]+offset)%360-240);
            //如果相差小于60°则为255
            if(x<=60) rgb[i]=255;
                //如果相差在60°和120°之间，
            else if(60<x && x<120) rgb[i]=((1-(x-60)/60)*255);
                //如果相差大于120°则为0
            else rgb[i]=0;
        }
        //在调节饱和度s
        for(int i=0;i<3;i++)
            rgb[i]+=(255-rgb[i])*(1-hsb[1]);
        //最后调节亮度b
        for(int i=0;i<3;i++)
            rgb[i]*=hsb[2];
        return rgb;
    }

    public float[] rgb2hsb(float[] rgb) {
        float[] hsb = new float[3];
        float[] rearranged = rgb.clone();
        int maxIndex = 0,minIndex = 0;
        float tmp;
        //将rgb的值从小到大排列，存在rearranged数组里
        for(int i=0;i<2;i++) {
            for(int j=0;j<2-i;j++)
                if(rearranged[j]>rearranged[j+1]) {
                    tmp=rearranged[j+1];
                    rearranged[j+1]=rearranged[j];
                    rearranged[j]=tmp;
                }
        }
        //rgb的下标分别为0、1、2，maxIndex和minIndex用于存储rgb中最大最小值的下标
        for(int i=0;i<3;i++) {
            if(rearranged[0]==rgb[i]) minIndex=i;
            if(rearranged[2]==rgb[i]) maxIndex=i;
        }
        //算出亮度
        hsb[2]=rearranged[2]/255.0f;
        //算出饱和度
        hsb[1]=1-rearranged[0]/rearranged[2];
        //算出色相
        hsb[0]=maxIndex*120+60* (rearranged[1]/hsb[1]/rearranged[2]+(1-1/hsb[1])) *((maxIndex-minIndex+3)%3==1?1:-1);
        //防止色相为负值
        hsb[0]=(hsb[0]+360)%360;
        return hsb;
    }

}
