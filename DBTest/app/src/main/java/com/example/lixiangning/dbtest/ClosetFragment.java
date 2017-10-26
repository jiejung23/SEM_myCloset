package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class ClosetFragment extends Fragment {

    private TextView topsCount;
    private TextView pantsCount;
    private TextView skirtsCount;
    private TextView allCount;

    final String TAG = "TAG ------------";

    int allNumber = 0;

    private String category;

    private View view;

    private DBHelper dbHelper; //database class

    public static ClosetFragment instance = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_closet,container,false);
        instance = this;

        ImageView imgAll = view.findViewById(R.id.img_all);
        imgAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "All Clothes";
                Intent intent = new Intent(getActivity(), ClothesListActivity.class);
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView imgTops = view.findViewById(R.id.img_tops);
        imgTops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Tops";
                Intent intent = new Intent(getActivity(), ClothesListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView imgPants = view.findViewById(R.id.img_pants);
        imgPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Pants";
                Intent intent = new Intent(getActivity(), ClothesListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView imgSkirts = view.findViewById(R.id.img_skirt);
        imgSkirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Skirts";
                Intent intent = new Intent(getActivity(), ClothesListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClothesActivity.class);
                startActivity(intent);
            }
        });

        topsCount = view.findViewById(R.id.text_tops_count);
        pantsCount = view.findViewById(R.id.text_pants_count);
        skirtsCount = view.findViewById(R.id.text_skirt_count);
        allCount = view.findViewById(R.id.text_all_count);

        dbHelper = new DBHelper(getActivity());


        dbHelper.getAllCount(new DBHelper.CountAllCallback() {
            @Override
            public void getAllCount(int number) {
                allCount.setText(number+"");
            }
        });

        dbHelper.getCount("Tops", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get tops number" + number);
                topsCount.setText(number+"");
            }
        });


        dbHelper.getCount("Pants", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get pants number" + number);
                pantsCount.setText(number+"");
            }
        });

        dbHelper.getCount("Skirts", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get skirts number" + number);
                skirtsCount.setText(number+"");
            }
        });

        return view;
    }


    /**
     * Refresh grid list
     */
    public void refresh() {

        topsCount = view.findViewById(R.id.text_tops_count);
        pantsCount = view.findViewById(R.id.text_pants_count);
        skirtsCount = view.findViewById(R.id.text_skirt_count);
        allCount = view.findViewById(R.id.text_all_count);

        dbHelper = new DBHelper(getActivity());


        dbHelper.getAllCount(new DBHelper.CountAllCallback() {
            @Override
            public void getAllCount(int number) {
                allCount.setText(number+"");
            }
        });

        dbHelper.getCount("Tops", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get tops number" + number);
                topsCount.setText(number+"");
            }
        });


        dbHelper.getCount("Pants", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get pants number" + number);
                pantsCount.setText(number+"");
            }
        });

        dbHelper.getCount("Skirts", new DBHelper.CountCallback() {
            @Override
            public void getCount(int number) {
                Log.i("Count-------------", "get skirts number" + number);
                skirtsCount.setText(number+"");
            }
        });

        allCount.setText(allNumber + "");

    }


    public String getCategory() {
        return category;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
