package com.example.lixiangning.dbtest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<ColorList> mList;
    private Context mContext;

    public SpinnerAdapter(Context pContext, List<ColorList> pList) {
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
        convertView = _LayoutInflater.inflate(R.layout.list_color_spinner, null);
        if(convertView != null) {
            TextView textColorValue = (TextView)convertView.findViewById(R.id.text_colorValue);
            TextView textColorName = (TextView)convertView.findViewById(R.id.text_colorName);
            String i = mList.get(position).getColorValue();
            int c = Color.parseColor(mList.get(position).getColorValue());
            textColorValue.setBackgroundColor(Color.parseColor(mList.get(position).getColorValue()));

            textColorName.setText(mList.get(position).getColorName());
        }
        return convertView;
    }
}
