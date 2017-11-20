package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
    private TextView no_declutter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_declutter,container,false);
        instance = this;

        dbHelper = new DBHelper(view.getContext());
        grid_declutter = (GridView) view.findViewById(R.id.grid_declutter);
        no_declutter = (TextView) view.findViewById(R.id.text_no_declutter);

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

                if(clothList.size() == 0) {
                    no_declutter.setVisibility(View.VISIBLE);
                } else {
                    no_declutter.setVisibility(View.GONE);
                }

                Log.i("---Declutter num---", clothList.size() + "");



                //add grid items
                for(int i = 0; i < clothList.size(); i++) {
                    ArrayList<String> thisCloth = clothList.get(i); //current cloth
                    String notification = "Haven't checked this item for a long time.";
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    gridList.add(new DeclutterGrid(imgUri, thisCloth.get(0).toString(), notification));
                }

                mAdapter = new ClothGridAdapter<DeclutterGrid>(gridList, R.layout.list_declutter) {
                    @Override
                    public void bindView(ViewHolder holder, DeclutterGrid obj) {
                        holder.setImageResource(R.id.img_home_de, obj.getDeclutterImage());
                        holder.setText(R.id.text_noti, obj.getNotification());
                        btn_keep = holder.getItemView().findViewById(R.id.btn_keep);
                        btn_delete = holder.getItemView().findViewById(R.id.btn_delete);
                        btn_think = holder.getItemView().findViewById(R.id.btn_think);

                        btn_keep.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date curDate = new Date(System.currentTimeMillis());
                                String currentDate = sdf.format(curDate);
                                String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {
                                        noti = holder.getItemView().findViewById(R.id.text_noti);
                                        noti.setText("You kept it! Love this cloth!");
                                        btn_keep.setEnabled(false);
                                        btn_delete.setEnabled(false);
                                        btn_think.setEnabled(false);

                                        btn_keep.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                        btn_delete.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                        btn_think.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                        btn_keep.setTextColor(Color.parseColor("#9e9e9e"));
                                        btn_delete.setTextColor(Color.parseColor("#9e9e9e"));
                                        btn_think.setTextColor(Color.parseColor("#9e9e9e"));
                                    }
                                });

                            }
                        });

                        btn_think.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date curDate = new Date(System.currentTimeMillis());
                                String currentDate = sdf.format(curDate);
                                String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                    @Override
                                    public void onFinished() {
                                        noti = holder.getItemView().findViewById(R.id.text_noti);
                                        noti.setText("You'll be reminded in 30 days ;)");
                                        btn_keep.setEnabled(false);
                                        btn_delete.setEnabled(false);
                                        btn_think.setEnabled(false);

                                        btn_keep.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                        btn_delete.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                        btn_think.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                        btn_keep.setTextColor(Color.parseColor("#9e9e9e"));
                                        btn_delete.setTextColor(Color.parseColor("#9e9e9e"));
                                        btn_think.setTextColor(Color.parseColor("#9e9e9e"));
                                    }
                                });
                            }
                        });


                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HomeFragment.instance.setDeclutterID(Integer.parseInt(obj.getDeclutterID()));

                                Intent intent = new Intent(getActivity(), DeclutterDetailActivity.class);
                                startActivity(intent);
                            }
                        });

                        imageView = holder.getItemView().findViewById(R.id.img_home_de);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clothId = Integer.parseInt(obj.getDeclutterID());
                                HomeFragment.instance.setDetailID(clothId);

                                Intent intent = new Intent(getActivity(), ClothDetailActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                };

                grid_declutter.setAdapter(mAdapter);

            }
        });

