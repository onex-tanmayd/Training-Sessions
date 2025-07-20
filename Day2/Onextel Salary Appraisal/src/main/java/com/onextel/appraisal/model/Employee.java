package com.onextel.appraisal.model;

import java.time.LocalDate;
import java.util.List;

public class Employee {
    private String employeeCode;
    private String name;
    private String department;
    private LocalDate joiningDate;
    private int ticketsSolved;
    private int ticketsPending;
    private int ticketsBacklog;
    private boolean hasPoshComplaints;
    private boolean hasDisciplinaryActions;
    private int daysLateMoreThan15Min;
    private int daysLeftEarly;
    private int totalWorkingDays;
    private int leavesTaken;
    private double selfRating;
    private double managerRating;
    private List<Double> colleagueRatings;
    private double currentSalary;

    // Constructor
    public Employee(String employeeCode, String name, String department,
                    LocalDate joiningDate, double currentSalary) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
        this.currentSalary = currentSalary;
        this.hasPoshComplaints = false;
        this.hasDisciplinaryActions = false;
    }

    // Getters and Setters
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public int getTicketsSolved() { return ticketsSolved; }
    public void setTicketsSolved(int ticketsSolved) { this.ticketsSolved = ticketsSolved; }

    public int getTicketsPending() { return ticketsPending; }
    public void setTicketsPending(int ticketsPending) { this.ticketsPending = ticketsPending; }

    public int getTicketsBacklog() { return ticketsBacklog; }
    public void setTicketsBacklog(int ticketsBacklog) { this.ticketsBacklog = ticketsBacklog; }

    public boolean isHasPoshComplaints() { return hasPoshComplaints; }
    public void setHasPoshComplaints(boolean hasPoshComplaints) { this.hasPoshComplaints = hasPoshComplaints; }

    public boolean isHasDisciplinaryActions() { return hasDisciplinaryActions; }
    public void setHasDisciplinaryActions(boolean hasDisciplinaryActions) { this.hasDisciplinaryActions = hasDisciplinaryActions; }

    public int getDaysLateMoreThan15Min() { return daysLateMoreThan15Min; }
    public void setDaysLateMoreThan15Min(int daysLateMoreThan15Min) { this.daysLateMoreThan15Min = daysLateMoreThan15Min; }

    public int getDaysLeftEarly() { return daysLeftEarly; }
    public void setDaysLeftEarly(int daysLeftEarly) { this.daysLeftEarly = daysLeftEarly; }

    public int getTotalWorkingDays() { return totalWorkingDays; }
    public void setTotalWorkingDays(int totalWorkingDays) { this.totalWorkingDays = totalWorkingDays; }

    public int getLeavesTaken() { return leavesTaken; }
    public void setLeavesTaken(int leavesTaken) { this.leavesTaken = leavesTaken; }

    public double getSelfRating() { return selfRating; }
    public void setSelfRating(double selfRating) { this.selfRating = selfRating; }

    public double getManagerRating() { return managerRating; }
    public void setManagerRating(double managerRating) { this.managerRating = managerRating; }

    public List<Double> getColleagueRatings() { return colleagueRatings; }
    public void setColleagueRatings(List<Double> colleagueRatings) { this.colleagueRatings = colleagueRatings; }

    public double getCurrentSalary() { return currentSalary; }
    public void setCurrentSalary(double currentSalary) { this.currentSalary = currentSalary; }
}