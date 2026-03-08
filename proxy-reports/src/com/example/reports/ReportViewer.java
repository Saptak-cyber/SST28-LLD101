package com.example.reports;

/**
 * ReportViewer now depends on the Report abstraction.
 * Works with both RealReport and ReportProxy through polymorphism.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
