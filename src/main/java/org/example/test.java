package org.example;

import org.example.dao.ReportDao;
import org.example.dao.UserDao;
import org.example.dto.ReportDto;
import org.example.model.*;
import org.example.service.UserService;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class test {
    public static void main(String[] args) {

        try (Session session = Factory.FACTORY.openSession()) {
            User user = session.get(User.class, 12L);
            UserService service = new UserService();
            service.changeRoleByUsername(user, "sadsad");
        } catch (IllegalArgumentException e) {
            System.out.println("ашипка");
        }

    }
}
