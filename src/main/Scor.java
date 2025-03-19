package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Scor {

    public void BazaDeDate() {
        createTable();
    }

    // Method to create the table if it doesn't already exist
    private static void createTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:program.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Score5 " +
                    "(Name TEXT NOT NULL, " +
                    " Score INT NOT NULL)";
            stmt.execute(sql);
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    // Method to insert data into the table
    public static void insertData(String Name, int Score) {
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:program.db");
            String sql = "INSERT INTO Score5 (Name, Score) VALUES (?, ?)";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setInt(2, Score);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    // Method to get all data from the table
    public static List<String[]> getData() {
        List<String[]> data = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:program.db");
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Score5");

            while (rs.next()) {
                String Name = rs.getString("Name");
                int Score = rs.getInt("Score");
                data.add(new String[]{Name, String.valueOf(Score)});
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return data;
    }
}
