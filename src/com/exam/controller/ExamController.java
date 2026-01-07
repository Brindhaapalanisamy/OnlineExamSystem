package com.exam.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import com.exam.model.Question;
import com.exam.view.ExamView;
import com.exam.dao.ResultDAO;
import com.exam.model.Result;
import com.exam.dao.QuestionDAO;

public class ExamController {

    private ArrayList<Question> questions = new ArrayList<>();
    private ExamView view = new ExamView();
    private Scanner sc = new Scanner(System.in);
    private double score = 0;
    QuestionDAO questionDAO = new QuestionDAO();
    ResultDAO resultDAO = new ResultDAO();


    // ================= ADMIN =================

    public void addQuestions() {
        System.out.print("How many questions to add: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter Question " + i);
            String q = sc.nextLine();
            String a = sc.nextLine();
            String b = sc.nextLine();
            String c = sc.nextLine();
            String d = sc.nextLine();
            char correct = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            Question ques = new Question(i, q, a, b, c, d, correct);
            questions.add(ques);
            questionDAO.saveQuestion(ques);
        }
        saveQuestionsToFile();
    }

    // ================= FILE: QUESTIONS =================

    public void saveQuestionsToFile() {
        try {
            FileWriter fw = new FileWriter("data/questions.txt");
            for (Question q : questions) {
                fw.write(
                        q.getQuestion() + "|" +
                                q.getOptionA() + "|" +
                                q.getOptionB() + "|" +
                                q.getOptionC() + "|" +
                                q.getOptionD() + "|" +
                                q.getCorrectOption() + "\n"
                );
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adminMenu() {
        System.out.println("1. View Questions");
        System.out.println("2. Delete Question");
        System.out.println("3. Edit Question");

        int ch = sc.nextInt();
        sc.nextLine();

        if (ch == 1) {
            questions = questionDAO.getAllQuestions();
            for (Question q : questions) {
                System.out.println(q.getId() + ". " + q.getQuestion());
            }
        }

        if (ch == 2) {
            System.out.print("Enter Question ID to delete: ");
            int id = sc.nextInt();
            questionDAO.deleteQuestion(id);
        }

        if (ch == 3) {
            System.out.print("Enter Question ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new question: ");
            String q = sc.nextLine();

            System.out.print("Enter correct option: ");
            char c = sc.next().charAt(0);

            questionDAO.updateQuestion(id, q, c);
        }
    }

    public void loadQuestionsFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/questions.txt"));
            String line;
            int id = 1;

            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                questions.add(new Question(
                        id++, p[0], p[1], p[2], p[3], p[4], p[5].charAt(0)
                ));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("No questions file found");
        }
    }

    // ================= STUDENT =================

    public void startExam() {

        questions = questionDAO.getAllQuestions();

        // loadQuestionsFromFile();   // IMPORTANT

        System.out.print("Enter Student Name: ");
        String studentName = sc.nextLine();

        score = 0;

        for (Question q : questions) {

            System.out.println("\n-------------------------");
            view.showQuestionOnly(q);   // just print question & options

            startStrictTimer(10); // ⏱ 10 seconds

            char ans = 'X';

            long start = System.currentTimeMillis();
            while (!timeUp && (System.currentTimeMillis() - start) < 10000) {
                if (sc.hasNext()) {
                    ans = sc.next().toUpperCase().charAt(0);
                    break;
                }
            }

            if (ans == q.getCorrectOption()) {
                score += 1;
            } else {
                score -= 0.25;
            }
        }

        view.showResult(score, questions.size());

        // ---- SAVE RESULT TO FILE ----
        saveResultToFile(studentName, score, questions.size());

        // ---- SAVE RESULT TO DATABASE ----
        Result result = new Result(studentName, score, questions.size());
        ResultDAO dao = new ResultDAO();
        dao.saveResult(result);
    }

    // ================= FILE: RESULT =================

    public void saveResultToFile(String studentName, double score, int total) {
        try {
            FileWriter fw = new FileWriter("data/result.txt", true);
            fw.write("Name: " + studentName +
                    " | Score: " + score +
                    " / " + total + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readResultsFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/result.txt"));
            String line;
            System.out.println("---- Exam Results ----");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("No results found");
        }
    }

    public void startTimer(int seconds) {
        try {
            for (int i = seconds; i > 0; i--) {
                System.out.print("\rTime left: " + i + " seconds ");
                Thread.sleep(1000);
            }
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private volatile boolean timeUp = false;

    public void startStrictTimer(int seconds) {
        timeUp = false;

        Thread timerThread = new Thread(() -> {
            try {
                for (int i = seconds; i > 0; i--) {
                    System.out.print("\rTime left: " + i + " seconds ");
                    Thread.sleep(1000);
                }
                timeUp = true;
                System.out.println("\nTime up! Auto skipped.");
            } catch (InterruptedException e) {
                // ignore
            }
        });

        timerThread.start();
    }
    // ExamController.java
    public void deleteStudentResult() {
        System.out.print("Enter student name to delete: ");
        String name = sc.nextLine();
        resultDAO.deleteResultByName(name);
    }
    public void viewResults() {
        resultDAO.viewAllResults();
    }
}
