package com.example.lixiangning.dbtest;

import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiangning on 2017/10/13.
 */

public class DBHelper {
    private static final String TAG = "tag------";
    private static Connection con;
    private static int count;
//    private static ArrayList<ArrayList<String>> allClothes;
    private static MyArrayList allClothes = new MyArrayList();
    static boolean flag = false;

    public DBHelper() {
//        this.con = getConnection();
    }


//    private Connection getConnection(){
//
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 反复尝试连接，直到连接成功后退出循环
//                while (!Thread.interrupted()) {
//                    try {
//                        Thread.sleep(100);  // 每隔0.1秒尝试连接
//                    } catch (InterruptedException e) {
//                        Log.e(TAG, e.toString());
//                    }
//
//                    try {
//                        Class.forName("com.mysql.jdbc.Driver");
//                        Log.v(TAG, "加载JDBC驱动成功");
//                    } catch (ClassNotFoundException e) {
//                        Log.e(TAG, "加载JDBC驱动失败");
//                        return;
//                    }
//
//                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
//                    String ip = "192.168.7.23";
//                    int port = 3306;
//                    String dbName = "myCloset";
//                    String url = "jdbc:mysql://" + ip + ":" + port
//                            + "/" + dbName; // 构建连接mysql的字符串
//                    String user = "root";
//                    String password = "xn1230o.";
//
//                    // 3.连接JDBC
//                    try {
//                        Connection conn = DriverManager.getConnection(url, user, password);
//                        Log.i(TAG, "远程连接成功!");
//                        conn.close();
//                        return;
//                    } catch (SQLException e) {
//                        Log.e(TAG, "远程连接失败!");
//                    }
//                }
//            }
//        });
//        thread.start();
//
//        return con;
//    }

    public void insert(Clothes clothes) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 反复尝试连接，直到连接成功后退出循环
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
//                    String ip = "192.168.7.23";
                    String ip = "172.29.95.53";
                    int port = 3306;
                    String dbName = "myCloset";
                    String url = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName; // 构建连接mysql的字符串
                    String user = "root";
                    String password = "xn1230o.";

                    // 3.连接JDBC
                    con = null;
                    try {
                        con = DriverManager.getConnection(url, user, password);
                        Log.i(TAG, "远程连接成功!");
                        break;
                    } catch (SQLException e) {
                        Log.e(TAG, "远程连接失败!");
                    }
                }


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
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            con.close();
                            Log.i(TAG, "关闭连接成功");
                        } catch (SQLException e) {
                            Log.e(TAG, "关闭连接失败");
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
                // 反复尝试连接，直到连接成功后退出循环
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
                    String ip = "192.168.7.23";
                    int port = 3306;
                    String dbName = "myCloset";
                    String url = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName; // 构建连接mysql的字符串
                    String user = "root";
                    String password = "xn1230o.";

                    // 3.连接JDBC
                    con = null;
                    try {
                        con = DriverManager.getConnection(url, user, password);
                        Log.i(TAG, "远程连接成功!");
                        break;
                    } catch (SQLException e) {
                        Log.e(TAG, "远程连接失败!");
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
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        con.close();
                        Log.i(TAG, "关闭连接成功");
                    } catch (SQLException e) {
                        Log.e(TAG, "关闭连接失败");
                    }
                }
            }
        });
        thread.start();
    }

    public MyArrayList getAll() {
        Thread thread = new SubThreadGet(allClothes);
        thread.start();
        while(!flag);

        return allClothes;
    }

    public static void callback()
    {
        Log.i(TAG, "子线程执行结束");
        flag = true;
    }

    public Integer getCount(String category) {

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                count = 0;

                // 反复尝试连接，直到连接成功后退出循环
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
                    String ip = "192.168.7.23";
                    int port = 3306;
                    String dbName = "myCloset";
                    String url = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName; // 构建连接mysql的字符串
                    String user = "root";
                    String password = "xn1230o.";

                    // 3.连接JDBC
                    con = null;
                    try {
                        con = DriverManager.getConnection(url, user, password);
                        Log.i(TAG, "远程连接成功!");
                        break;
                    } catch (SQLException e) {
                        Log.e(TAG, "远程连接失败!");
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

                    try {
                        con.close();
                        Log.i(TAG, "关闭连接成功");
                    } catch (SQLException e) {
                        Log.e(TAG, "关闭连接失败");
                    }

                }
            }
        });
        thread.start();
        return count;

    }

    public void delete(Clothes clothes) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 反复尝试连接，直到连接成功后退出循环
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
                    String ip = "192.168.7.23";
                    int port = 3306;
                    String dbName = "myCloset";
                    String url = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName; // 构建连接mysql的字符串
                    String user = "root";
                    String password = "xn1230o.";

                    // 3.连接JDBC
                    con = null;
                    try {
                        con = DriverManager.getConnection(url, user, password);
                        Log.i(TAG, "远程连接成功!");
                        break;
                    } catch (SQLException e) {
                        Log.e(TAG, "远程连接失败!");
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
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        con.close();
                    } catch (SQLException e) {
                        Log.e(TAG, "关闭连接失败");
                    }
                }
            }
        });
        thread.start();

    }

}
