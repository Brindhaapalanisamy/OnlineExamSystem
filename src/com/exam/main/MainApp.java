package com.exam.main;

import java.util.Scanner;
import com.exam.controller.ExamController;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExamController controller = new ExamController();

        while (true) {

            System.out.println("\n===== ONLINE EXAM SYSTEM =====");
            System.out.println("1. Student");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    controller.startExam();
                    break;

                case 2:
                    adminMenu(controller, sc);
                    break;

                case 3:
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ================= ADMIN MENU =================
    private static void adminMenu(ExamController controller, Scanner sc) {

        while (true) {

            System.out.println("\n----- ADMIN MENU -----");
            System.out.println("1. Add Questions");
            System.out.println("2. View Questions");
            System.out.println("3. Edit Question");
            System.out.println("4. Delete Question");
            System.out.println("5. View Results");
            System.out.println("6. Delete Student Result");
            System.out.println("7. Back");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    controller.addQuestions();
                    break;

                case 2:
                    controller.viewQuestions();
                    break;

                case 3:
                    controller.editQuestion();
                    break;

                case 4:
                    controller.deleteQuestion();
                    break;

                case 5:
                    controller.viewResults();
                    break;

                case 6:
                    controller.deleteResult();
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
