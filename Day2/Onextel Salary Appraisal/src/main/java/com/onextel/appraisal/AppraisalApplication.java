package com.onextel.appraisal;

import com.onextel.appraisal.model.Employee;
import com.onextel.appraisal.model.AppraisalResult;
import com.onextel.appraisal.service.AppraisalService;
import com.onextel.appraisal.util.ConsoleUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppraisalApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AppraisalService appraisalService = new AppraisalService();

    public static void main(String[] args) {
        ConsoleUtils.printHeader();
        showAppraisalStatus();

        String employeeCode = ConsoleUtils.getUserInput(scanner, "Enter Employee Code: ");
        List<String> done = getAppraisedEmployeeCodes();

        if (done.contains(employeeCode)) {
            showAppraisalResult(employeeCode);
        } else {
            Employee employee = findEmployeeInFile(employeeCode);
            if (employee == null) {
                System.out.println("Employee not found. Please enter details manually.");
                employee = collectEmployeeDataWithCode(employeeCode);
            } else {
                System.out.println("Employee found in file. Details loaded.");
                printEmployeeDetails(employee);
                collectPerformanceAndRatings(employee);
            }
            AppraisalResult result = appraisalService.calculateAppraisal(employee);
            displayResults(result);
            writeAppraisalResultToCsv(result);
        }
        scanner.close();
    }
    private static List<String> getAppraisedEmployeeCodes() {
        List<String> codes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("appraisals.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    codes.add(parts[0]);
                }
            }
        } catch (IOException e) {
            // File may not exist yet, that's fine
        }
        return codes;
    }

    private static void showAppraisalStatus() {
        List<String> allCodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) allCodes.add(parts[0]);
            }
        } catch (IOException e) {
            System.out.println("Error reading employees.csv: " + e.getMessage());
        }

        List<String> done = getAppraisedEmployeeCodes();
        List<String> pending = new ArrayList<>(allCodes);
        pending.removeAll(done);

        System.out.println("\nAppraisal Pending: " + pending);
        System.out.println("Appraisal Done: " + done);
    }

    private static void showAppraisalResult(String employeeCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("appraisals.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); // -1 to include empty fields
                if (parts.length > 1 && parts[0].equals(employeeCode)) {
                    System.out.println("\nAppraisal Result for " + employeeCode + ":");
                    System.out.println("Employee Name: " + parts[1]);
                    System.out.println("Total Score: " + parts[2] + "/10");
                    System.out.println("Current Salary: ₹" + parts[3]);
                    System.out.println("New Salary: ₹" + parts[4]);
                    System.out.println("Appraisal Percentage: " + parts[5] + "%");
                    System.out.println("Performance Grade: " + parts[6]);
                    System.out.println("Comments: " + parts[7]);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appraisals.csv: " + e.getMessage());
        }
        System.out.println("No appraisal result found for " + employeeCode);
    }

    private static void writeAppraisalResultToCsv(AppraisalResult result) {
        try (FileWriter fw = new FileWriter("appraisals.csv", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Format: employeeCode,employeeName,totalScore,currentSalary,newSalary,appraisalPercentage,performanceGrade,comments
            String line = String.format("%s,%s,%.2f,%.2f,%.2f,%.1f,%s,%s",
                    result.getEmployeeCode(),
                    result.getEmployeeName(),
                    result.getTotalScore(),
                    result.getCurrentSalary(),
                    result.getNewSalary(),
                    result.getAppraisalPercentage(),
                    result.getPerformanceGrade(),
                    result.getComments().replace(",", ";"));
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to appraisals.csv: " + e.getMessage());
        }
    }

    private static void writeEmployeeToCsv(Employee employee) {
        try (FileWriter fw = new FileWriter("employees.csv", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Write in the same order as the CSV header
            String line = String.format("%s,%s,%s,%s,%.2f",
                    employee.getEmployeeCode(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getJoiningDate(),
                    employee.getCurrentSalary());
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to employees.csv: " + e.getMessage());
        }
    }

    private static Employee getEmployeeFromFileOrManual() {
        System.out.println("\nEmployee Information:");
        ConsoleUtils.printSeparator();

        String employeeCode = ConsoleUtils.getUserInput(scanner, "Employee Code: ");
        Employee employee = findEmployeeInFile(employeeCode);

        if (employee != null) {
            System.out.println("Employee found in file. Details loaded.");
            printEmployeeDetails(employee);
            // Collect remaining performance/rating data
            collectPerformanceAndRatings(employee);
            return employee;
        } else {
            System.out.println("Employee not found. Please enter details manually.");
            return collectEmployeeDataWithCode(employeeCode);
        }
    }

    // Reads basic employee info from CSV file
    private static Employee findEmployeeInFile(String employeeCode) {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(employeeCode)) {
                    String name = parts[1];
                    String department = parts[2];
                    LocalDate joiningDate = LocalDate.parse(parts[3]);
                    double currentSalary = Double.parseDouble(parts[4]);
                    return new Employee(employeeCode, name, department, joiningDate, currentSalary);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    // Print loaded employee details
    private static void printEmployeeDetails(Employee employee) {
        System.out.println("Employee Code: " + employee.getEmployeeCode());
        System.out.println("Employee Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Joining Date: " + employee.getJoiningDate());
        System.out.println("Current Salary: ₹" + String.format("%.2f", employee.getCurrentSalary()));
    }

    // Collect performance and ratings for loaded employee
    private static void collectPerformanceAndRatings(Employee employee) {
        System.out.println("\nPerformance Data:");
        ConsoleUtils.printSeparator();

        employee.setTicketsSolved(ConsoleUtils.getIntInput(scanner, "Tickets Solved: "));
        employee.setTicketsPending(ConsoleUtils.getIntInput(scanner, "Tickets Pending: "));
        employee.setTicketsBacklog(ConsoleUtils.getIntInput(scanner, "Tickets in Backlog: "));

        employee.setHasPoshComplaints(ConsoleUtils.getBooleanInput(scanner, "Any POSH complaints"));
        employee.setHasDisciplinaryActions(ConsoleUtils.getBooleanInput(scanner, "Any disciplinary actions"));

        employee.setTotalWorkingDays(ConsoleUtils.getIntInput(scanner, "Total working days in year: "));
        employee.setDaysLateMoreThan15Min(ConsoleUtils.getIntInput(scanner, "Days late more than 15 minutes: "));
        employee.setDaysLeftEarly(ConsoleUtils.getIntInput(scanner, "Days left early: "));

        employee.setLeavesTaken(ConsoleUtils.getIntInput(scanner, "Total leaves taken: "));

        // Ratings
        System.out.println("\nRatings (1-10 scale):");
        ConsoleUtils.printSeparator();

        employee.setSelfRating(ConsoleUtils.getDoubleInput(scanner, "Self Rating: "));
        employee.setManagerRating(ConsoleUtils.getDoubleInput(scanner, "Manager Rating: "));

        List<Double> colleagueRatings = new ArrayList<>();
        int numColleagues = ConsoleUtils.getIntInput(scanner, "Number of colleague ratings: ");
        for (int i = 0; i < numColleagues; i++) {
            double rating = ConsoleUtils.getDoubleInput(scanner, "Colleague " + (i + 1) + " Rating: ");
            colleagueRatings.add(rating);
        }
        employee.setColleagueRatings(colleagueRatings);
    }

    // Manual entry if not found in file
    private static Employee collectEmployeeDataWithCode(String employeeCode) {
        String name = ConsoleUtils.getUserInput(scanner, "Employee Name: ");
        String department = ConsoleUtils.getUserInput(scanner, "Department: ");

        LocalDate joiningDate = getDateInput("Joining Date (YYYY-MM-DD): ");
        if (joiningDate.isAfter(LocalDate.now())) {
            System.out.println("Joining date cannot be in the future. Please enter a valid date.");
            joiningDate = getDateInput("Joining Date (YYYY-MM-DD): ");
        }
        // Check eligibility immediately
        Employee tempEmployee = new Employee(employeeCode, name, department, joiningDate, 0.0);
        writeEmployeeToCsv(tempEmployee);
        if (!appraisalService.isEligibleForAppraisal(tempEmployee)) {
            System.out.println("Employee not eligible for appraisal. Minimum 1 year service required.");
            scanner.close();
            System.exit(0);
        }

        double currentSalary = ConsoleUtils.getDoubleInput(scanner, "Current Salary: ");
        Employee employee = new Employee(employeeCode, name, department, joiningDate, currentSalary);

        collectPerformanceAndRatings(employee);
        return employee;
    }

    private static LocalDate getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }

    private static void displayResults(AppraisalResult result) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    APPRAISAL RESULTS");
        System.out.println("=".repeat(60));

        if (!result.isEligible()) {
            System.out.println("Employee Code: " + result.getEmployeeCode());
            System.out.println("Employee Name: " + result.getEmployeeName());
            System.out.println("Status: " + result.getComments());
            return;
        }

        System.out.println("Employee Code: " + result.getEmployeeCode());
        System.out.println("Employee Name: " + result.getEmployeeName());
        System.out.println("Total Score: " + String.format("%.2f", result.getTotalScore()) + "/10");
        System.out.println("Performance Grade: " + result.getPerformanceGrade());
        System.out.println("Current Salary: ₹" + String.format("%.2f", result.getCurrentSalary()));
        System.out.println("Appraisal Percentage: " + String.format("%.1f", result.getAppraisalPercentage()) + "%");
        System.out.println("New Salary: ₹" + String.format("%.2f", result.getNewSalary()));
        System.out.println("Salary Increase: ₹" + String.format("%.2f", result.getNewSalary() - result.getCurrentSalary()));
        System.out.println("Comments: " + result.getComments());

        System.out.println("=".repeat(60));
    }
}