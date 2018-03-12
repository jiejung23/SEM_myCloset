package com.example.lixiangning.dbtest;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class ClothesListActivity extends AppCompatActivity {

    public static ClothesListActivity instance = null;

    private static final String TAG = "tag------";

    private int clothId = 0;

    private String category;


    private ArrayList<ArrayList<String>> clothList; //clothes list from database
    private ArrayList<ArrayList<String>> clothList2;
    private ArrayList<ArrayList<String>> clothList3;
    private ArrayList<ArrayList<String>> clothList4;
    private ArrayList<ArrayList<String>> clothList5;
    private BaseAdapter mAdapter; //grid view adapter
    private BaseAdapter mAdapter2;
    private BaseAdapter mAdapter3;
    private BaseAdapter mAdapter4;
    private BaseAdapter mAdapter5;
    private ArrayList<ClothImageGrid> gridList = new ArrayList<>(); //grid view items
    private ArrayList<ClothImageGrid> gridList2 = new ArrayList<>();
    private ArrayList<ClothImageGrid> gridList3 = new ArrayList<>();
    private ArrayList<ClothImageGrid> gridList4 = new ArrayList<>();
    private ArrayList<ClothImageGrid> gridList5 = new ArrayList<>();
    private DBHelper dbHelper; //database class




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_list);

        dbHelper = new DBHelper(this);
        instance = this;

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_in_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClothesListActivity.this, AddClothesActivity.class);
                startActivity(intent);
            }
        });

        category = HomeFragment.instance.getCategory();

        ClothesListActivity.this.setTitle(HomeFragment.instance.getTitleCategory());


        setGridView(); //initiate grid view

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_nav, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HomeFragment.instance.setCategory(query);
                HomeFragment.instance.setSearchFrom(1);
                Intent intent = new Intent(ClothesListActivity.this, SearchActivity.class);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Initiate grid view
     */
    public void setGridView() {

        GridView grid_cloth = (GridView)findViewById(R.id.grid_clothes);

        //get all list
        if(category.equals("All Clothes")) {

            dbHelper.getAll(new DBHelper.GetAllCallback() {

                @Override
                public void getAll(ArrayList<ArrayList<String>> data) {

                    clothList = data;

                    //add grid items
                    for(int i = clothList.size() - 1; i >= 0; i--) {
                        ArrayList<String> thisCloth = clothList.get(i);

                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
//                    String[] proj = { MediaStore.Images.Media.DATA };
//                    Cursor actualimagecursor = managedQuery(imgUri,proj,null,null,null);
//                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    actualimagecursor.moveToFirst();
//                    String img_path = actualimagecursor.getString(actual_image_column_index);
//                    File file = new File(img_path);

                        gridList.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }

                    //add grid adapter
                    mAdapter = new ClothGridAdapter<ClothImageGrid>(gridList, R.layout.grid_cloth_text) {
                        @Override
                        public void bindView(ViewHolder holder, ClothImageGrid obj) {
                            holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
//                        holder.setText(R.id.grid_text_category, obj.getClothGridCategory());
//                        holder.setText(R.id.grid_text_color, obj.getClothGridColor());
                        }
                    };

                    grid_cloth.setAdapter(mAdapter);

                    //grid item listener
                    grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ClothImageGrid c = (ClothImageGrid) mAdapter.getItem(position);

                            clothId = Integer.parseInt(c.getClothGridID());

                            HomeFragment.instance.setDetailID(clothId);

                            Intent intent = new Intent(ClothesListActivity.this, ClothDetailActivity.class);
                            startActivity(intent);

                        }
                    });

                }
            });
        }

        //get catrgory list
        else if(category.equals("Tops") || category.equals("Pants") ||category.equals("Skirts")){

            dbHelper.getCategoryAll(category, new DBHelper.GetCategoryCallback() {

                @Override
                public void getCategoryAll(ArrayList<ArrayList<String>> data) {

                    clothList2 = data;

                    //add grid items
                    for(int i = clothList2.size() - 1; i >=0 ; i--) {
                        ArrayList<String> thisCloth = clothList2.get(i);
                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
                        gridList2.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }

                    //add grid adapter
                    mAdapter2 = new ClothGridAdapter<ClothImageGrid>(gridList2, R.layout.grid_cloth_text) {
                        @Override
                        public void bindView(ViewHolder holder, ClothImageGrid obj) {
                            holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                        }
                    };

                    grid_cloth.setAdapter(mAdapter2);

                    //grid item listener
                    grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ClothImageGrid c = (ClothImageGrid) mAdapter2.getItem(position);

                            clothId = Integer.parseInt(c.getClothGridID());
                            HomeFragment.instance.setDetailID(clothId);

                            Intent intent = new Intent(ClothesListActivity.this, ClothDetailActivity.class);
                            startActivity(intent);

                        }
                    });

                }
            });
        }


    }

    public int getClothId() {
        return clothId;
    }

    /**
     * Refresh grid list
     */
    public void refresh() {

        if(category.equals("All Clothes")) {
            dbHelper.getAll(new DBHelper.GetAllCallback() {
                @Override
                public void getAll(ArrayList<ArrayList<String>> data) {
                    clothList = data;
                    gridList.clear();

//                Log.e("test", "clothList list size: " + clothList.size());

                    for(int i = clothList.size() - 1; i >= 0 ; i--) {
                        ArrayList<String> thisCloth = clothList.get(i);
                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
                        gridList.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }

                    //update data with new grid list
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        else {
            dbHelper.getCategoryAll(category, new DBHelper.GetCategoryCallback() {
                @Override
                public void getCategoryAll(ArrayList<ArrayList<String>> data) {
                    clothList2 = data;
                    gridList2.clear();

                    for(int i = clothList2.size() - 1; i >= 0 ; i--) {
                        ArrayList<String> thisCloth = clothList2.get(i);
                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
                        gridList2.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }

                    //update data with new grid list
                    mAdapter2.notifyDataSetChanged();
                }
            });
        }


    }

    public void setClothId(int id) {
        this.clothId = id;
    }


    /**
     * Action bar button listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                this.overridePendingTransition(R.anim.no_slide, R.anim.slide_out_right);
                return false;
//            case R.id.action_refresh:
//                refresh();
//                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
