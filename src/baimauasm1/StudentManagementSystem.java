package baimauasm1;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author phung
 */
public class StudentManagementSystem {

    private static LinkedList<StudentRecord> students = new LinkedList<>();
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

            while (true) {
                System.out.print("Enter Student ID: ");
                id = scanner.nextLine();
                if (id.isEmpty() || !id.matches("\\d+")) {
                    System.out.println("Invalid ID. Please enter a numeric ID.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Enter Student Name: ");
                name = scanner.nextLine();
                if (name.isEmpty() || name.matches(".*\\d.*")) {
                    System.out.println("Invalid name. Please enter a name without numbers.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Enter Student Marks: ");
                String marksInput = scanner.nextLine();
                if (marksInput.isEmpty()) {
                    System.out.println("Marks cannot be empty. Please enter valid marks.");
                } else {
                    try {
                        marks = Double.parseDouble(marksInput);
                        if (marks < 0 || marks > 10) {
                            System.out.println("Marks must be between 0 and 10.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks. Please enter a valid number.");
                    }
                }
            }

            students.add(new StudentRecord(id, name, marks));
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void editStudent() {
        try {
            System.out.print("Enter Student ID to edit: ");
            String id = scanner.nextLine();
            for (StudentRecord student : students) {
                if (student.getId().equals(id)) {
                    String name;
                    double marks = -1;

                    while (true) {
                        System.out.print("Enter new name: ");
                        name = scanner.nextLine();
                        if (name.isEmpty() || name.matches(".*\\d.*")) {
                            System.out.println("Invalid name. Please enter a name without numbers.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("Enter new marks: ");
                        String marksInput = scanner.nextLine();
                        if (marksInput.isEmpty()) {
                            System.out.println("Marks cannot be empty. Please enter valid marks.");
                        } else {
                            try {
                                marks = Double.parseDouble(marksInput);
                                if (marks < 0 || marks > 10) {
                                    System.out.println("Marks must be between 0 and 10.");
                                } else {
                                    break;
                                }
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
            if (id.isEmpty() || !id.matches("\\d+")) {
                System.out.println("Invalid ID. Please enter a numeric ID.");
                return;
            }
            boolean removed = students.removeIf(student -> student.getId().equals(id));
            if (removed) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    private static void sortStudents() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students to sort.");
                return;
            }

            System.out.println("Choose sorting method:");
            System.out.println("1. Bubble Sort");
            System.out.println("2. Selection Sort");
            System.out.print("Enter your choice: ");
            int sortChoice;
            try {
                sortChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (sortChoice) {
                    case 1:
                        bubbleSortById();
                        System.out.println("Students sorted by ID using Bubble Sort.");
                        break;
                    case 2:
                        selectionSortById();
                        System.out.println("Students sorted by ID using Selection Sort.");
                        break;
                    default:
                        System.out.println("Invalid choice! Defaulting to Bubble Sort.");
                        bubbleSortById();
                        System.out.println("Students sorted by ID using Bubble Sort.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
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
                StudentRecord current = students.get(i);
                StudentRecord next = students.get(i + 1);
                if (current.getId().compareTo(next.getId()) > 0) {
                    students.set(i, next);
                    students.set(i + 1, current);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    private static void selectionSortById() {
        if (students.isEmpty()) {
            return;
        }

        for (int i = 0; i < students.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(j).getId().compareTo(students.get(minIndex).getId()) < 0) {
                    minIndex = j;
                }
            }
            StudentRecord temp = students.get(minIndex);
            students.set(minIndex, students.get(i));
            students.set(i, temp);
        }
    }

    private static void searchStudent() {
        try {
            System.out.print("Enter Student ID to search: ");
            String id = scanner.nextLine();
            for (StudentRecord student : students) {
                if (student.getId().equals(id)) {
                    System.out.println(student);
                    return;
                }
            }
            System.out.println("Student not found.");
        } catch (Exception e) {
            System.out.println("Error searching for student: " + e.getMessage());
        }
    }

    private static void displayAllStudents() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students to display.");
            } else {
                for (StudentRecord student : students) {
                    System.out.println(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying students: " + e.getMessage());
        }
    }

    private static void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (StudentRecord student : students) {
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
                    students.add(new StudentRecord(id, name, marks));
                }
            }
            System.out.println("Students loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
            throw e;
        }
    }
}
