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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

        ip = "172.29.95.53"; //School
//        ip = "192.168.7.23"; //home
//        ip = "192.168.29.107";
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
                    String sql = "insert into clothes(clothImg, clothCategory, clothColor, clothColorLabel, clothR, clothG, clothB, "
                    + "clothTexture, clothTags, addDate, checkDate, thinkDate, checkTimes, likeTimes) " +
                            "values('" + clothes.getClothImg() + "','" + clothes.getClothCategory() + "','"
                            + clothes.getClothColor() + "','" + clothes.getClothColorLabel() + "',"
                            + clothes.getClothR() + "," + clothes.getClothG() + ","
                            + clothes.getClothB() + ",'" + clothes.getClothTexture() + "','"
                            + clothes.getDBClothTages() + "','" + clothes.getDBAddDate() + "','"
                            + clothes.getDBCheckDate() + "','" + clothes.getDBThinkDate() + "',"
                            + clothes.getCheckTimes() + "," + clothes.getLikeTimes() + ")";


                    PreparedStatement pstmt;
                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);

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
                            Log.e(TAG, "Code" + e.getErrorCode() + " State" + e.getSQLState() + " Cause:"+ e.getCause() + " Message:" +e.getMessage());
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }

                // get all
                if (con != null) {
                    String sql = "select clothID, clothImg, clothCategory, clothColor, clothTags from clothes";
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
                            String tags = rs.getString(5);
                            Log.i(TAG, "cloth: " + id + ", " + img + ", " + category + "," + color);
                            temp.add(id);
                            temp.add(img);
                            temp.add(category);
                            temp.add(color);
                            temp.add(tags);
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
                    String sql = "select clothImg, clothCategory, clothColor, clothColorLabel, clothR, clothG, clothB, clothTags, addDate from clothes where clothId=" + cloth_id;
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
                            String tags = rs.getString(8);
                            String add = rs.getString(9);
                            oneCloth.add(img);
                            oneCloth.add(category);
                            oneCloth.add(color);
                            oneCloth.add(colorLabel);
                            oneCloth.add(colorR);
                            oneCloth.add(colorG);
                            oneCloth.add(colorB);
                            oneCloth.add(tags);
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
                    String sql = "select clothID, clothImg, clothCategory, clothColor, clothTags from clothes where clothCategory='" + category + "'";
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
                            String tags = rs.getString(5);
                            Log.i(TAG, "cloth: " + id + ", " + img + ", " + category + "," + color);
                            temp.add(id);
                            temp.add(img);
                            temp.add(category);
                            temp.add(color);
                            temp.add(tags);
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


    public void insertStyle(Styles styles, InsertCallback callback) {

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
                    String sql = "insert into styles(styleImg, styleLike) values('" + styles.getStyleImg() + "'," + styles.getStyleLike() + ")";
                    PreparedStatement pstmt;
                    try {
                        pstmt = (PreparedStatement) con.prepareStatement(sql);
                        i = pstmt.executeUpdate();

                        Log.i(TAG, "Insert the cloth: " + styles.getStyleID() + ".");
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


    public void getAllStyle(GetAllStyleCallback getAllCallback) {

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
                    String sql = "select styleID, styleImg, styleLike from styles";
                    PreparedStatement pstmt;

                    try {
                        final ArrayList<ArrayList<String>> oneList = new ArrayList<>();
                        pstmt = (PreparedStatement)con.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery();

                        while (rs.next()) {
                            ArrayList<String> temp = new ArrayList<>();
                            String id = rs.getInt(1)+"";
                            String img = rs.getString(2);
                            String like = rs.getString(3) + "";
                            temp.add(id);
                            temp.add(img);
                            temp.add(like);
                            oneList.add(temp);
                        }

                        if (getAllCallback != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getAllCallback.getAllStyle(oneList);
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


    public void getAllDeclutter(GetAllDeclutterCallback getAllDeclutterCallback) {

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
                            Log.e(TAG, "Code" + e.getErrorCode() + " State" + e.getSQLState() + " Cause:"+ e.getCause() + " Message:" +e.getMessage());
                            Log.e(TAG, "Connect to JDBC Unsuccessfully!");
                        }

                    } else {
                        break;
                    }
                }

                // get all
                if (con != null) {
                    String sql = "select clothID, clothImg, clothCategory, clothColor, addDate, checkDate, thinkDate from clothes";
                    PreparedStatement pstmt;

                    try {
                        final ArrayList<ArrayList<String>> oneList = new ArrayList<>();
                        pstmt = (PreparedStatement)con.prepareStatement(sql);

                        ResultSet rs = pstmt.executeQuery();

                        while (rs.next()) {
                            ArrayList<String> temp = new ArrayList<>();
                            String id = rs.getInt(1)+"";
                            String img = rs.getString(2);
                            String category = rs.getString(3);
                            String color = rs.getString(4);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());
                            String currentDate = sdf.format(curDate);

                            long checkDayCount = 0;
                            long addDayCount = 0;
                            long thinkDayCount = 0;
                            String checkDate = rs.getString(5);
                            if(!checkDate.equals("")) {
                                checkDayCount = getDayCount(checkDate);
                            }

                            String addDate = rs.getString(6);
                            if(!addDate.equals("")) {
                                addDayCount = getDayCount(addDate);
                            }

                            String thinkDate = rs.getString(7);
                            if(!thinkDate.equals("")) {
                                thinkDayCount = getDayCount(thinkDate);
                            }


                            if((checkDate.equals(addDate) && addDayCount > 30) || (!checkDate.equals(addDate) && thinkDate.equals("") && checkDayCount > 365) || (!thinkDate.equals("") && thinkDayCount > 30)) {
                                Log.i(TAG, "cloth: " + id + ", " + img + ", " + category + "," + color);
                                temp.add(id);
                                temp.add(img);
                                temp.add(category);
                                temp.add(color);
                                oneList.add(temp);
                            }
                        }

//                        Log.e("ddd", "all clohtes size = " + oneList.size());

                        if (getAllDeclutterCallback != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getAllDeclutterCallback.getAllDeclutter(oneList);
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

    public interface GetAllStyleCallback {
        public void getAllStyle(ArrayList<ArrayList<String>> data);
    }

    public interface GetAllDeclutterCallback {
        public void getAllDeclutter(ArrayList<ArrayList<String>> data);
    }

    public long getDayCount(String StartDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        Date clickDate = null;

        try {
            clickDate = sdf.parse(StartDate);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }

        Date curDate = new Date(System.currentTimeMillis());
        String currentDateStr = sdf.format(curDate);
        Date currentDate = null;

        try {
            currentDate = sdf.parse(currentDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GregorianCalendar cal1 = new GregorianCalendar();

        GregorianCalendar cal2 = new GregorianCalendar();

        cal1.setTime(clickDate);

        cal2.setTime(currentDate);

        long dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);

        return dayCount;
    }
}

