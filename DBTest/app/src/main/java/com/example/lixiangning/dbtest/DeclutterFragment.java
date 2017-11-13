package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class DeclutterFragment extends Fragment {

    private ArrayList<ArrayList<String>> styleList;
    private BaseAdapter mAdapter;
    private ArrayList<DeclutterGrid> gridList = new ArrayList<>();
    private DBHelper dbHelper;
    GridView grid_declutter;
    Uri imgUri;
    private ArrayList<ArrayList<String>> clothList;
    Button btn_keep;
    Button btn_delete;
    Button btn_think;
    TextView noti;
    DBHelper helper;
    ImageView imageView;
    public static DeclutterFragment instance = null;
    private int clothId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_declutter,container,false);
        instance = this;

        dbHelper = new DBHelper(view.getContext());
        grid_declutter = (GridView) view.findViewById(R.id.grid_declutter);

//        String notification = "Haven't check it for a long time.";
//
//        Uri imgUri = Uri.parse("content://com.xn.customview.fileprovider/images/1509845443893.jpg");
//
//        gridList.add(new DeclutterGrid(imgUri, "258", notification));
//        gridList.add(new DeclutterGrid(imgUri, "258", notification));
//        gridList.add(new DeclutterGrid(imgUri, "258", notification));

        dbHelper.getAllDeclutter(new DBHelper.GetAllDeclutterCallback() {

            @Override
            public void getAllDeclutter(ArrayList<ArrayList<String>> data) {

                clothList = data;

                //add grid items
                for(int i = 0; i < clothList.size(); i++) {
                    ArrayList<String> thisCloth = clothList.get(i); //current cloth

                    // your codes
                    // choose notification
                    // if(...) {next 3 lines}

                    String notification = "Haven't checked this item for a long time.";
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    gridList.add(new DeclutterGrid(imgUri, thisCloth.get(0).toString(), notification));

                }

                mAdapter = new ClothGridAdapter<DeclutterGrid>(gridList, R.layout.list_declutter) {
                    @Override
                    public void bindView(ViewHolder holder, DeclutterGrid obj) {
                        holder.setImageResource(R.id.img_home_de, obj.getDeclutterImage());
                        holder.setText(R.id.text_home_noti, obj.getNotification());
                        btn_keep = holder.getItemView().findViewById(R.id.btn_keep);
                        btn_delete = holder.getItemView().findViewById(R.id.btn_delete);
                        btn_think = holder.getItemView().findViewById(R.id.btn_think);

                        btn_keep.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //example
                                LocalDate localDate = LocalDate.now();//For reference
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String currentDate = localDate.format(formatter);
                                String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {
                                        noti = holder.getItemView().findViewById(R.id.text_noti);
                                        noti.setText("You kept it! Love this cloth!");
                                    }
                                });
                                btn_keep.setEnabled(false);
                                btn_delete.setEnabled(false);
                                btn_think.setEnabled(false);
                            }
                        });

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //declutter
                                String sql = "delete from clothes where clothID="+ obj.getDeclutterID();
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {
                                        noti = holder.getItemView().findViewById(R.id.text_noti);
                                        noti.setText("Removed this item from the closet permanently.");
                                    }
                                });
                                btn_keep.setEnabled(false);
                                btn_delete.setEnabled(false);
                                btn_think.setEnabled(false);
                            }
                        });

                        btn_think.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //think later
                                LocalDate localDate = LocalDate.now();//For reference
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String currentDate = localDate.format(formatter);
                                String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {
                                        noti = holder.getItemView().findViewById(R.id.text_noti);
                                        noti.setText("You'll be reminded in 30 days ;)");
                                    }
                                });
                                btn_keep.setEnabled(false);
                                btn_delete.setEnabled(false);
                                btn_think.setEnabled(false);
                            }
                        });

                        imageView = holder.getItemView().findViewById(R.id.img_home_de);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clothId = Integer.parseInt(obj.getDeclutterID());
                                if(ClothesListActivity.instance != null) {
                                    ClothesListActivity.instance.setClothId(clothId);
                                }
                                Intent intent = new Intent(getActivity(), ClothDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                };

                grid_declutter.setAdapter(mAdapter);

            }
        });

        return view;
    }

    public int getDeclutterId() {
        return this.clothId;
    }

    //This needs further review
    public void refresh() {
        dbHelper.getAllDeclutter(new DBHelper.GetAllDeclutterCallback() {
            @Override
            public void getAllDeclutter(ArrayList<ArrayList<String>> data) {

                clothList = data;

                //add grid items
                for(int i = 0; i < clothList.size(); i++) {
                    ArrayList<String> thisCloth = clothList.get(i); //current cloth

                    // your codes
                    // choose notification
                    // if(...) {next 3 lines}

                    String notification = "Haven't checked this item for a long time.";
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    gridList.add(new DeclutterGrid(imgUri, thisCloth.get(0).toString(), notification));

                }

                //reload the adapter
                BaseAdapter adapter = new ClothGridAdapter<DeclutterGrid>(gridList, R.layout.list_declutter){
                    @Override
                    public void bindView(ViewHolder holder, DeclutterGrid obj) {

                    }
                };
                grid_declutter.invalidateViews();
                grid_declutter.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}