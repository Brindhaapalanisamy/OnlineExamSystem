package com.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.exam.model.Result;
import com.exam.util.DBConnection;
import java.sql.ResultSet;

public class ResultDAO {

    public void saveResult(Result result) {

        String sql = "INSERT INTO results(student_name, score, total) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("DB Connection Failed");
                return;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, result.getStudentName());
            ps.setDouble(2, result.getScore());
            ps.setInt(3, result.getTotal());

            ps.executeUpdate();
            System.out.println("Result saved to DB");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteResultByName(String studentName) {
        String sql = "DELETE FROM results WHERE student_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, studentName);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Result deleted from DB");
            } else {
                System.out.println("No record found in DB");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewAllResults() {
        String sql = "SELECT * FROM results";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("---- Exam Results ----");

            while (rs.next()) {
                System.out.println(
                        "Name: " + rs.getString("student_name") +
                                " | Score: " + rs.getDouble("score") +
                                " / " + rs.getInt("total")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
