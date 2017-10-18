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
    private static Connection con;
    private MyArrayList clothList;
    private BaseAdapter mAdapter;
    private ArrayList<ClothImageGrid> gridList = new ArrayList<>();;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_list);

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


        setGridView();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void setGridView() {
        GridView grid_cloth = (GridView)findViewById(R.id.grid_clothes);



//        gridList.add(new ClothImageGrid(R.mipmap.ic_launcher));

        DBHelper dbHelper = new DBHelper();
        clothList = dbHelper.getAll();

        for(int i = 0; i < clothList.getvalue().size(); i++) {
            ArrayList<String> thisCloth = clothList.getvalue().get(i);
//            gridList.add(new ClothImageGrid(thisCloth.get(0), thisCloth.get(1), thisCloth.get(2)));
            gridList.add(new ClothImageGrid(R.mipmap.ic_launcher_round, thisCloth.get(0), thisCloth.get(1), thisCloth.get(2)));
        }

        mAdapter = new ClothGridAdapter<ClothImageGrid>(gridList, R.layout.grid_cloth_text) {
            @Override
            public void bindView(ViewHolder holder, ClothImageGrid obj) {
                holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
//                holder.setText(R.id.grid_text_id, obj.getClothGridID());
                holder.setText(R.id.grid_text_category, obj.getClothGridCategory());
                holder.setText(R.id.grid_text_color, obj.getClothGridColor());
            }
        };

        grid_cloth.setAdapter(mAdapter);

        grid_cloth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClothImageGrid c = (ClothImageGrid) mAdapter.getItem(position);
                Toast.makeText(ClothesListActivity.this, "Cloth ID: " + c.getClothGridID(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void refresh() {
        DBHelper dbHelper = new DBHelper();
        clothList = dbHelper.getAll();
        gridList.clear();

        for(int i = 0; i < clothList.getvalue().size(); i++) {
            ArrayList<String> thisCloth = clothList.getvalue().get(i);
            gridList.add(new ClothImageGrid(R.mipmap.ic_launcher_round, thisCloth.get(0), thisCloth.get(1), thisCloth.get(2)));
        }

        mAdapter.notifyDataSetChanged();
//        setGridView();
    }


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
