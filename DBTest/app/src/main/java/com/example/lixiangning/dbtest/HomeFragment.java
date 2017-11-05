package com.example.lixiangning.dbtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class HomeFragment extends Fragment {

    public static HomeFragment instance = null;

    private int searchFrom = 0;
    private String category;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        instance = this;

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(int from) {
        this.searchFrom = from;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
