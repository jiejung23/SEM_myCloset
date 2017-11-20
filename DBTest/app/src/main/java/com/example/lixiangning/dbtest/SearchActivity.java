package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static SearchActivity instance = null;

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

        category = HomeFragment.instance.getCategory();

        SearchActivity.this.setTitle(HomeFragment.instance.getTitleCategory() + " > " + category);

        setGridView(); //initiate grid view

    }


    /**
     * Initiate grid view
     */
    public void setGridView() {

        GridView grid_cloth = (GridView)findViewById(R.id.grid_clothes);

        //search outside the list
        if(HomeFragment.instance.getSearchFrom() == 0) {
            dbHelper.getAll(new DBHelper.GetAllCallback() {

                @Override
                public void getAll(ArrayList<ArrayList<String>> data) {

                    clothList3 = data;

                    //add grid items
                    for(int i = 0; i < clothList3.size(); i++) {
                        int already = 0;

                        ArrayList<String> thisCloth = clothList3.get(i);

                        String[] colorTags = thisCloth.get(3).split(", ");

                        for(int j = 0; j < colorTags.length; j++) {
                            if(colorTags[j].equals(category)) {
                                already = 1;
                                Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                gridList3.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                            }
                        }

                        String[] freeTags = thisCloth.get(4).split(", ");

                        for(int j = 0; j < freeTags.length; j++) {
                            if(already != 1) {
                                if(freeTags[j].equals(category)) {
                                    already = 1;
                                    Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                    gridList3.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                                }
                            }

                        }

                    }

                    //add grid adapter
                    mAdapter3 = new ClothGridAdapter<ClothImageGrid>(gridList3, R.layout.grid_cloth_text) {
                        @Override
                        public void bindView(ViewHolder holder, ClothImageGrid obj) {
                            holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                        }
                    };

                    grid_cloth.setAdapter(mAdapter3);

                    //grid item listener
                    grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ClothImageGrid c = (ClothImageGrid) mAdapter3.getItem(position);

                            clothId = Integer.parseInt(c.getClothGridID());
                            Log.i("------Search id------", clothId+"");

                            HomeFragment.instance.setDetailID(clothId);

                            Intent intent = new Intent(SearchActivity.this, ClothDetailActivity.class);
                            startActivity(intent);

                        }
                    });

                }
            });
        }

        //search inside the list
        else {

            String cate = ClosetFragment.instance.getCategory();

            if(cate.equals("All Clothes")) {

                dbHelper.getAll(new DBHelper.GetAllCallback() {

                    @Override
                    public void getAll(ArrayList<ArrayList<String>> data) {

                        clothList4 = data;

                        for(int i = 0; i < clothList4.size(); i++) {
                            int already = 0;

                            ArrayList<String> thisCloth = clothList4.get(i);

                            String[] colorTags = thisCloth.get(3).split(", ");

                            for(int j = 0; j < colorTags.length; j++) {
                                if(colorTags[j].equals(category)) {
                                    already = 1;
                                    Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                    gridList4.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                                }
                            }

                            String[] freeTags = thisCloth.get(4).split(", ");

                            for(int j = 0; j < freeTags.length; j++) {
                                if(already != 1) {
                                    if(freeTags[j].equals(category)) {
                                        already = 1;
                                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                        gridList4.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                                    }
                                }

                            }
                        }

                        //add grid adapter
                        mAdapter4 = new ClothGridAdapter<ClothImageGrid>(gridList4, R.layout.grid_cloth_text) {
                            @Override
                            public void bindView(ViewHolder holder, ClothImageGrid obj) {
                                holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                            }
                        };

                        grid_cloth.setAdapter(mAdapter4);

                        //grid item listener
                        grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ClothImageGrid c = (ClothImageGrid) mAdapter4.getItem(position);

                                clothId = Integer.parseInt(c.getClothGridID());
                                HomeFragment.instance.setDetailID(clothId);

                                Intent intent = new Intent(SearchActivity.this, ClothDetailActivity.class);
                                startActivity(intent);

                            }
                        });

                    }
                });
            }

            else {
                dbHelper.getCategoryAll(cate, new DBHelper.GetCategoryCallback() {

                    @Override
                    public void getCategoryAll(ArrayList<ArrayList<String>> data) {

                        clothList5 = data;

                        for(int i = 0; i < clothList5.size(); i++) {
                            int already = 0;

                            ArrayList<String> thisCloth = clothList5.get(i);

                            String[] colorTags = thisCloth.get(3).split(", ");

                            for(int j = 0; j < colorTags.length; j++) {
                                if(colorTags[j].equals(category)) {
                                    already = 1;
                                    Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                    gridList5.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                                }
                            }

                            String[] freeTags = thisCloth.get(4).split(", ");

                            for(int j = 0; j < freeTags.length; j++) {
                                if(already != 1) {
                                    if(freeTags[j].equals(category)) {
                                        already = 1;
                                        Uri imgUri = Uri.parse((String)thisCloth.get(1));
                                        gridList5.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                                    }
                                }

                            }
                        }

                        //add grid adapter
                        mAdapter5 = new ClothGridAdapter<ClothImageGrid>(gridList5, R.layout.grid_cloth_text) {
                            @Override
                            public void bindView(ViewHolder holder, ClothImageGrid obj) {
                                holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                            }
                        };

                        grid_cloth.setAdapter(mAdapter5);

                        //grid item listener
                        grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ClothImageGrid c = (ClothImageGrid) mAdapter5.getItem(position);

                                clothId = Integer.parseInt(c.getClothGridID());
                                HomeFragment.instance.setDetailID(clothId);

                                Intent intent = new Intent(SearchActivity.this, ClothDetailActivity.class);
                                startActivity(intent);

                            }
                        });

                    }
                });
            }

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

                    for(int i = 0; i < clothList.size(); i++) {
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

                    for(int i = 0; i < clothList2.size(); i++) {
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
