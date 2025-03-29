package org.example;

import org.example.dao.ReportDao;
import org.example.dao.UserDao;
import org.example.dto.ReportDto;
import org.example.model.Report;
import org.example.model.ReportSession;
import org.example.model.ReportType;
import org.example.model.User;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class test {
    public static void main(String[] args) {
        ReportDao dao = new ReportDao();
        List<Report> all = dao.getAll();
        System.out.println(all.get(0));
    }
}
