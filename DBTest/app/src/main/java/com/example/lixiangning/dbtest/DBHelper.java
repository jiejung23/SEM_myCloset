package com.example.lixiangning.dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lixiangning on 2017/10/13.
 */

public class DBHelper {
    private static Connection con;

    public DBHelper() {
        this.con = getConnection();
    }


    private static Connection getConnection(){

        String driver_class = "com.mysql.cj.jdbc.Driver";
        String driver_url = "jdbc:mysql://localhost:3306/myCloset?serverTimezone=UTC&verifyServerCertificate=false&useSSL=false";
        String database_user = "root";
        String database_password = "xn1230o.";
        try {

            Class.forName(driver_class);
            con = DriverManager.getConnection(driver_url, database_user, database_password);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

//    public static int insert(User student) {
//        Connection conn =  getConnection();
//        int i = 0;
//        String sql = "insert into user (iduser,user_count) values(?,?)";
//        PreparedStatement pstmt;
//        try {
//            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            pstmt.setString(1, student.getId()+"");
//            pstmt.setString(2, student.getCout_us()+"");
//            //    pstmt.setString(3, student.getAge());
//            i = pstmt.executeUpdate();
//            pstmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return i;
//    }

//    public static int update(User student) {
//        Connection conn = getConnection();
//        int i = 0;
//        String sql = "update user set user_count='" + student.getCout_us() + "' where iduser='" + student.getId() + "'";
//        PreparedStatement pstmt;
//        try {
//            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            i = pstmt.executeUpdate();
//            System.out.println("resutl: " + i);
//            pstmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return i;
//    }

    public static Integer getAll() {
        Connection conn = getConnection();
        String sql = "select * from clothes";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static int delete(String name) {
//        Connection conn = getConnection();
//        int i = 0;
//        String sql = "delete from user where iduser='" + name + "'";
//        PreparedStatement pstmt;
//        try {
//            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            i = pstmt.executeUpdate();
//            System.out.println("resutl: " + i);
//            pstmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return i;
//    }

    public static void main(String[] args) {

        DBHelper.getAll();

        System.out.println("测试中文输出");


    }
}
