package org.example;

import org.example.dao.UserDao;
import org.example.dto.ReportDto;
import org.example.model.Report;
import org.example.model.ReportSession;
import org.example.model.ReportType;
import org.example.model.User;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class test {
    public static void main(String[] args) {
        UserDao dao = new UserDao();

        User userByTgId = dao.getUserByTgId(129332L);
        System.out.println(userByTgId);
    }
}
