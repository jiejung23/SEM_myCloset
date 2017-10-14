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
//        String driver_url = "jdbc:mysql://172.29.95.53:3306/myCloset?serverTimezone=UTC&verifyServerCertificate=false&useSSL=false";
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

    public static int insert(Clothes clothes) {
        Connection conn =  getConnection();
        int i = 0;
        String sql = "insert into clothes(clothImg, clothCategory, clothColor, clothTexture, clothTags, addDate, checkDate, checkTimes, likeTimes) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);

            pstmt.setString(1,clothes.getClothImg() + "");
            pstmt.setString(2,clothes.getClothCategory() + "");
            pstmt.setString(3,clothes.getClothColor() + "");
            pstmt.setString(4,clothes.getClothTexture() + "");
            pstmt.setString(5,clothes.getDBClothTages() + "");
            pstmt.setString(6,clothes.getDBAddDate() + "");
            pstmt.setString(7,clothes.getDBCheckDate() + "");
            pstmt.setString(8,clothes.getCheckTimes() + "");
            pstmt.setString(9,clothes.getLikeTimes() + "");

            i = pstmt.executeUpdate();

            System.out.println("Insert the cloth: " + clothes.getClothID() + ".");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int update(Clothes clothes) {
        Connection conn = getConnection();
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
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();

            System.out.println("Update the cloth: " + clothes.getClothID() + ".");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

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

    public static Integer getCount(String category) {
        Connection conn = getConnection();
        String sql = "SELECT count(clothID) FROM clothes WHERE clothCategory='" + category + "'";
        PreparedStatement pstmt;

        int count = 0;

        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }

            System.out.println("Number of " + category + ": " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int delete(Clothes clothes) {
        Connection conn = getConnection();
        int i = 0;
        String sql = "delete from clothes where clothID='" + clothes.getClothID() + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();

            System.out.println("Delete the cloth: " + clothes.getClothID() + ".");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }



    public static void main(String[] args) {

        DBHelper.getAll();

        int num = DBHelper.insert(new Clothes("img11","category11","color11","texture11","tag1, tag2, tag3","2015-10-11","2017-09-02",23,19));
        DBHelper.getAll();

    }
}
