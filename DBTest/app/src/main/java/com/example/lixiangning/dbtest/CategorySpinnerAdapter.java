package com.example.lixiangning.dbtest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lixiangning on 2017/11/1.
 */

public class CategorySpinnerAdapter extends BaseAdapter {

    private List<CategoryList> mList;
    private Context mContext;

    public CategorySpinnerAdapter(Context pContext, List<CategoryList> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.list_category_spinner, null);

        if(convertView != null) {
            TextView textCategoryName = (TextView)convertView.findViewById(R.id.text_categoryName);
            textCategoryName.setText(mList.get(position).getCategoryName());
        }

        return convertView;
    }

}
