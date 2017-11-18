package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevin.crop.UCrop;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class StyleLookFragment extends Fragment implements AdapterView.OnItemClickListener , AbsListView.OnScrollListener {

    private ArrayList<ArrayList<String>> styleList;
    private BaseAdapter mAdapter;
    private ArrayList<StyleImageGrid> gridList = new ArrayList<>();
    private DBHelper dbHelper;
    private int styleId = 0;
    GridView grid_style;
    Uri imgUri;
    ImageView btn_like;

    final int CHOOSE_PHOTO = 2;
    final int AFTER_CROP = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_style_look, container, false);
        dbHelper = new DBHelper(view.getContext());
        grid_style = (GridView) view.findViewById(R.id.grid_look);

//        setLookGrid();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_in_style);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumPhoto();
            }
        });

        return view;
    }

    private void albumPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    imgUri = data.getData();
                    gridList.add(new StyleImageGrid(imgUri, "id"));

                    mAdapter = new ClothGridAdapter<StyleImageGrid>(gridList, R.layout.list_style_item) {
                        @Override
                        public void bindView(ViewHolder holder, StyleImageGrid obj) {
                            holder.setImageResource(R.id.img_style1, obj.getStyleImage());
                            btn_like = holder.getItemView().findViewById(R.id.icon_like);
                            btn_like.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), holder.getItemPosition() + "", Toast.LENGTH_SHORT).show();
                                    ImageView img_like = holder.getItemView().findViewById(R.id.icon_like);
                                    img_like.setImageResource(R.drawable.ic_favorite_white_24dp);
                                }
                            });
                        }
                    };

                    grid_style.setAdapter(mAdapter);

                    //grid item listener
                    grid_style.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            StyleImageGrid c = (StyleImageGrid) mAdapter.getItem(position);
                        }
                    });

                }
                break;

            default:
                break;
        }
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
