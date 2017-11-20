package com.example.lixiangning.dbtest;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lixiangning on 2017/10/15.
 */

public class HomeFragment extends Fragment {

    public static HomeFragment instance = null;

    private int searchFrom = 0;
    private String category;
    private String titleCategory;
    private int detail_id;
    private int declutter_id;

    private ArrayList<ArrayList<String>> styleList;
    private BaseAdapter mAdapter;
    private BaseAdapter mAdapterMy;
    private ArrayList<StyleImageGrid> gridList = new ArrayList<>();
    private ArrayList<ClothImageGrid> gridListMy = new ArrayList<>();
    private DBHelper dbHelper;
    private int styleId = 0;
    GridView grid_style;
    GridView grid_my;
    Uri imgUri = null;

    private TextView no_declutter;
    private LinearLayout layout_de1;
    private LinearLayout layout_de2;
    private LinearLayout layout_de3;

    private ArrayList<ArrayList<String>> clothList;

    View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

//        getActivity().setTitle("Home");

        instance = this;

        dbHelper = new DBHelper(view.getContext());
        grid_style = (GridView) view.findViewById(R.id.grid_home_style);
        grid_my = (GridView) view.findViewById(R.id.grid_home_my);
        no_declutter = (TextView) view.findViewById(R.id.text_no_declutter);
        layout_de1 = view.findViewById(R.id.layout_de1);
        layout_de2 = view.findViewById(R.id.layout_de2);
        layout_de3 = view.findViewById(R.id.layout_de3);

        setLookGrid();
        setDeclutterGrid();
        setMyGrid();

