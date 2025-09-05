package com.studentapp.service;

import com.studentapp.model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students;
    private static final String FILE_NAME = "students.txt";

    public StudentService() {
        this.students = new ArrayList<>();
        loadStudentsFromFile(); // Load existing data when the service is created
    }

    // CREATE: Add a new student
    public void addStudent(Student student) {
        students.add(student);
    }

    // READ: Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent external modification
    }

    // READ: Find a student by ID
    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Return null if not found
    }

    // UPDATE: Update an existing student's details
    public boolean updateStudent(int id, String newName, String newDepartment, double newGpa) {
        Student student = findStudentById(id);
        if (student != null) {
            student.setName(newName);
            student.setDepartment(newDepartment);
            student.setGpa(newGpa);
            return true;
        }
        return false;
    }

    // DELETE: Remove a student by ID
    public boolean deleteStudent(int id) {
        Student studentToRemove = findStudentById(id);
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            return true;
        }
        return false;
    }
    
    // --- File I/O Operations ---

    // Save student data to a text file
    public void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                // We'll use a simple comma-separated format: id,name,department,gpa
                writer.println(student.getId() + "," + student.getName() + "," + student.getDepartment() + "," + student.getGpa());
            }
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    // Load student data from a text file
    private void loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // No data to load yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String department = parts[2];
                    double gpa = Double.parseDouble(parts[3]);
                    students.add(new Student(id, name, department, gpa));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading students from file: " + e.getMessage());
        }
    }
}