//        dbHelper.getAllDeclutter(new DBHelper.GetAllDeclutterCallback() {
//
//            @Override
//            public void getAllDeclutter(ArrayList<ArrayList<String>> data) {
//
//                clothList = data;
//
//                //add grid items
//                for(int i = 0; i < clothList.size(); i++) {
//                    ArrayList<String> thisCloth = clothList.get(i); //current cloth
//
//                    // your codes
//                    // choose notification
//                    // if(...) {next 3 lines}
//
//                    String notification = "Haven't checked this item for a long time.";
//                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
//                    gridList.add(new DeclutterGrid(imgUri, thisCloth.get(0).toString(), notification));
//
//                }
//
//                mAdapter = new ClothGridAdapter<DeclutterGrid>(gridList, R.layout.list_declutter) {
//                    @Override
//                    public void bindView(ViewHolder holder, DeclutterGrid obj) {
//                        holder.setImageResource(R.id.img_home_de, obj.getDeclutterImage());
//                        holder.setText(R.id.text_noti, obj.getNotification());
//                        btn_keep = holder.getItemView().findViewById(R.id.btn_keep);
//                        btn_delete = holder.getItemView().findViewById(R.id.btn_delete);
//                        btn_think = holder.getItemView().findViewById(R.id.btn_think);
//
//                        btn_keep.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //example
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                Date curDate = new Date(System.currentTimeMillis());
//                                String currentDate = sdf.format(curDate);
//                                String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
//                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
//                                    @Override
//                                    public void onFinished() {
//                                        noti = holder.getItemView().findViewById(R.id.text_noti);
//                                        noti.setText("You kept it! Love this cloth!");
//                                        btn_keep.setEnabled(false);
//                                        btn_delete.setEnabled(false);
//                                        btn_think.setEnabled(false);
//                                    }
//                                });
//
//                            }
//                        });
//
//                        btn_delete.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //declutter
//                                String sql = "delete from clothes where clothID="+ obj.getDeclutterID();
//                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
//                                    @Override
//                                    public void onFinished() {
//                                        noti = holder.getItemView().findViewById(R.id.text_noti);
//                                        noti.setText("Removed this item from the closet permanently.");
//                                        btn_keep.setEnabled(false);
//                                        btn_delete.setEnabled(false);
//                                        btn_think.setEnabled(false);
//                                        btn_keep.setClickable(false);
//                                        btn_delete.setClickable(false);
//                                        btn_think.setClickable(false);
//                                        btn_keep.setBackgroundColor(Color.parseColor("#e0e0e0"));
//                                        btn_delete.setBackgroundColor(Color.parseColor("#e0e0e0"));
//                                        btn_think.setBackgroundColor(Color.parseColor("#e0e0e0"));
//                                    }
//                                });
//                            }
//                        });
//
//                        btn_think.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                Date curDate = new Date(System.currentTimeMillis());
//                                String currentDate = sdf.format(curDate);
//                                String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
//                                dbHelper.update(sql, new DBHelper.UpdateCallback() {
//                                    @Override
//                                    public void onFinished() {
//                                        noti = holder.getItemView().findViewById(R.id.text_noti);
//                                        noti.setText("You'll be reminded in 30 days ;)");
//                                        btn_keep.setEnabled(false);
//                                        btn_delete.setEnabled(false);
//                                        btn_think.setEnabled(false);
//                                    }
//                                });
//                            }
//                        });
//
//                        imageView = holder.getItemView().findViewById(R.id.img_home_de);
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                clothId = Integer.parseInt(obj.getDeclutterID());
//                                if(ClothesListActivity.instance != null) {
//                                    ClothesListActivity.instance.setClothId(clothId);
//                                }
//                                Intent intent = new Intent(getActivity(), ClothDetailActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//                    }
//                };
//
//                grid_declutter.setAdapter(mAdapter);
//
//            }
//        });

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
                Log.i("------Declutter------", "Refresh Declutter Page.");

                gridList.clear();

                dbHelper.getAllDeclutter(new DBHelper.GetAllDeclutterCallback() {

                    @Override
                    public void getAllDeclutter(ArrayList<ArrayList<String>> data) {

                        clothList = data;

                        if(clothList.size() == 0) {
                            no_declutter.setVisibility(View.VISIBLE);
                        } else {
                            no_declutter.setVisibility(View.GONE);
                        }

                        Log.i("---Declutter num---", clothList.size() + "");

                        //add grid items
                        for(int i = 0; i < clothList.size(); i++) {
                            ArrayList<String> thisCloth = clothList.get(i); //current cloth
                            String notification = "Haven't checked this item for a long time.";
                            Uri imgUri = Uri.parse((String) thisCloth.get(1));
                            gridList.add(new DeclutterGrid(imgUri, thisCloth.get(0).toString(), notification));
                        }

                        mAdapter = new ClothGridAdapter<DeclutterGrid>(gridList, R.layout.list_declutter) {
                            @Override
                            public void bindView(ViewHolder holder, DeclutterGrid obj) {
                                holder.setImageResource(R.id.img_home_de, obj.getDeclutterImage());
                                holder.setText(R.id.text_noti, obj.getNotification());
                                btn_keep = holder.getItemView().findViewById(R.id.btn_keep);
                                btn_delete = holder.getItemView().findViewById(R.id.btn_delete);
                                btn_think = holder.getItemView().findViewById(R.id.btn_think);

                                btn_keep.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date curDate = new Date(System.currentTimeMillis());
                                        String currentDate = sdf.format(curDate);
                                        String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                        dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                            @Override
                                            public void onFinished() {
                                                noti = holder.getItemView().findViewById(R.id.text_noti);
                                                noti.setText("You kept it! Love this cloth!");
                                                btn_keep.setEnabled(false);
                                                btn_delete.setEnabled(false);
                                                btn_think.setEnabled(false);

                                                btn_keep.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                                btn_delete.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                                btn_think.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                                btn_keep.setTextColor(Color.parseColor("#9e9e9e"));
                                                btn_delete.setTextColor(Color.parseColor("#9e9e9e"));
                                                btn_think.setTextColor(Color.parseColor("#9e9e9e"));
                                            }
                                        });

                                    }
                                });

                                btn_think.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date curDate = new Date(System.currentTimeMillis());
                                        String currentDate = sdf.format(curDate);
                                        String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + obj.getDeclutterID();
                                        dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                            @Override
                                            public void onFinished() {
                                                noti = holder.getItemView().findViewById(R.id.text_noti);
                                                noti.setText("You'll be reminded in 30 days ;)");
                                                btn_keep.setEnabled(false);
                                                btn_delete.setEnabled(false);
                                                btn_think.setEnabled(false);

                                                btn_keep.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                                btn_delete.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                                btn_think.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                                btn_keep.setTextColor(Color.parseColor("#9e9e9e"));
                                                btn_delete.setTextColor(Color.parseColor("#9e9e9e"));
                                                btn_think.setTextColor(Color.parseColor("#9e9e9e"));
                                            }
                                        });
                                    }
                                });


                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HomeFragment.instance.setDeclutterID(Integer.parseInt(obj.getDeclutterID()));

                                        Intent intent = new Intent(getActivity(), DeclutterDetailActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                imageView = holder.getItemView().findViewById(R.id.img_home_de);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clothId = Integer.parseInt(obj.getDeclutterID());
                                        HomeFragment.instance.setDetailID(clothId);

                                        Intent intent = new Intent(getActivity(), ClothDetailActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        };

                        grid_declutter.setAdapter(mAdapter);

                    }
                });


            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}