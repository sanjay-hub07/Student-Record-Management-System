package com.studentapp.main;

import com.studentapp.model.Student;
import com.studentapp.service.StudentService;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    studentService.saveStudentsToFile();
                    System.out.println("Data saved. Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Student Record Management System ---");
        System.out.println("1. Add a new student");
        System.out.println("2. Display all students");
        System.out.println("3. Search for a student by ID");
        System.out.println("4. Update student details");
        System.out.println("5. Delete a student");
        System.out.println("6. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());

        Student student = new Student(id, name, department, gpa);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }
    
    private static void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println("\n--- All Student Records ---");
            students.forEach(System.out::println);
        }
    }
    
    private static void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        int id = Integer.parseInt(scanner.nextLine());
        Student student = studentService.findStudentById(id);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (studentService.findStudentById(id) == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter new GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());

        if (studentService.updateStudent(id, name, department, gpa)) {
            System.out.println("Student updated successfully!");
        }
    }
    
    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (studentService.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
}