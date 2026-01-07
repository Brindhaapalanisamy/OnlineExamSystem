package com.exam.view;

import java.util.Scanner;
import com.exam.model.Question;

public class ExamView {
    Scanner sc = new Scanner(System.in);

    public void showQuestionOnly(Question q) {
        System.out.println(q.getQuestion());
        System.out.println("A. " + q.getOptionA());
        System.out.println("B. " + q.getOptionB());
        System.out.println("C. " + q.getOptionC());
        System.out.println("D. " + q.getOptionD());
        System.out.print("Your answer: ");
    }

    public void showResult(double score, int total) {
        System.out.println("Score: " + score + " / " + total);
    }
}
