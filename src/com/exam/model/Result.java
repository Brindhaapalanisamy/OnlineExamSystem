package com.exam.model;

public class Result {

    private String studentName;
    private double score;
    private int total;

    public Result(String studentName, double score, int total) {
        this.studentName = studentName;
        this.score = score;
        this.total = total;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getScore() {
        return score;
    }

    public int getTotal() {
        return total;
    }
}
