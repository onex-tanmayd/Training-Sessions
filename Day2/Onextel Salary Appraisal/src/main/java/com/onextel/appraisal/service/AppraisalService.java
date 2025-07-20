package com.onextel.appraisal.service;

import com.onextel.appraisal.model.Employee;
import com.onextel.appraisal.model.AppraisalResult;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AppraisalService {

    private static final int ANNUAL_LEAVE_QUOTA = 30; // 18 paid + 12 casual
    private static final double TICKET_WEIGHT = 0.25;
    private static final double ATTENDANCE_WEIGHT = 0.20;
    private static final double RATING_WEIGHT = 0.30;
    private static final double CONDUCT_WEIGHT = 0.15;
    private static final double LEAVE_WEIGHT = 0.10;

    public AppraisalResult calculateAppraisal(Employee employee) {
        AppraisalResult result = new AppraisalResult(employee.getEmployeeCode(), employee.getName());

//        // Check if employee is eligible (minimum 1 year service)
//        if (!isEligibleForAppraisal(employee)) {
//            result.setEligible(false);
//            result.setComments("Employee not eligible for appraisal. Minimum 1 year service required.");
//            return result;
//        }

        // Calculate individual scores
        double ticketScore = calculateTicketScore(employee);
        double attendanceScore = calculateAttendanceScore(employee);
        double ratingScore = calculateRatingScore(employee);
        double conductScore = calculateConductScore(employee);
        double leaveScore = calculateLeaveScore(employee);

        // Calculate weighted total score
        double totalScore = (ticketScore * TICKET_WEIGHT) +
                (attendanceScore * ATTENDANCE_WEIGHT) +
                (ratingScore * RATING_WEIGHT) +
                (conductScore * CONDUCT_WEIGHT) +
                (leaveScore * LEAVE_WEIGHT);

        // Set result values
        result.setTotalScore(totalScore);
        result.setCurrentSalary(employee.getCurrentSalary());
        result.setPerformanceGrade(getPerformanceGrade(totalScore));
        result.setAppraisalPercentage(getAppraisalPercentage(totalScore));
        result.setNewSalary(employee.getCurrentSalary() * (1 + result.getAppraisalPercentage() / 100));
        result.setComments(generateComments(totalScore, employee));

        return result;
    }

    public boolean isEligibleForAppraisal(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        long monthsWorked = ChronoUnit.MONTHS.between(employee.getJoiningDate(), currentDate);
        return monthsWorked >= 12;
    }

    private double calculateTicketScore(Employee employee) {
        int totalTickets = employee.getTicketsSolved() + employee.getTicketsPending() + employee.getTicketsBacklog();

        if (totalTickets == 0) {
            return 5.0; // Average score if no tickets assigned
        }

        double resolutionRate = (double) employee.getTicketsSolved() / totalTickets;

        // Score calculation based on resolution rate
        if (resolutionRate >= 0.95) return 10.0;
        else if (resolutionRate >= 0.90) return 9.0;
        else if (resolutionRate >= 0.85) return 8.0;
        else if (resolutionRate >= 0.80) return 7.0;
        else if (resolutionRate >= 0.75) return 6.0;
        else if (resolutionRate >= 0.70) return 5.0;
        else if (resolutionRate >= 0.60) return 4.0;
        else if (resolutionRate >= 0.50) return 3.0;
        else if (resolutionRate >= 0.40) return 2.0;
        else return 1.0;
    }

    private double calculateAttendanceScore(Employee employee) {
        double latePercentage = (double) employee.getDaysLateMoreThan15Min() / employee.getTotalWorkingDays();
        double earlyLeavePercentage = (double) employee.getDaysLeftEarly() / employee.getTotalWorkingDays();

        double attendanceScore = 10.0;

        // Deduct points for being late
        if (latePercentage > 0.20) attendanceScore -= 4.0;
        else if (latePercentage > 0.15) attendanceScore -= 3.0;
        else if (latePercentage > 0.10) attendanceScore -= 2.0;
        else if (latePercentage > 0.05) attendanceScore -= 1.0;

        // Deduct points for leaving early
        if (earlyLeavePercentage > 0.20) attendanceScore -= 3.0;
        else if (earlyLeavePercentage > 0.15) attendanceScore -= 2.0;
        else if (earlyLeavePercentage > 0.10) attendanceScore -= 1.0;

        return Math.max(1.0, attendanceScore);
    }

    private double calculateRatingScore(Employee employee) {
        double selfRating = employee.getSelfRating();
        double managerRating = employee.getManagerRating();

        // Calculate average colleague rating
        double avgColleagueRating = 0.0;
        if (employee.getColleagueRatings() != null && !employee.getColleagueRatings().isEmpty()) {
            avgColleagueRating = employee.getColleagueRatings().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
        }

        // Weighted average: Manager 50%, Colleagues 30%, Self 20%
        return (managerRating * 0.5) + (avgColleagueRating * 0.3) + (selfRating * 0.2);
    }

    private double calculateConductScore(Employee employee) {
        double conductScore = 10.0;

        if (employee.isHasPoshComplaints()) {
            conductScore -= 5.0;
        }

        if (employee.isHasDisciplinaryActions()) {
            conductScore -= 3.0;
        }

        return Math.max(1.0, conductScore);
    }

    private double calculateLeaveScore(Employee employee) {
        if (employee.getLeavesTaken() <= ANNUAL_LEAVE_QUOTA) {
            return 10.0;
        }

        int excessLeaves = employee.getLeavesTaken() - ANNUAL_LEAVE_QUOTA;
        double deduction = excessLeaves * 0.5; // 0.5 points per excess leave

        return Math.max(1.0, 10.0 - deduction);
    }

    private String getPerformanceGrade(double score) {
        if (score >= 9.0) return "A+";
        else if (score >= 8.0) return "A";
        else if (score >= 7.0) return "B+";
        else if (score >= 6.0) return "B";
        else if (score >= 5.0) return "C+";
        else if (score >= 4.0) return "C";
        else return "D";
    }

    private double getAppraisalPercentage(double score) {
        if (score >= 9.0) return 20.0;
        else if (score >= 8.0) return 15.0;
        else if (score >= 7.0) return 12.0;
        else if (score >= 6.0) return 8.0;
        else if (score >= 5.0) return 5.0;
        else if (score >= 4.0) return 2.0;
        else return 0.0;
    }

    private String generateComments(double totalScore, Employee employee) {
        StringBuilder comments = new StringBuilder();

        if (totalScore >= 8.0) {
            comments.append("Excellent performance. ");
        } else if (totalScore >= 6.0) {
            comments.append("Good performance. ");
        } else if (totalScore >= 4.0) {
            comments.append("Average performance. ");
        } else {
            comments.append("Below average performance. ");
        }

        if (employee.isHasPoshComplaints()) {
            comments.append("POSH complaints noted. ");
        }

        if (employee.isHasDisciplinaryActions()) {
            comments.append("Disciplinary actions noted. ");
        }

        if (employee.getLeavesTaken() > ANNUAL_LEAVE_QUOTA) {
            comments.append("Exceeded annual leave quota. ");
        }

        return comments.toString();
    }
}



