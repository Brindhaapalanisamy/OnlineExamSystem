Online Exam System (Java Console Application)

Overview

The Online Exam System is a Java-based console application designed to simulate a real-world examination system. It supports student exams with time limits, admin management of questions, and result storage using a MySQL database.
This project follows a structured MVC-style package organization and is suitable for academic and learning purposes.

Features

>Student Module

- Start exam with questions fetched from the database

- Each question has an individual timer

- Automatic scoring:

- Correct answer within time → score awarded

- Time-out / skipped / wrong answer → 0 marks

- Final score calculation

- Result stored in database

>Admin Module

- Add questions with options and correct answer

- View all questions

- Edit existing questions

- Delete questions

- View all student results

- Delete student results by name

 Technologies Used

- Java (JDK 17+)

- MySQL

- JDBC

- IntelliJ IDEA

- Git & GitHub

  𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝗦𝘁𝗿𝘂𝗰𝘁𝘂𝗿𝗲

src/
└── com/exam
    ├── controller
    │   └── ExamController.java
    ├── dao
    │   ├── QuestionDAO.java
    │   └── ResultDAO.java
    ├── model
    │   ├── Question.java
    │   └── Result.java
    ├── util
    │   └── DBConnection.java
    ├── view
    │   └── ExamView.java
    └── main
        └── MainApp.java

