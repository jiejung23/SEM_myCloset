package com.example.lixiangning.dbtest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

/**
 * Created by lixiangning on 2017/10/13.
 */

public class DBHelper {
    private static final String TAG = "tag------"; //log tag

    private static Connection con; //database connection

    private static int count; //count clothes in different categories

    private Context mContext; //activity context to main thread

    private String ip;
    private int port;
    private String dbName;
    private String url;
    private String user;
    private String password;


    public DBHelper(Context context) {
        mContext = context;

//        ip = "172.29.95.53";
        ip = "192.168.7.23";
        port = 3306;
        dbName = "myCloset";
        url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        user = "root";
        password = "xn1230o.";
    }


    public void insert(Clothes clothes, InsertCallback callback) {

        //insert thread
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // connect to database
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    if (con == null) {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.v(TAG, "Strat JDBC Successfully");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "Strat JDBC Unsuccessfully");
                            return;
                        }

                        // connect to JDBC
                        try {
                            con = DriverManager.getConnection(url, user, password);

                            con.setAutoCommit(true);

                            Log.i(TAG, "Connect to JDBC Successfully!");
                            break;

                        } catch (SQLException e) {
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }

                // insert
                if (con != null) {
                    int i = 0;
                    String sql = "insert into clothes(clothImg, clothCategory, clothColor, clothTexture, clothTags, addDate, checkDate, checkTimes, likeTimes) values(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstmt;
                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);

                        pstmt.setString(1, clothes.getClothImg() + "");
                        pstmt.setString(2, clothes.getClothCategory() + "");
                        pstmt.setString(3, clothes.getClothColor() + "");
                        pstmt.setString(4, clothes.getClothTexture() + "");
                        pstmt.setString(5, clothes.getDBClothTages() + "");
                        pstmt.setString(6, clothes.getDBAddDate() + "");
                        pstmt.setString(7, clothes.getDBCheckDate() + "");
                        pstmt.setString(8, clothes.getCheckTimes() + "");
                        pstmt.setString(9, clothes.getLikeTimes() + "");

                        i = pstmt.executeUpdate();

                        Log.i(TAG, "Insert the cloth: " + clothes.getClothID() + ".");
                        pstmt.close();

                        //return to main thread
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //insert finish or not
                                if (callback != null) {
                                    callback.onFinished();
                                }
                            }
                        });

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();

    }

    public void getAll(GetAllCallback getAllCallback) {

        //get all thread
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // connect to database
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    if (con == null) {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.v(TAG, "Strat JDBC Successfully");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "Strat JDBC Unsuccessfully");
                            return;
                        }

                        // connect to JDBC
                        try {
                            con = DriverManager.getConnection(url, user, password);

                            con.setAutoCommit(true);

                            Log.i(TAG, "Connect to JDBC Successfully!");
                            break;

                        } catch (SQLException e) {
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }

                // get all
                if (con != null) {
                    String sql = "select clothID, clothCategory, clothColor from clothes";
                    PreparedStatement pstmt;

                    try {
                        final ArrayList<ArrayList<String>> oneList = new ArrayList<>();
                        pstmt = (PreparedStatement)con.prepareStatement(sql);

                        ResultSet rs = pstmt.executeQuery();

//                        int col = rs.getMetaData().getColumnCount();
//                        Log.i(TAG, "col: " + col);
//                        Log.i(TAG, "1111");

                        while (rs.next()) {
                            ArrayList<String> temp = new ArrayList<>();
                            String id = rs.getInt(1)+"";
                            String img = rs.getString(2);
                            String color = rs.getString(3);
                            Log.i(TAG, "cloth: " + id + ", " + img + ", " + color);
                            temp.add(id);
                            temp.add(img);
                            temp.add(color);
                            oneList.add(temp);
                        }

//                        Log.e("ddd", "all clohtes size = " + oneList.size());

                        if (getAllCallback != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getAllCallback.getAll(oneList);
                                }
                            });
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }



    public void update(Clothes clothes) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // connect to database
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    if (con == null) {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.v(TAG, "Strat JDBC Successfully");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "Strat JDBC Unsuccessfully");
                            return;
                        }

                        // connect to JDBC
                        try {
                            con = DriverManager.getConnection(url, user, password);

                            con.setAutoCommit(true);

                            Log.i(TAG, "Connect to JDBC Successfully!");
                            break;

                        } catch (SQLException e) {
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }


                if (con != null) {
                    int i = 0;
                    String sql = "update clothes set clothImg='" + clothes.getClothImg() + "', clothCategory='" + clothes.getClothCategory() +
                            "', clothColor='" + clothes.getClothColor() +
                            "', clothTexture='" + clothes.getClothTexture() +
                            "', clothTags='" + clothes.getDBClothTages() +
                            "', addDate='" + clothes.getDBAddDate() +
                            "', checkDate='" + clothes.getDBCheckDate() +
                            "', checkTimes='" + clothes.getCheckTimes() +
                            "', likeTimes='" + clothes.getLikeTimes() + "' where clothID='" + clothes.getClothID() + "'";

                    PreparedStatement pstmt;

                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);
                        i = pstmt.executeUpdate();

                        Log.i(TAG, "Update the cloth: " + clothes.getClothID() + ".");
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            } //run
        });
        thread.start();
    }

    public Integer getCount(String category) {

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                count = 0;

                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    if (con == null) {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.v(TAG, "Strat JDBC Successfully");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "Strat JDBC Unsuccessfully");
                            return;
                        }

                        // connect to JDBC
                        try {
                            con = DriverManager.getConnection(url, user, password);

                            con.setAutoCommit(true);

                            Log.i(TAG, "Connect to JDBC Successfully!");
                            break;

                        } catch (SQLException e) {
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }


                if (con != null) {
                    String sql = "SELECT count(clothID) FROM clothes WHERE clothCategory='" + category + "'";
                    PreparedStatement pstmt;

                    try {
                        pstmt = (PreparedStatement)con.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next())
                        {
                            count = rs.getInt(1);
                        }

                        Log.i(TAG, "Number of " + category + ": " + count);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            } //run
        });

        thread.start();

        return count;

    }

    public void delete(Clothes clothes) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    if (con == null) {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Log.v(TAG, "Strat JDBC Successfully");
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "Strat JDBC Unsuccessfully");
                            return;
                        }

                        // connect to JDBC
                        try {
                            con = DriverManager.getConnection(url, user, password);

                            con.setAutoCommit(true);

                            Log.i(TAG, "Connect to JDBC Successfully!");
                            break;

                        } catch (SQLException e) {
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }

                if (con != null) {
                    int i = 0;
                    String sql = "delete from clothes where clothID='" + clothes.getClothID() + "'";
                    PreparedStatement pstmt;
                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);
                        i = pstmt.executeUpdate();

                        System.out.println("Delete the cloth: " + clothes.getClothID() + ".");
                        pstmt.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } //run
        });

        thread.start();

    }



    public interface GetAllCallback {
        public void getAll(ArrayList<ArrayList<String>> data);
    }

    public interface InsertCallback {
        public void onFinished();
    }
}
