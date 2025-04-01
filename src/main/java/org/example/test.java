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

        String input = "minus1";
        String[] parts = input.split("(?<=\\D)(?=\\d)"); // Разделяет между знаком и цифрой
        System.out.println("Знак: " + parts[0]);
        System.out.println("Число: " + parts[1]);

    }
}
