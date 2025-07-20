package com.onextel.appraisal.model;

public class AppraisalResult {
    private String employeeCode;
    private String employeeName;
    private double totalScore;
    private double currentSalary;
    private double newSalary;
    private double appraisalPercentage;
    private String performanceGrade;
    private String comments;
    private boolean isEligible;

    // Constructor
    public AppraisalResult(String employeeCode, String employeeName) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.isEligible = true;
    }

    // Getters and Setters
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public double getTotalScore() { return totalScore; }
    public void setTotalScore(double totalScore) { this.totalScore = totalScore; }

    public double getCurrentSalary() { return currentSalary; }
    public void setCurrentSalary(double currentSalary) { this.currentSalary = currentSalary; }

    public double getNewSalary() { return newSalary; }
    public void setNewSalary(double newSalary) { this.newSalary = newSalary; }

    public double getAppraisalPercentage() { return appraisalPercentage; }
    public void setAppraisalPercentage(double appraisalPercentage) { this.appraisalPercentage = appraisalPercentage; }

    public String getPerformanceGrade() { return performanceGrade; }
    public void setPerformanceGrade(String performanceGrade) { this.performanceGrade = performanceGrade; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public boolean isEligible() { return isEligible; }
    public void setEligible(boolean eligible) { isEligible = eligible; }
}
