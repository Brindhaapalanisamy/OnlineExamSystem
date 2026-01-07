package com.exam.util;

public class DriverTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver Loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

