package com.example.lixiangning.dbtest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class StyleFragment extends Fragment {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, String>> listClothes;

    TabLayout mTablayout;
    ViewPager mViewpager;
    String[] mTitles = {"LOOKS", "LIKES"};

    private DBHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_style, container, false);

//        getActivity().setTitle("Style");

        mTablayout = (TabLayout) view.findViewById(R.id.tab_style);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);

        mViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return new StyleLookFragment();
                else
                    return new StyleLikeFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        mTablayout.setupWithViewPager(mViewpager);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
