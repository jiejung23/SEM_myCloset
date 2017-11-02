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

    private Context mContext; //activity context to main thread

    private String ip;
    private int port;
    private String dbName;
    private String url;
    private String user;
    private String password;


    public DBHelper(Context context) {
        mContext = context;

//        ip = "172.29.95.53"; //School
        ip = "192.168.7.23"; //home
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
                    String sql = "insert into clothes(" + "" +
                            "clothImg, clothCategory, clothColor, clothColorLabel, clothR, clothG, clothB, clothTexture, clothTags, addDate, checkDate, checkTimes, likeTimes) " + "" +
                            "values('" + clothes.getClothImg() + "','" + clothes.getClothCategory() + "','"
                            + clothes.getClothColor() + "','" + clothes.getClothColorLabel() + "','"
                            + clothes.getClothR() + "','" + clothes.getClothG() + "','"
                            + clothes.getClothB() + "','" + clothes.getClothTexture() + "','"
                            + clothes.getDBClothTages() + "','" + clothes.getDBAddDate() + "','"
                            + clothes.getDBCheckDate() + "'," + clothes.getCheckTimes() + ","
                            + clothes.getLikeTimes() + ")";
                    PreparedStatement pstmt;
                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);

//                        pstmt.setString(1, clothes.getClothImg());
//                        pstmt.setString(2, clothes.getClothCategory());
//                        pstmt.setString(3, clothes.getClothColor());
//                        pstmt.setString(4, clothes.getClothTexture());
//                        pstmt.setString(5, clothes.getDBClothTages());
//                        pstmt.setString(6, clothes.getDBAddDate());
//                        pstmt.setString(7, clothes.getDBCheckDate());
//                        pstmt.setString(8, clothes.getCheckTimes() + "");
//                        pstmt.setString(9, clothes.getLikeTimes() + "");

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
                    String sql = "select clothID, clothImg, clothCategory, clothColor from clothes";
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
                            String category = rs.getString(3);
                            String color = rs.getString(4);
                            Log.i(TAG, "cloth: " + id + ", " + img + ", " + category + "," + color);
                            temp.add(id);
                            temp.add(img);
                            temp.add(category);
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


    public void getCount(String category, CountCallback countCallback) {

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

                    String sql = "SELECT count(clothID) FROM clothes WHERE clothCategory='" + category + "'";
                    PreparedStatement pstmt;


                    try {
                        pstmt = (PreparedStatement)con.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery();
                        int count = 0;
                        if (rs.next())
                        {
                            count = rs.getInt(1);
                        }

                        Log.i(TAG, "Number of " + category + ": " + count);

                        if (countCallback != null) {
                            int finalCount = count;
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countCallback.getCount(finalCount);
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


    public void getAllCount(CountAllCallback countAllCallback) {

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

                    String sql = "SELECT count(clothID) FROM clothes";
                    PreparedStatement pstmt;


                    try {
                        pstmt = (PreparedStatement)con.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery();
                        int count = 0;
                        if (rs.next())
                        {
                            count = rs.getInt(1);
                        }


                        if (countAllCallback != null) {
                            int finalCount = count;
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countAllCallback.getAllCount(finalCount);
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


    public void getOne(int cloth_id, GetOneCallback getOneCallback) {

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

                        //return to main thread
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //insert finish or not
                                if (getOneCallback != null) {
                                    getOneCallback.getOne(oneCloth);
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


    public void update(String sql, UpdateCallback callback) {

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

            } //run
        });
        thread.start();
    }


    public void getCategoryAll(String category, GetCategoryCallback getCategoryCallback) {

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
                    String sql = "select clothID, clothImg, clothCategory, clothColor from clothes where clothCategory='" + category + "'";
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
                            String category = rs.getString(3);
                            String color = rs.getString(4);
                            Log.i(TAG, "cloth: " + id + ", " + img + ", " + category + "," + color);
                            temp.add(id);
                            temp.add(img);
                            temp.add(category);
                            temp.add(color);
                            oneList.add(temp);
                        }

//                        Log.e("ddd", "all clohtes size = " + oneList.size());

                        if (getCategoryCallback != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getCategoryCallback.getCategoryAll(oneList);
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

    public interface GetCategoryCallback {
        public void getCategoryAll(ArrayList<ArrayList<String>> data);
    }

    public interface InsertCallback {
        public void onFinished();
    }

    public interface UpdateCallback {
        public void onFinished();
    }

    public interface CountCallback {
        public void getCount(int number);
    }

    public interface CountAllCallback {
        public void getAllCount(int number);
    }

    public interface GetOneCallback {
        public void getOne(ArrayList<String> data);
    }
}
