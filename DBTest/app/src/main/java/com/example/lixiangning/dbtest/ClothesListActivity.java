package com.example.lixiangning.dbtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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


    private ArrayList<ArrayList<String>> clothList; //clothes list from database
    private BaseAdapter mAdapter; //grid view adapter
    private ArrayList<ClothImageGrid> gridList = new ArrayList<>(); //grid view items
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

        setGridView(); //initiate grid view

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Initiate grid view
     */
    public void setGridView() {

        GridView grid_cloth = (GridView)findViewById(R.id.grid_clothes);

        dbHelper.getAll(new DBHelper.GetAllCallback() {

            @Override
            public void getAll(ArrayList<ArrayList<String>> data) {

                clothList = data;

                //add grid items
                for(int i = 0; i < clothList.size(); i++) {
                    ArrayList<String> thisCloth = clothList.get(i);
                    gridList.add(new ClothImageGrid(R.mipmap.ic_launcher_round, thisCloth.get(0), thisCloth.get(1), thisCloth.get(2)));
                }

                //add grid adapter
                mAdapter = new ClothGridAdapter<ClothImageGrid>(gridList, R.layout.grid_cloth_text) {
                    @Override
                    public void bindView(ViewHolder holder, ClothImageGrid obj) {
                        holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                        holder.setText(R.id.grid_text_category, obj.getClothGridCategory());
                        holder.setText(R.id.grid_text_color, obj.getClothGridColor());
                    }
                };

                grid_cloth.setAdapter(mAdapter);

                //grid item listener
                grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ClothImageGrid c = (ClothImageGrid) mAdapter.getItem(position);
                        Toast.makeText(ClothesListActivity.this, "Cloth ID: " + c.getClothGridID(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


    /**
     * Refresh grid list
     */
    public void refresh() {

        dbHelper.getAll(new DBHelper.GetAllCallback() {
            @Override
            public void getAll(ArrayList<ArrayList<String>> data) {
                clothList = data;
                gridList.clear();

//                Log.e("test", "clothList list size: " + clothList.size());

                for(int i = 0; i < clothList.size(); i++) {
                    ArrayList<String> thisCloth = clothList.get(i);
                    gridList.add(new ClothImageGrid(R.mipmap.ic_launcher_round, thisCloth.get(0), thisCloth.get(1), thisCloth.get(2)));
                }

                //update data with new grid list
                mAdapter.notifyDataSetChanged();
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
            case R.id.action_refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
