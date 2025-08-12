package com.onextel;

import file.FileReaderUtil;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        FileReaderUtil.printfile("/home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day18/FileIO/src/main/resources/users.csv");
        FileReaderUtil.exportToCsv("/home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day18/FileIO/src/main/resources/users_export.csv");

    }
    }
