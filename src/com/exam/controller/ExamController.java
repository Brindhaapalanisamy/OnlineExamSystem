package com.exam.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import com.exam.dao.ResultDAO;
import com.exam.dao.QuestionDAO;
import com.exam.model.Question;
import com.exam.model.Result;

public class ExamController {

    private final String URL = "jdbc:mysql://localhost:3306/online_exam";
    private final String USER = "root";
    private final String PASS = "brin360!";
    private ArrayList<Question> questions = new ArrayList<>();
    private double score = 0;
    volatile boolean timeUp = false;

    Scanner sc = new Scanner(System.in);
    private ResultDAO resultDAO = new ResultDAO();
    private QuestionDAO questionDAO = new QuestionDAO();

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // STUDENT
    public void startExam() {

        questions = questionDAO.getAllQuestions();

        if (questions.isEmpty()) {
            System.out.println("No questions available");
            return;
        }

        System.out.println("Exam started");

        System.out.print("Enter Student Name: ");
        sc.nextLine();
        String studentName = sc.nextLine();

        score = 0;

        for (Question q : questions) {

            System.out.println("\n------------------");
            System.out.println(q.getQuestion());
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());

            startTimer(10);   // 🔁 timer reset here

            char ans = 'X';
            long start = System.currentTimeMillis();

            while (!timeUp && System.currentTimeMillis() - start < 10000) {

                if (sc.hasNextLine()) {
                    String input = sc.nextLine().trim();

                    if (!input.isEmpty()) {
                        ans = input.toUpperCase().charAt(0);
                    }
                    break;
                }
            }

            // ⛔ STRICT: time over → ZERO MARK
            if (timeUp) {
                continue;
            }

            // ✅ answered within time
            if (ans == q.getCorrectOption()) {
                score++;
            }
            // else → 0 mark
        }

        System.out.println("\nScore: " + score + " / " + questions.size());

        Result r = new Result(studentName, score, questions.size());
        resultDAO.saveResult(r);
    }

    public void startTimer(int seconds) {
        timeUp = false;

        new Thread(() -> {
            try {
                for (int i = seconds; i >= 1; i--) {
                    System.out.print("\rTime left: " + i + " sec ");
                    Thread.sleep(1000);
                }
                timeUp = true;
                System.out.println("\nTime up! Question skipped (0 mark)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // ADD QUESTION
    public void addQuestions() {

        System.out.print("How many questions to add: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter Question " + i + ":");
            String q = sc.nextLine();

            System.out.print("Option A: ");
            String a = sc.nextLine();

            System.out.print("Option B: ");
            String b = sc.nextLine();

            System.out.print("Option C: ");
            String c = sc.nextLine();

            System.out.print("Option D: ");
            String d = sc.nextLine();

            System.out.print("Correct option (A/B/C/D): ");
            char correct = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            Question ques = new Question(0, q, a, b, c, d, correct);
            questionDAO.saveQuestion(ques);
        }
    }

    // VIEW QUESTIONS
    public void viewQuestions() {
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + ". " + rs.getString("question"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EDIT QUESTION
    public void editQuestion() {

        System.out.print("Enter Question ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new question: ");
        String q = sc.nextLine();

        System.out.print("Enter new correct option (A/B/C/D): ");
        char c = sc.next().toUpperCase().charAt(0);

        questionDAO.updateQuestion(id, q, c);
    }

    // DELETE QUESTION
    public void deleteQuestion() {
        try (Connection con = getConnection()) {
            System.out.print("Enter question ID: ");
            int id = sc.nextInt();

            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM questions WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Question deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewResults() {
        resultDAO.viewAllResults();
    }

    public void deleteResult() {
        System.out.print("Enter Student Name to delete result: ");
        sc.nextLine(); // 🔥 VERY IMPORTANT
        String name = sc.nextLine();

        resultDAO.deleteResultByName(name);
    }

}
