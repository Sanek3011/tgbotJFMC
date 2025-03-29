package org.example.service;

import org.example.dao.ReportDao;
import org.example.model.Report;
import org.example.model.User;

import java.time.LocalDate;
import java.util.List;

public class ReportService {
    private ReportDao dao = new ReportDao();


    public List<Report> getAllReportByUser(User user) {

        return dao.getAllReportByUser(user.getId());

    }
    public List<Report> getReportsByUsernameAndDate(User user, LocalDate date) {
        return dao.getAllReportByUsernameAndDate(user.getName(), date);


    }
    public List<Report> getAllReports() {
        return dao.getAll();
    }

    public void saveReport(Report report) {
        dao.save(report);
    }
}
