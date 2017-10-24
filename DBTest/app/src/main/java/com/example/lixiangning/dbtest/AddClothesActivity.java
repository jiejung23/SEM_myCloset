package com.example.lixiangning.dbtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddClothesActivity extends AppCompatActivity implements CameraPopupWindow.OnItemClickListener {

    private Spinner spin_color;
    private Spinner spin_category;
    private String clothCategory;
    private String clothColor;

    private CameraPopupWindow mPop;

    private ImageView imgCamera;
    private TextView notification;

    Uri imageUri = null;
    File imageFile = null;

    final int TAKE_PHOTO = 1;
    final int CHOOSE_PHOTO = 2;
    final int REQUEST_TAKE_PHOTO_PERMISSION = 3;

    final String TAG = "tag---------";


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

        imgCamera = (ImageView) findViewById(R.id.img_clothes);
        mPop = new CameraPopupWindow(this);
        mPop.setOnItemClickListener(this);


        notification = (TextView)findViewById(R.id.add_notification);


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


        Button btn_add = (Button)findViewById(R.id.btn_addClothes);

        //add cloth to database
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri == null) {
                    notification.setText("Please choose a picture.");
                    return;
                }
                String clothImg = imageUri.toString();
//                String clothImg = "";
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

                        ClosetFragment.instance.refresh();

                        Toast.makeText(AddClothesActivity.this, "Add clothes successfully.", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });

            }
        });

    }


    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg");
        imageFile.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Android > 7.0
            Log.e(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N");
            imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            //Android < 7.0
            imageUri = Uri.fromFile(imageFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }



    private void albumPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


//    private void choosePhoto() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        imageFile = new File(Environment.getExternalStorageDirectory(), "outputImage.jpg");
//        if (imageFile.exists()) {
//            imageFile.delete();
//        }
//        try {
//            imageFile.createNewFile();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //Android > 7.0
//            Log.e(TAG, "Build.VERSION.SDK_INT >= Build.VERSION_CODES.N");
//            imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            //Android < 7.0
//            imageUri = Uri.fromFile(imageFile);
//        }
//
//        intent.setType("image/*");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, CHOOSE_PHOTO);
//
//    }


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
//                Toast.makeText(getApplicationContext(),"Choose from album", Toast.LENGTH_LONG).show();
//                choosePhoto();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    imgCamera.setImageURI(Uri.fromFile(imageFile));
                    mPop.dismiss();
                    notification.setText("");
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

                    File file = new File(imagePath);
                    imageFile = file;
                    imageUri = FileProvider.getUriForFile(AddClothesActivity.this, "com.xn.customview.fileprovider", imageFile);

                    showImage(imagePath);
                    c.close();
                    mPop.dismiss();
                    notification.setText("");
                }
                break;
            default:
                break;
        }
    }

    private void showImage(String imaPath){
        Bitmap bm = BitmapFactory.decodeFile(imaPath);
        imgCamera.setImageBitmap(bm);
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
