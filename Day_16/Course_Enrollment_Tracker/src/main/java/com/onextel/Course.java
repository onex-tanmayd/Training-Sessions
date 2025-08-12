package com.onextel;

import java.util.ArrayList;

public class Course {
    private String code;
    private ArrayList<String> students;

    public Course(String code) {
        this.code = code;
        this.students = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void addStudent(String name) {
        students.add(name);
    }

    public int countStudent(String name) {
        int count = 0;
        for (String s : students) {
            if (s.equalsIgnoreCase(name)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return code + ": " + students;
    }
}