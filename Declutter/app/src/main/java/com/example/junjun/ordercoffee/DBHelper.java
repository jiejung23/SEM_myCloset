package com.example.junjun.ordercoffee;

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
 * Created by junjun on 11/5/17.
 */
public class DBHelper {

    private static Connection con; //database connection
    private Context mContext; //activity context to main thread
    private static final String TAG = "tag------"; //log tag

    private String ip;
    private int port;
    private String dbName;
    private String url;
    private String user;
    private String password;

    public DBHelper(Context context) {
        mContext = context;


//        ip = "172.29.95.53"; //School
        ip = "172.29.93.20"; //home
        port = 3306;
        dbName = "myCloset";
        url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        user = "root";
        password = "cat";
    }


    public String getOne(int cloth_id) {
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
                            e.printStackTrace(System.err);
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                            // Permission denied - socket failed - add in manifest
                            Log.e(TAG, e.getSQLState() + e.getMessage() + e.getErrorCode() + e.getCause());
                        }

                    } else {
                        break;
                    }
                }

                // get all
                if (con != null) {
                    String sql = "select clothImg, clothCategory, clothColor, clothColorLabel, clothR, clothG, clothB, addDate from clothes where clothId=" + cloth_id;
                    PreparedStatement pstmt;

                    try {
                        ArrayList<String> oneCloth = new ArrayList<>();
                        pstmt = (PreparedStatement)con.prepareStatement(sql);

                        ResultSet rs = pstmt.executeQuery();

//                        Log.i("TAG---------", rs.getString(1));
                        while(rs.next()) {
                            String img = rs.getString(1);
                            String category = rs.getString(2);
                            String color = rs.getString(3);
                            String colorLabel = rs.getString(4);
                            String colorR = rs.getInt(5) + "";
                            String colorG = rs.getInt(6) + "";
                            String colorB = rs.getInt(7) + "";
                            String add = rs.getString(8);
                            oneCloth.add(img);
                            oneCloth.add(category);
                            oneCloth.add(color);
                            oneCloth.add(colorLabel);
                            oneCloth.add(colorR);
                            oneCloth.add(colorG);
                            oneCloth.add(colorB);
                            oneCloth.add(add);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return "Hello";
    }

    public void update(String sql) {

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

                    PreparedStatement pstmt;

                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);
                        i = pstmt.executeUpdate();

                        pstmt.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            } //run
        });
        thread.start();
    }

//    public interface GetOneCallback {
//        public void getOne(ArrayList<String> data);
//    }
}