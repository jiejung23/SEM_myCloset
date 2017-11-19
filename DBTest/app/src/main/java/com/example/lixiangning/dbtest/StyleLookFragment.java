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
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.crop.UCrop;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lixiangning on 2017/11/9.
 */

public class StyleLookFragment extends Fragment implements AdapterView.OnItemClickListener , AbsListView.OnScrollListener {

    public static StyleLookFragment instance = null;

    private ArrayList<ArrayList<String>> styleList;
    private BaseAdapter mAdapter;
    private ArrayList<StyleImageGrid> gridList = new ArrayList<>();
    private DBHelper dbHelper;
    private int styleId = 0;
    GridView grid_style;
    Uri imgUri = null;
    ImageView btn_like;

    final int CHOOSE_PHOTO = 2;
    final int AFTER_CROP = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_style_look, container, false);
        instance = this;
        dbHelper = new DBHelper(view.getContext());
        grid_style = (GridView) view.findViewById(R.id.grid_look);

        setLookGrid();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_in_style);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumPhoto();
            }
        });

        return view;
    }

    private void setLookGrid() {
        dbHelper.getAllStyle(new DBHelper.GetAllStyleCallback() {
            @Override
            public void getAllStyle(ArrayList<ArrayList<String>> data) {
                styleList = data;
                Log.i("---StyleLook Size---", styleList.size() + "");
                for(int i = 0; i < styleList.size(); i++) {
                    ArrayList<String> thisCloth = styleList.get(i);
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    String id = thisCloth.get(0);
                    gridList.add(new StyleImageGrid(imgUri, id, Integer.parseInt(thisCloth.get(2))));
                }

                mAdapter = new ClothGridAdapter<StyleImageGrid>(gridList, R.layout.list_style_item) {
                    @Override
                    public void bindView(ViewHolder holder, StyleImageGrid obj) {
                        holder.setImageResource(R.id.img_style1, obj.getStyleImage());
                        holder.setText(R.id.text_id, obj.getStyleID());
                        btn_like = holder.getItemView().findViewById(R.id.icon_like);
                        if (obj.getStyleLike() == 1) {
                            btn_like.setImageResource(R.drawable.ic_favorite_white_24dp);
                            holder.setText(R.id.text_like, "1");
                        } else {
                            btn_like.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                            holder.setText(R.id.text_like, "0");
                        }
                        btn_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getActivity(), holder.getItemPosition() + "", Toast.LENGTH_SHORT).show();
                                ImageView img_like = holder.getItemView().findViewById(R.id.icon_like);
                                TextView text_like = holder.getItemView().findViewById(R.id.text_like);
                                TextView text_id = holder.getItemView().findViewById(R.id.text_id);
                                int thisID = Integer.parseInt(text_id.getText().toString());
                                if (text_like.getText().equals("1")) {
                                    img_like.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                                    text_like.setText("0");
                                    String sql = "update styles set styleLike=0 where styleID=" + thisID;
                                    dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                        @Override
                                        public void onFinished() {
                                            if(StyleLikeFragment.instance != null) {
                                                StyleLikeFragment.instance.refresh();
                                            }
                                        }
                                    });
                                } else {
                                    img_like.setImageResource(R.drawable.ic_favorite_white_24dp);
                                    text_like.setText("1");
                                    String sql = "update styles set styleLike=1 where styleID=" + thisID;
                                    dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                        @Override
                                        public void onFinished() {
                                            if(StyleLikeFragment.instance != null) {
                                                StyleLikeFragment.instance.refresh();
                                            }

                                        }
                                    });
                                }
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
        });
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
                    Uri uri = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContext().getContentResolver().query(uri, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    File imageFile = new File(imagePath);
                    imageFile.mkdirs();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //Android > 7.0
                        imgUri = FileProvider.getUriForFile(getActivity(), "com.xn.customview.fileprovider", imageFile);
                    } else {
                        //Android < 7.0
                        imgUri = Uri.fromFile(imageFile);
                    }

                    Styles styles = new Styles(imgUri.toString(), 0);

                    dbHelper.insertStyle(styles, new DBHelper.InsertCallback() {
                        @Override
                        public void onFinished() {
                            refresh();
                            StyleLikeFragment.instance.refresh();
                        }
                    });

                }
                break;

            default:
                break;
        }
    }

    public void refresh() {
        gridList.clear();
        setLookGrid();
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
