package com.studentapp.model;

public class Student {
    private int id;
    private String name;
    private String department;
    private double gpa;

    // Constructor to initialize a Student object
    public Student(int id, String name, String department, double gpa) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.gpa = gpa;
    }

    // Getters and Setters for encapsulation
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    // Override toString() for a clean, readable representation of the object
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", GPA: " + gpa;
    }
}