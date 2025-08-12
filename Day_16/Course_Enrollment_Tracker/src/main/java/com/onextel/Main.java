package com.onextel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Course Enrollment App!");

        while (true) {
            try {
                System.out.println("\n1. Add course");
                System.out.println("2. Enroll student");
                System.out.println("3. Show all courses");
                System.out.println("4. Sort courses");
                System.out.println("5. Shuffle courses");
                System.out.println("6. Show report");
                System.out.println("0. Exit");
                System.out.print("Choose option: ");
                int option = input.nextInt();
                input.nextLine(); // consume newline

                switch (option) {
                    case 1:
                        System.out.print("Course code: ");
                        String code = input.nextLine();
                        manager.addCourse(code);
                        break;
                    case 2:
                        System.out.print("Course code: ");
                        code = input.nextLine();
                        System.out.print("Student name: ");
                        String name = input.nextLine();
                        manager.enroll(code, name);
                        break;
                    case 3:
                        manager.showCourses();
                        break;
                    case 4:
                        manager.sortCourses();
                        System.out.println("Courses sorted.");
                        break;
                    case 5:
                        manager.shuffleCourses();
                        System.out.println("Courses shuffled.");
                        break;
                    case 6:
                        manager.showReport();
                        break;
                    case 0:
                        System.out.println("Bye!");
                        input.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                input.nextLine(); // clear invalid input
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}