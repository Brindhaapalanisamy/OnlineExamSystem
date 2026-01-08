package com.exam.dao;

import java.sql.*;
import java.util.ArrayList;
import com.exam.model.Question;
import com.exam.util.DBConnection;

public class QuestionDAO {

    public void saveQuestion(Question q) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO questions(question, option_a, option_b, option_c, option_d, correct_option) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, q.getQuestion());
            ps.setString(2, q.getOptionA());
            ps.setString(3, q.getOptionB());
            ps.setString(4, q.getOptionC());
            ps.setString(5, q.getOptionD());
            ps.setString(6, String.valueOf(q.getCorrectOption()));

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM questions";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option").charAt(0)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteQuestion(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM questions WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
            System.out.println("Question deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(int id, String question, char correct) {
        try {
            Connection con = DBConnection.getConnection();
            String sql =
                    "UPDATE questions SET question=?, correct_option=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, question);
            ps.setString(2, String.valueOf(correct));
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasQuestions() {
        boolean exists = false;
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM questions");
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}
