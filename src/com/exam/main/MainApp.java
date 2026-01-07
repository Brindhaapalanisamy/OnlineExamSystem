package com.exam.main;

import java.util.Scanner;
import com.exam.controller.ExamController;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExamController exam = new ExamController();

        while (true) {
            System.out.println("\n===== ONLINE EXAM SYSTEM =====");
            System.out.println("1. Student Exam");
            System.out.println("2. Add Questions (Admin)");
            System.out.println("3. Admin Menu (View/Edit/Delete Questions)");
            System.out.println("4. View Results");
            System.out.println("5. Delete Student Result");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    // Student Exam
                    exam.startExam();
                    break;

                case 2:
                    // Admin: Add Questions
                    exam.addQuestions();
                    break;

                case 3:
                    // Admin: View/Edit/Delete Questions
                    exam.adminMenu();
                    break;

                case 4:
                    // View Results
                    //exam.readResultsFromFile();
                    exam.viewResults();
                    break;

                case 5:
                    // Delete specific student result
                    exam.deleteStudentResult();
                    break;

                case 6:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
