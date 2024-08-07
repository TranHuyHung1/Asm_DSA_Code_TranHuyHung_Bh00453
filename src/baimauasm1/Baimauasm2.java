package baimauasm1;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author hung
 */
public class Baimauasm2 {

    private static LinkedList<Student> students = new LinkedList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        try {
            loadStudentsFromFile();
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }

        int choice;

        do {
            System.out.println("\nStudent Management System:");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students");
            System.out.println("5. Search Student");
            System.out.println("6. Display All Students");
            System.out.println("7. Exit");
            
            // Bắt lỗi cho việc chọn mục
            while (true) {
                System.out.print("Enter your choice: ");
                String choiceInput = scanner.nextLine();
                if (choiceInput.isEmpty() || !choiceInput.matches("\\d+")) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                } else {
                    choice = Integer.parseInt(choiceInput);
                    break;
                }
            }

            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        editStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        sortStudents();
                        break;
                    case 5:
                        searchStudent();
                        break;
                    case 6:
                        displayAllStudents();
                        break;
                    case 7:
                        saveStudentsToFile();
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 7);
    }

    private static void addStudent() {
        try {
            String id, name;
            double marks = -1;

            // Check for empty ID
            while (true) {
                System.out.print("Enter Student ID: ");
                id = scanner.nextLine();
                if (id.isEmpty() || !id.matches("\\d+")) {
                    System.out.println("Invalid ID. Please enter a numeric ID.");
                } else {
                    break;
                }
            }

            // Check for empty name or name containing numbers
            while (true) {
                System.out.print("Enter Student Name: ");
                name = scanner.nextLine();
                if (name.isEmpty() || name.matches(".*\\d.*")) {
                    System.out.println("Invalid name. Please enter a name without numbers.");
                } else {
                    break;
                }
            }

            // Check for valid marks
            while (true) {
                System.out.print("Enter Student Marks: ");
                String marksInput = scanner.nextLine();
                if (marksInput.isEmpty()) {
                    System.out.println("Marks cannot be empty. Please enter valid marks.");
                } else {
                    try {
                        marks = Double.parseDouble(marksInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks. Please enter a valid number.");
                    }
                }
            }

            students.add(new Student(id, name, marks));
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void editStudent() {
        try {
            System.out.print("Enter Student ID to edit: ");
            String id = scanner.nextLine();
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    String name;
                    double marks = -1;

                    // Check for empty name or name containing numbers
                    while (true) {
                        System.out.print("Enter new name: ");
                        name = scanner.nextLine();
                        if (name.isEmpty() || name.matches(".*\\d.*")) {
                            System.out.println("Invalid name. Please enter a name without numbers.");
                        } else {
                            break;
                        }
                    }

                    // Check for valid marks
                    while (true) {
                        System.out.print("Enter new marks: ");
                        String marksInput = scanner.nextLine();
                        if (marksInput.isEmpty()) {
                            System.out.println("Marks cannot be empty. Please enter valid marks.");
                        } else {
                            try {
                                marks = Double.parseDouble(marksInput);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid marks. Please enter a valid number.");
                            }
                        }
                    }

                    student.setName(name);
                    student.setMarks(marks);
                    System.out.println("Student updated successfully.");
                    return;
                }
            }
            System.out.println("Student not found.");
        } catch (Exception e) {
            System.out.println("Error editing student: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        try {
            System.out.print("Enter Student ID to delete: ");
            String id = scanner.nextLine();
            students.removeIf(student -> student.getId().equals(id));
            System.out.println("Student deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    private static void sortStudents() {
        try {
            bubbleSortById();
            System.out.println("Students sorted by ID.");
        } catch (Exception e) {
            System.out.println("Error sorting students: " + e.getMessage());
        }
    }

    private static void bubbleSortById() {
        if (students.isEmpty()) {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < students.size() - 1; i++) {
                Student current = students.get(i);
                Student next = students.get(i + 1);
                if (current.getId().compareTo(next.getId()) > 0) {
                    // Swap students
                    students.set(i, next);
                    students.set(i + 1, current);
                    swapped = true;
                }
            }
        } while (swapped);
    }

 private static void searchStudent() {
    try {
        System.out.println("Choose search method:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        int searchMethod;

        while (true) {
            System.out.print("Enter your choice (1 or 2): ");
            String choiceInput = scanner.nextLine();
            if (choiceInput.isEmpty() || !choiceInput.matches("[12]")) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            } else {
                searchMethod = Integer.parseInt(choiceInput);
                break;
            }
        }

        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();

        Student foundStudent = null;

        if (searchMethod == 1) {
            foundStudent = linearSearch(id);
        } else if (searchMethod == 2) {
            bubbleSortById();  // Ensure the list is sorted before binary search
            foundStudent = binarySearch(id);
        }

        if (foundStudent != null) {
            System.out.println(foundStudent);
        } else {
            System.out.println("Student not found.");
        }
    } catch (Exception e) {
        System.out.println("Error searching for student: " + e.getMessage());
    }
}

private static Student linearSearch(String id) {
    for (Student student : students) {
        if (student.getId().equals(id)) {
            return student;
        }
    }
    return null;
}

private static Student binarySearch(String id) {
    int low = 0;
    int high = students.size() - 1;

    while (low <= high) {
        int mid = (low + high) / 2;
        Student midStudent = students.get(mid);

        int comparison = midStudent.getId().compareTo(id);
        if (comparison == 0) {
            return midStudent;
        } else if (comparison < 0) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return null;
}

    private static void displayAllStudents() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students to display.");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying students: " + e.getMessage());
        }
    }

    private static void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getMarks());
                writer.newLine();
            }
            System.out.println("Students saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private static void loadStudentsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    double marks = Double.parseDouble(parts[2]);
                    students.add(new Student(id, name, marks));
                }
            }
            System.out.println("Students loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
            throw e;
        }
    }

    static class Student {
        private String id;
        private String name;
        private double marks;

        public Student(String id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getMarks() {
            return marks;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMarks(double marks) {
            this.marks = marks;
        }

        public String getRank() {
            if (marks >= 0 && marks < 5.0) {
                return "Fail";
            } else if (marks >= 5.0 && marks < 6.5) {
                return "Medium";
            } else if (marks >= 6.5 && marks < 7.5) {
                return "Good";
            } else if (marks >= 7.5 && marks < 9.0) {
                return "Very Good";
            } else if (marks >= 9.0 && marks <= 10.0) {
                return "Excellent";
            }
            return "Invalid Marks";
        }

        @Override
        public String toString() {
            return "Student ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + getRank();
        }
    }
}
