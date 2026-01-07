Project Structure
OnlineExamSystem
│
├── src
│   └── com.exam
│       ├── controller
│       │   └── ExamController.java
│       ├── dao
│       │   ├── QuestionDAO.java
│       │   └── ResultDAO.java
│       ├── main
│       │   └── MainApp.java
│       ├── model
│       │   ├── Question.java
│       │   └── Result.java
│       ├── util
│       │   ├── DBConnection.java
│       │   └── DriverTest.java
│       └── view
│           └── ExamView.java
│
├── lib
│   └── mysql-connector-j-9.5.0.jar
├── data
│   └── result.txt
├── out              (compile output → git-ku vendam)
├── .idea            (IDE config → git-ku vendam)
├── .gitignore
├── README.md
└── OnlineExamSystem.iml

# OnlineExamSystem

A Java console-based Online Exam System that allows students and admins to manage exams, questions, and results with Timer.

## Features
- Student register
- Admin panel for adding/editing/deleting and also view questions
- Conduct online exams with automatic evaluation and Strict Timer Fixed
- View results and Delete results

## Requirements
- Java 17+
- MySQL 8+
- IntelliJ IDEA or any Java IDE

  * Usage

- Follow console prompts to register as a student or login as admin.

- Admin can manage questions, start exams, and view results.

- Students can take exams with a Strict mointor on Timer and view their scores immediately.