        return view;
    }

    private void setLookGrid() {
        dbHelper.getAllStyle(new DBHelper.GetAllStyleCallback() {
            @Override
            public void getAllStyle(ArrayList<ArrayList<String>> data) {
                styleList = data;
                Log.i("---StyleLook Size---", styleList.size() + "");
                int i = 0;
                int j = 0;

                while(j < 3) {
                    ArrayList<String> thisCloth = styleList.get(i);
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    String id = thisCloth.get(0);
                    if(thisCloth.get(2).equals("0")) {
                        gridList.add(new StyleImageGrid(imgUri, id, Integer.parseInt(thisCloth.get(2))));
                        j++;
                    }
                    i++;
                }



                mAdapter = new ClothGridAdapter<StyleImageGrid>(gridList, R.layout.grid_cloth_text) {
                    @Override
                    public void bindView(ViewHolder holder, StyleImageGrid obj) {
                        holder.setImageResource(R.id.grid_text_img, obj.getStyleImage());
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

    private void setDeclutterGrid() {
        dbHelper.getAllDeclutter(new DBHelper.GetAllDeclutterCallback() {

            @Override
            public void getAllDeclutter(ArrayList<ArrayList<String>> data) {

                clothList = data;

                if(clothList.size() == 0) {
                    no_declutter.setVisibility(View.VISIBLE);
                }

                int n = clothList.size();

                if(n >= 1) {
                    no_declutter.setVisibility(View.GONE);
                    layout_de1.setVisibility(View.VISIBLE);
                    ArrayList<String> thisCloth = clothList.get(0);
                    String notification = "Haven't checked this item for a long time.";
                    Uri imgUri1 = Uri.parse((String) thisCloth.get(1));
                    String id1 = thisCloth.get(0);

                    ImageView img1 = (ImageView) view.findViewById(R.id.home_de1);
                    img1.setImageURI(imgUri1);
                    TextView noti1 = (TextView) view.findViewById(R.id.text_noti1);
                    noti1.setText(notification);
                    Button keep1 = (Button) view.findViewById(R.id.btn_keep1);
                    Button think1 = (Button) view.findViewById(R.id.btn_think1);
                    Button delete1 = (Button) view.findViewById(R.id.btn_delete1);

                    keep1.setEnabled(true);
                    think1.setEnabled(true);
                    delete1.setEnabled(true);

                    keep1.setClickable(true);
                    think1.setClickable(true);
                    delete1.setClickable(true);

                    keep1.setBackgroundResource(R.color.colorPrimary);
                    think1.setBackgroundResource(R.color.colorPrimary);
                    delete1.setBackgroundResource(R.color.colorAccent);

                    keep1.setTextColor(Color.parseColor("#ffffff"));
                    think1.setTextColor(Color.parseColor("#ffffff"));
                    delete1.setTextColor(Color.parseColor("#ffffff"));


                    keep1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + id1;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti1.setText("You kept it! Love this cloth!");
                                    keep1.setEnabled(false);
                                    think1.setEnabled(false);
                                    delete1.setEnabled(false);

                                    keep1.setClickable(false);
                                    think1.setClickable(false);
                                    delete1.setClickable(false);

                                    keep1.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think1.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete1.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep1.setTextColor(Color.parseColor("#9e9e9e"));
                                    think1.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete1.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    think1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + id1;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti1.setText("You'll be reminded in 30 days ;)");
                                    keep1.setEnabled(false);
                                    think1.setEnabled(false);
                                    delete1.setEnabled(false);

                                    keep1.setClickable(false);
                                    think1.setClickable(false);
                                    delete1.setClickable(false);

                                    keep1.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think1.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete1.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep1.setTextColor(Color.parseColor("#9e9e9e"));
                                    think1.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete1.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    delete1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setDeclutterID(Integer.parseInt(id1));

                            Intent intent = new Intent(getActivity(), DeclutterDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    layout_de1.setVisibility(View.GONE);
                }

                if(n >= 2) {
                    layout_de2.setVisibility(View.VISIBLE);
                    ArrayList<String> thisCloth2 = clothList.get(1);
                    String notification2 = "Haven't checked this item for a long time.";
                    Uri imgUri2 = Uri.parse((String) thisCloth2.get(1));
                    String id2 = thisCloth2.get(0);

                    ImageView img2 = (ImageView) view.findViewById(R.id.home_de2);
                    img2.setImageURI(imgUri2);
                    TextView noti2 = (TextView) view.findViewById(R.id.text_noti2);
                    noti2.setText(notification2);
                    Button keep2 = (Button) view.findViewById(R.id.btn_keep2);
                    Button think2 = (Button) view.findViewById(R.id.btn_think2);
                    Button delete2 = (Button) view.findViewById(R.id.btn_delete2);

                    keep2.setEnabled(true);
                    think2.setEnabled(true);
                    delete2.setEnabled(true);

                    keep2.setClickable(true);
                    think2.setClickable(true);
                    delete2.setClickable(true);

                    keep2.setBackgroundResource(R.color.colorPrimary);
                    think2.setBackgroundResource(R.color.colorPrimary);
                    delete2.setBackgroundResource(R.color.colorAccent);

                    keep2.setTextColor(Color.parseColor("#ffffff"));
                    think2.setTextColor(Color.parseColor("#ffffff"));
                    delete2.setTextColor(Color.parseColor("#ffffff"));

                    keep2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + id2;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti2.setText("You kept it! Love this cloth!");
                                    keep2.setEnabled(false);
                                    think2.setEnabled(false);
                                    delete2.setEnabled(false);

                                    keep2.setClickable(false);
                                    think2.setClickable(false);
                                    delete2.setClickable(false);

                                    keep2.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think2.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete2.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep2.setTextColor(Color.parseColor("#9e9e9e"));
                                    think2.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete2.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    think2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + id2;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti2.setText("You'll be reminded in 30 days ;)");
                                    keep2.setEnabled(false);
                                    think2.setEnabled(false);
                                    delete2.setEnabled(false);

                                    keep2.setClickable(false);
                                    think2.setClickable(false);
                                    delete2.setClickable(false);

                                    keep2.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think2.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete2.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep2.setTextColor(Color.parseColor("#9e9e9e"));
                                    think2.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete2.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    delete2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setDeclutterID(Integer.parseInt(id2));

                            Intent intent = new Intent(getActivity(), DeclutterDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    layout_de2.setVisibility(View.GONE);
                }

                if(n >= 3) {
                    layout_de3.setVisibility(View.VISIBLE);
                    ArrayList<String> thisCloth3 = clothList.get(2);
                    String notification3 = "Haven't checked this item for a long time.";
                    Uri imgUri3 = Uri.parse((String) thisCloth3.get(1));
                    String id3 = thisCloth3.get(0);

                    ImageView img3 = (ImageView) view.findViewById(R.id.home_de3);
                    img3.setImageURI(imgUri3);
                    TextView noti3 = (TextView) view.findViewById(R.id.text_noti3);
                    noti3.setText(notification3);
                    Button keep3 = (Button) view.findViewById(R.id.btn_keep3);
                    Button think3 = (Button) view.findViewById(R.id.btn_think3);
                    Button delete3 = (Button) view.findViewById(R.id.btn_delete3);

                    keep3.setEnabled(true);
                    think3.setEnabled(true);
                    delete3.setEnabled(true);

                    keep3.setClickable(true);
                    think3.setClickable(true);
                    delete3.setClickable(true);

                    keep3.setBackgroundResource(R.color.colorPrimary);
                    think3.setBackgroundResource(R.color.colorPrimary);
                    delete3.setBackgroundResource(R.color.colorAccent);

                    keep3.setTextColor(Color.parseColor("#ffffff"));
                    think3.setTextColor(Color.parseColor("#ffffff"));
                    delete3.setTextColor(Color.parseColor("#ffffff"));

                    keep3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set checkDate='"+ currentDate + "' where clothID=" + id3;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti3.setText("You kept it! Love this cloth!");
                                    keep3.setEnabled(false);
                                    think3.setEnabled(false);
                                    delete3.setEnabled(false);

                                    keep3.setClickable(false);
                                    think3.setClickable(false);
                                    delete3.setClickable(false);

                                    keep3.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think3.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete3.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep3.setTextColor(Color.parseColor("#9e9e9e"));
                                    think3.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete3.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    think3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);
                            String sql = "update clothes set thinkDate='"+ currentDate + "' where clothID=" + id3;
                            dbHelper.update(sql, new DBHelper.UpdateCallback() {
                                @Override
                                public void onFinished() {
                                    noti3.setText("You'll be reminded in 30 days ;)");
                                    keep3.setEnabled(false);
                                    think3.setEnabled(false);
                                    delete3.setEnabled(false);

                                    keep3.setClickable(false);
                                    think3.setClickable(false);
                                    delete3.setClickable(false);

                                    keep3.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    think3.setBackgroundColor(Color.parseColor("#e0e0e0"));
                                    delete3.setBackgroundColor(Color.parseColor("#e0e0e0"));

                                    keep3.setTextColor(Color.parseColor("#9e9e9e"));
                                    think3.setTextColor(Color.parseColor("#9e9e9e"));
                                    delete3.setTextColor(Color.parseColor("#9e9e9e"));

                                    if(DeclutterFragment.instance != null) {
                                        DeclutterFragment.instance.refresh();
                                    }
                                }
                            });
                        }
                    });
                    delete3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setDeclutterID(Integer.parseInt(id3));

                            Intent intent = new Intent(getActivity(), DeclutterDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    layout_de3.setVisibility(View.GONE);
                }











            }
        });
    }

    private void setMyGrid() {
        dbHelper.getAll(new DBHelper.GetAllCallback() {

            @Override
            public void getAll(ArrayList<ArrayList<String>> data) {

                clothList = data;

                if(clothList.size() > 3) {
                    //add grid items
                    for(int i = clothList.size()-1; i > clothList.size()-4; i--) {
                        ArrayList<String> thisCloth = clothList.get(i);

                        Uri imgUri = Uri.parse((String)thisCloth.get(1));

                        gridListMy.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }
                } else {
                    for(int i = clothList.size()-1; i > 0; i--) {
                        ArrayList<String> thisCloth = clothList.get(i);

                        Uri imgUri = Uri.parse((String)thisCloth.get(1));

                        gridListMy.add(new ClothImageGrid(imgUri, thisCloth.get(0), thisCloth.get(2), thisCloth.get(3)));
                    }
                }



                //add grid adapter
                mAdapterMy = new ClothGridAdapter<ClothImageGrid>(gridListMy, R.layout.grid_cloth_text) {
                    @Override
                    public void bindView(ViewHolder holder, ClothImageGrid obj) {
                        holder.setImageResource(R.id.grid_text_img, obj.getClothImageGrid());
                    }
                };

                grid_my.setAdapter(mAdapterMy);

                //grid item listener
                grid_my.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ClothImageGrid c = (ClothImageGrid) mAdapterMy.getItem(position);

                        detail_id = Integer.parseInt(c.getClothGridID());

                        Intent intent = new Intent(getActivity(), ClothDetailActivity.class);
                        startActivity(intent);

                    }
                });

            }
        });
        dbHelper.getAllStyle(new DBHelper.GetAllStyleCallback() {
            @Override
            public void getAllStyle(ArrayList<ArrayList<String>> data) {
                styleList = data;
                Log.i("---StyleLook Size---", styleList.size() + "");
                int i = 0;
                int j = 0;

                while(j < 3) {
                    ArrayList<String> thisCloth = styleList.get(i);
                    Uri imgUri = Uri.parse((String) thisCloth.get(1));
                    String id = thisCloth.get(0);
                    if(thisCloth.get(2).equals("0")) {
                        gridList.add(new StyleImageGrid(imgUri, id, Integer.parseInt(thisCloth.get(2))));
                        j++;
                    }
                    i++;
                }



                mAdapter = new ClothGridAdapter<StyleImageGrid>(gridList, R.layout.grid_cloth_text) {
                    @Override
                    public void bindView(ViewHolder holder, StyleImageGrid obj) {
                        holder.setImageResource(R.id.grid_text_img, obj.getStyleImage());
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

    public int getDetailID() {
        return detail_id;
    }

    public void setDetailID(int id) {
        this.detail_id = id;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String category) {
        this.titleCategory = category;
    }

    public int getDeclutterID() {
        return declutter_id;
    }

    public void setDeclutterID(int id) {
        this.declutter_id = id;
    }

    public void refresh() {
        setDeclutterGrid();
    }
}
