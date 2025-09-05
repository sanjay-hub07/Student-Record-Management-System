# Student Record Management System
A simple yet robust desktop application for managing student records, built entirely with Java. This project showcases core object-oriented programming (OOP) principles and provides two distinct user interfaces: a classic command-line interface (CLI) and a modern graphical user interface (GUI) built with Swing.

## Features
This application allows users to perform all fundamental CRUD (Create, Read, Update, Delete) operations on student records.

* **Add a New Student**: Add a new student with details like ID, Name, Department, and GPA.

* **View All Records**: Display a comprehensive list of all students currently in the system.

* **Update Student Details**: Modify the information for an existing student.

* **Delete a Student**: Remove a student's record from the system.

* **Search Functionality**: Quickly find a specific student by their unique ID.

* **Data Persistence**: All student records are saved to a local students.txt file, ensuring data is not lost when the application is closed.

* **Dual Interface**:

* **Command-Line Interface (CLI)**: For users who prefer a text-based, fast interaction.

* **Graphical User Interface (GUI)**: A user-friendly, visually appealing interface built with Java Swing for easy navigation and management.

## Technologies Used
* **Language**: Java

* **GUI Toolkit**: Java Swing

* **Core Concepts**: Object-Oriented Programming (OOP), Data Structures (ArrayList), File I/O

## How to Run the Application
**Prerequisites**

Java Development Kit (JDK) 11 or higher installed.

**Steps**

**1. Clone the repository**:

```
git clone [https://github.com/your-username/Student-Record-Management-System.git](https://github.com/your-username/Student-Record-Management-System.git)
cd Student-Record-Management-System
```

**2. Compile the source code**:
Navigate to the src directory and run the compile command.
```
cd src
javac -d . com/studentapp/model/Student.java com/studentapp/service/StudentService.java com/studentapp/view/StudentGUI.java com/studentapp/main/Main.java
```

**3. Run your desired version**:

**To run the GUI Version**:
```
java com.studentapp.view.StudentGUI
```
**To run the Command-Line Version**:
```
java com.studentapp.main.Main
```

# Output

**Command-Line Output:**

<img width="1919" height="1199" alt="image" src="https://github.com/user-attachments/assets/0ad48df7-6ce5-4f12-99df-df7ac09fde75" />

**GUI-Based Output:**

<img width="1918" height="1199" alt="image" src="https://github.com/user-attachments/assets/da9118e0-aeb5-4935-b5c3-34bc3242bb2e" />
