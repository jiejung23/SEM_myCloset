package com.example.lixiangning.dbtest;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lixiangning on 2017/10/17.
 */

public class SubThreadGet extends Thread {

    private MyArrayList allClothes;
    private static Connection con;
    private static final String TAG = "tag------";

    public SubThreadGet(MyArrayList list)
    {
        this.allClothes = list;
    }

    @Override
    public void run() {

        allClothes.setvalue(new ArrayList<>());

        ArrayList<ArrayList<String>> oneList = new ArrayList<>();

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
            String sql = "select clothID, clothCategory, clothColor from clothes";
            PreparedStatement pstmt;

            try {
                pstmt = (PreparedStatement)con.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();
                int col = rs.getMetaData().getColumnCount();
                Log.i(TAG, "col: " + col);

                Log.i(TAG, "1111");

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
                allClothes.setvalue(oneList);
                DBHelper.callback();

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
}
