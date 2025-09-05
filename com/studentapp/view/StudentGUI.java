package com.studentapp.view;

import com.studentapp.model.Student;
import com.studentapp.service.StudentService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StudentGUI extends JFrame {
    // --- Data & Service Layer ---
    private final StudentService studentService;

    // --- UI Components ---
    private final JTextField idField = new JTextField(5);
    private final JTextField nameField = new JTextField(15);
    private final JTextField departmentField = new JTextField(10);
    private final JTextField gpaField = new JTextField(5);

    private final JButton addButton = new JButton("Add Student");
    private final JButton updateButton = new JButton("Update Record");
    private final JButton deleteButton = new JButton("Delete Record");
    private final JButton searchButton = new JButton("Search by ID");
    private final JButton saveButton = new JButton("Save & Exit");

    // Table to display student data
    private final JTable studentTable;
    private final DefaultTableModel tableModel;

    // --- Styling ---
    private final Color bgColor = new Color(237, 246, 249); // Very light cloud blue
    private final Color headerColor = new Color(52, 73, 94); // Dark desaturated blue
    private final Color buttonColor = new Color(41, 128, 185); // Strong, clean blue
    private final Color buttonHoverColor = new Color(52, 152, 219); // Lighter blue for hover
    private final Color textColor = new Color(44, 62, 80); // Dark grey, softer than black

    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
    private final Font headerFont = new Font("Segoe UI", Font.BOLD, 16);

    public StudentGUI() {
        // Initialize the service layer
        studentService = new StudentService();

        // --- Window Setup ---
        setTitle("Student Record Management System");
        setSize(950, 700); // Made window slightly larger for bigger fonts
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bgColor);

        // --- Table Setup ---
        String[] columnNames = {"ID", "Name", "Department", "GPA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        styleTable();

        // --- Top Panel for Inputs and Buttons ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(bgColor);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Manage Student Records");
        titledBorder.setTitleFont(titleFont);
        titledBorder.setTitleColor(headerColor);
        topPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), titledBorder));

        // Panel for input fields
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        fieldsPanel.setBackground(bgColor);
        
        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel deptLabel = new JLabel("Department:");
        JLabel gpaLabel = new JLabel("GPA:");
        
        styleLabel(idLabel);
        styleLabel(nameLabel);
        styleLabel(deptLabel);
        styleLabel(gpaLabel);

        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameField);
        fieldsPanel.add(deptLabel);
        fieldsPanel.add(departmentField);
        fieldsPanel.add(gpaLabel);
        fieldsPanel.add(gpaField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(bgColor);
        styleButton(addButton);
        styleButton(updateButton);
        styleButton(deleteButton);
        styleButton(searchButton);
        styleButton(saveButton);
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(saveButton);
        
        topPanel.add(fieldsPanel);
        topPanel.add(buttonPanel);
        
        styleTextField(idField);
        styleTextField(nameField);
        styleTextField(departmentField);
        styleTextField(gpaField);

        // --- Main Layout ---
        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(new EmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        setupActionListeners();
        refreshTable();
    }
    
    private void styleLabel(JLabel label) {
        label.setFont(mainFont);
        label.setForeground(textColor);
    }

    private void styleTable() {
        studentTable.setFont(mainFont);
        studentTable.setRowHeight(30);
        studentTable.setSelectionBackground(buttonHoverColor);
        studentTable.setSelectionForeground(Color.WHITE);
        JTableHeader header = studentTable.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(headerColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 40));
    }

    private void styleTextField(JTextField field) {
        field.setFont(mainFont);
        Border line = BorderFactory.createLineBorder(new Color(200, 200, 200));
        Border padding = new EmptyBorder(5, 8, 5, 8);
        field.setBorder(BorderFactory.createCompoundBorder(line, padding));
    }
    
    private void styleButton(JButton button) {
        button.setFont(buttonFont);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(buttonHoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });
    }

    private void setupActionListeners() {
        // ADD button action
        addButton.addActionListener(e -> addStudent());

        // UPDATE button action
        updateButton.addActionListener(e -> updateStudent());

        // DELETE button action
        deleteButton.addActionListener(e -> deleteStudent());

        // SEARCH button action
        searchButton.addActionListener(e -> searchStudent());

        // SAVE & EXIT button action
        saveButton.addActionListener(e -> {
            studentService.saveStudentsToFile();
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
            System.exit(0);
        });

        // Add a listener to the table to populate fields when a row is clicked
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                departmentField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                gpaField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
    }

    private void refreshTable() {
        // Clear existing rows
        tableModel.setRowCount(0);
        // Get all students from the service
        List<Student> students = studentService.getAllStudents();
        // Add each student as a new row
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getId(), student.getName(), student.getDepartment(), student.getGpa()});
        }
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            double gpa = Double.parseDouble(gpaField.getText().trim());

            if (name.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Department cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (studentService.findStudentById(id) != null) {
                JOptionPane.showMessageDialog(this, "Student with this ID already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            studentService.addStudent(new Student(id, name, department, gpa));
            refreshTable();
            clearInputFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and GPA.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            double gpa = Double.parseDouble(gpaField.getText().trim());

            if (studentService.updateStudent(id, name, department, gpa)) {
                refreshTable();
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Student with ID " + id + " not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a student or enter a valid ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student with ID " + id + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                if (studentService.deleteStudent(id)) {
                    refreshTable();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Student with ID " + id + " not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please select a student or enter a valid ID to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        try {
            String idString = JOptionPane.showInputDialog(this, "Enter Student ID to search:");
            if (idString == null || idString.trim().isEmpty()) {
                return; // User cancelled or entered nothing
            }
            int id = Integer.parseInt(idString.trim());
            Student student = studentService.findStudentById(id);
            if (student != null) {
                JOptionPane.showMessageDialog(this, "Student Found:\n" + student, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Student with ID " + id + " not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        gpaField.setText("");
        studentTable.clearSelection();
    }

    public static void main(String[] args) {
        try {
            // Set a modern look and feel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Look and feel not set, using default.");
        }

        SwingUtilities.invokeLater(() -> {
            new StudentGUI().setVisible(true);
        });
    }
}