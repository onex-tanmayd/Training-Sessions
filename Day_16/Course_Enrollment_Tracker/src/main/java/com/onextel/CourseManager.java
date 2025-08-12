package com.onextel;

import java.util.*;

public class CourseManager {
    private ArrayList<Course> courseList;

    public CourseManager() {
        this.courseList = new ArrayList<>();
    }

    public void addCourse(String code) {
        courseList.add(new Course(code));
    }

    public Course getCourse(String code) {
        for (Course c : courseList) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public void enroll(String code, String student) {
        Course c = getCourse(code);
        if (c != null) {
            c.addStudent(student);
            System.out.println("Added " + student + " to " + code);
        } else {
            System.out.println("Course " + code + " not found.");
        }
    }

    public void sortCourses() {
        courseList.sort(Comparator.comparing(Course::getCode, String.CASE_INSENSITIVE_ORDER));
    }

    public void shuffleCourses() {
        Collections.shuffle(courseList);
    }

    public void showCourses() {
        for (int i = 0; i < courseList.size(); i++) {
            System.out.println((i + 1) + ". " + courseList.get(i));
        }
    }

    public void showReport() {
        System.out.println("\n--- Course Report ---");
        for (Course c : courseList) {
            System.out.println("Course: " + c.getCode());
            if (c.getStudents().isEmpty()) {
                System.out.println("  No students enrolled.");
            } else {
                System.out.println("  Students: " + c.getStudents());
                Set<String> unique = new HashSet<>(c.getStudents());
                for (String s : unique) {
                    int freq = c.countStudent(s);
                    if (freq > 1) {
                        System.out.println("    " + s + ": " + freq + " times");
                    }
                }
            }
        }
        System.out.println("---------------------\n");
    }
}