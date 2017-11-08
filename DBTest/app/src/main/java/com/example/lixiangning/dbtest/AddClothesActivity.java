package com.example.lixiangning.dbtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.kevin.crop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddClothesActivity extends AppCompatActivity implements CameraPopupWindow.OnItemClickListener {

    private Spinner spin_category;
    private String clothCategory;
    private String clothColor;
    private String clothColorLabel;
    private int rValue = 0;
    private int gValue = 0;
    private int bValue = 0;

    private float hue = (float)0.0;
    private float saturation = (float)1.0;
    private float brightness = (float)1.0;

    private int colorClicked = 0;

    private CameraPopupWindow mPop;

    private LinearLayout add_main;
    private LinearLayout color_picker;
    private ImageView imgCamera;
    private TextView notification;
    private Button btn_add;
    private TextView text_choose_color;
    private EditText text_tag;
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;

    Uri imageUri = null;
    File imageFile = null;
    Uri imageUri_in = null;
    File imageFile_in = null;

    final int TAKE_PHOTO = 1;
    final int CHOOSE_PHOTO = 2;
    final int REQUEST_TAKE_PHOTO_PERMISSION = 3;
    final int REQUEST_CROP = 4;
    final int RESULT_ERROR = 5;

    final String TAG = "tag---------";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        add_main = (LinearLayout) findViewById(R.id.add_main);
        color_picker = (LinearLayout) findViewById(R.id.color_picker);
        btn_add = (Button)findViewById(R.id.btn_addClothes);
        text_tag = (EditText) findViewById(R.id.editText_tages);
        notification = (TextView)findViewById(R.id.add_notification);
        seekR = (SeekBar) findViewById(R.id.seekBar_R);
        seekG = (SeekBar) findViewById(R.id.seekBar_G);
        seekB = (SeekBar) findViewById(R.id.seekBar_B);
        text_choose_color = (TextView) findViewById(R.id.text_choose_color);
        spin_category = (Spinner) findViewById(R.id.spin_category);
        imgCamera = (ImageView) findViewById(R.id.img_clothes);

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
                    btn_add.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_add.setTextColor(Color.rgb(255,255,255));
                }

                btn_add.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
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
                    btn_add.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_add.setTextColor(Color.rgb(255,255,255));
                }

                btn_add.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
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
                    btn_add.setTextColor(Color.rgb(66,66,66));
                } else {
                    text_choose_color.setTextColor(Color.rgb(255,255,255));
                    btn_add.setTextColor(Color.rgb(255,255,255));
                }

                btn_add.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
                text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));
            }
        });


        add_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                add_main.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text_tag.getWindowToken(), 0);
                return false;
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
                    text_choose_color.setText("Click to change color");
                    colorClicked = 0;
                }
            }
        });


        mPop = new CameraPopupWindow(this);
        mPop.setOnItemClickListener(this);

        imgCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPop.showAtLocation(AddClothesActivity.this.findViewById(R.id.add_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);

                mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
            }
        });

        //add cloth to database
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri == null) {
                    notification.setVisibility(View.VISIBLE);
                    notification.setText("Please choose a picture.");
                    return;
                }
                String clothImg = imageUri.toString();
                String category = clothCategory;
                String color = clothColor = "";
                String colorLabel = clothColorLabel = "";
                int colorR = rValue;
                int colorG = gValue;
                int colorB = bValue;
                String clothTexture = "";
                String clothTags = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());
                String addDate = sdf.format(curDate);
                String checkDate = sdf.format(curDate);
                int checkTimes = 0;
                int likeTimes = 0;

                //Map to color name
                float[] Array = {(float)rValue, (float)gValue, (float)bValue};
                float[] hsbArray = rgb2hsb(Array);
                hue = hsbArray[0];
                saturation = hsbArray[1];
                brightness = hsbArray[2];

                Log.i("---RGB-----------------", rValue + gValue + bValue + "");
                Log.i("---HSB-----------------", hue + saturation + brightness + "");


                MapRGBToColor map = new MapRGBToColor(hue, saturation, brightness);
                color = map.getColorName();


                Clothes clothes = new Clothes(clothImg, category, color, colorLabel, colorR, colorG, colorB, clothTexture, clothTags, addDate, checkDate, checkTimes, likeTimes);

                DBHelper dbHelper = new DBHelper(AddClothesActivity.this);

                //wait for insert thread finish
                dbHelper.insert(clothes, new DBHelper.InsertCallback() {
                    @Override
                    public void onFinished() {

                        if(ClothesListActivity.instance != null) {
                            //refresh clothes grid list
                            ClothesListActivity.instance.refresh();
                        }

                        ClosetFragment.instance.refresh();

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

    @Override
    public void setOnItemClick(View v) {
        switch(v.getId()){
            case R.id.btn_camera:
                if (ContextCompat.checkSelfPermission(AddClothesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //Ask for permission
                    ActivityCompat.requestPermissions(AddClothesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_TAKE_PHOTO_PERMISSION);
                } else {
                    takePhoto();
                }
                break;
            case R.id.btn_album:
                albumPhoto();
                break;
            case R.id.btn_cancel:
                mPop.dismiss();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (imageUri_in != null) {
                        startCropActivity(imageUri_in);
                    } else {
                        Toast.makeText(AddClothesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    imageFile_in = new File(imagePath);
                    imageFile_in.mkdirs();
                    imageUri_in = Uri.fromFile(imageFile_in);

                    imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg");
                    imageFile.getParentFile().mkdirs();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //Android > 7.0
                        Log.e(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N");
                        imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);
                    } else {
                        //Android < 7.0
                        imageUri = Uri.fromFile(imageFile);
                    }

                    if (imageUri_in != null) {
                        startCropActivity(imageUri_in);
                    } else {
                        Toast.makeText(AddClothesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case UCrop.REQUEST_CROP:
                imgCamera.setImageURI(imageUri);

                mPop.dismiss();
                notification.setText("");

                getRGB();
                break;

            default:
                break;
        }
    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile_in = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg");
        imageFile_in.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Android > 7.0
            Log.e(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N");
            imageUri_in = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile_in);
        } else {
            //Android < 7.0
            imageUri_in = Uri.fromFile(imageFile_in);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_in);

        imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg");
        imageFile.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Android > 7.0
            Log.e(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N");
            imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);
        } else {
            //Android < 7.0
            imageUri = Uri.fromFile(imageFile);
        }


        startActivityForResult(intent, TAKE_PHOTO);
    }


    private void albumPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


    public void startCropActivity(Uri uri) {
        UCrop.of(uri, imageUri)
                .withAspectRatio(1, 1)
                .withTargetActivity(CropActivity.class)
                .start(AddClothesActivity.this);

    }


    private void getRGB(){
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // value declaration
        int R, G, B;

        int variance = 50;
        //1. Get size of width and height in pixels

        // get middle region of the image
        int width3 = width/3;
        int height3 = height/3;
        int [] middlerangesize = new int[width3*height3];
        ArrayList middleregion = new ArrayList();
        ArrayList middleregionR = new ArrayList();
        ArrayList middleregionG = new ArrayList();
        ArrayList middleregionB = new ArrayList();
        ArrayList<String> pixel2 = new ArrayList();

        //  2. Make 2D array that gets the middle region of the image
        for(int h = height3; h<height3+height3; h++) {
            for (int w = width3; w<width3+width3; w++) {
                int pixel = bitmap.getPixel(w, h);
                // pixel2.add ("(" + bitmap.getPixel(w, h)+")");
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                //3. make count functions that counts the most frequent pixel
                int [] onepixelRGB  = {R,G,B};
                //middleregion.add(width3*h + w, {R,G,B}); // formula for converting 2D array to 1D array
                middleregionR.add(R);
                middleregionG.add(G);
                middleregionB.add(B);
                middleregion.add(onepixelRGB);

            }
        }
        int sizeoflist = middleregion.size();

        // Make 2D array
        // find most common element
        //   System.out.println("Most common" + mostCommon(middleregionR));

        for(int i=0; i<sizeoflist; i++)
        {
            pixel2.add(i,  "(" + middleregionR.get(i) + ", " + middleregionG.get(i) + ", " + middleregionB.get(i) + ")");
        }

        //-------------------------------
        Map<String, Integer> stringsCount = new HashMap<>();
        for(String s: pixel2)
        {
            Integer cc = stringsCount.get(s);
            if(cc == null) cc = new Integer(0);
            cc++;
            stringsCount.put(s,cc);
        }
        Map.Entry<String,Integer> mostRepeated = null;
        for(Map.Entry<String, Integer> e: stringsCount.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
                mostRepeated = e;
        }
        //-------------------------------
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddClothesActivity.this);

        // Title of dialog
        alertDialogBuilder.setTitle("RGB value");
        String RGBVal = mostRepeated.getKey();

        int rgblength = RGBVal.length();
        String rgbstring = RGBVal.substring(1, rgblength-1);
        String[] entireRGB = rgbstring.split(", ");
        int RGBValR = Integer.parseInt(entireRGB[0]);
        int RGBValG = Integer.parseInt(entireRGB[1]);
        int RGBValB = Integer.parseInt(entireRGB[2]);
        String message_check=  String.format("Is this the right color?", RGBValR, RGBValG, RGBValB);  // "Is this the right color?";
        // AlertDialog settings
        alertDialogBuilder

                .setMessage("RGB: " + mostRepeated + "pixels\n")

                .setCancelable(false)
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null);


        // Create Dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show dialog
        alertDialog.show();
//--------------------------------------------
        String str = mostRepeated.getKey();
        int len = str.length();
        String rgb = str.substring(1, len-1);
        String[] rgbArray = rgb.split(", ");
        rValue = Integer.parseInt(rgbArray[0]);
        gValue = Integer.parseInt(rgbArray[1]);
        bValue = Integer.parseInt(rgbArray[2]);

        //Map to color name
        float[] Array = {(float)rValue, (float)gValue, (float)bValue};
        float[] hsbArray = rgb2hsb(Array);
        hue = hsbArray[0];
        saturation = hsbArray[1];
        brightness = hsbArray[2];

        if((saturation <= 0.2 && brightness >= 0.9) || (brightness >= 0.7 && saturation <= 0.05)) {
            text_choose_color.setTextColor(Color.rgb(66,66,66));
            btn_add.setTextColor(Color.rgb(66,66,66));
        } else {
            text_choose_color.setTextColor(Color.rgb(255,255,255));
            btn_add.setTextColor(Color.rgb(255,255,255));
        }

        seekR.setProgress((int)(hue * 100 / 359));
        seekG.setProgress(100 - (int)(saturation * 100));
        seekB.setProgress(100 - (int)(brightness * 100));

        btn_add.setBackgroundColor(Color.rgb(rValue, gValue, bValue));

        text_choose_color.setBackgroundColor(Color.rgb(rValue, gValue, bValue));

        clothColorLabel = "";

        clothColor = "";

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

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void handleImageOnKitkat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri
//                    .getAuthority())) {
//                String id = docId.split(":")[1];
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri
//                    .getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"),
//                        Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            imagePath = getImagePath(uri, null);
//        }
//        displayImage(imagePath);
//        System.err.println(imagePath);
//    }


//    private String getImagePath(Uri uri, String selection) {
//        String path = null;
//        Cursor cursor = getContentResolver().query(uri, null, selection, null,
//                null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }

//    private void displayImage(String imagePath) {
//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            imgCamera.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
//        }
//    }


//    private void getDefaultImg() {
//        imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/default000.jpg");
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_img);
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput("default000.jpg", Context.MODE_PRIVATE);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.flush();
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);
//
//    }
}
