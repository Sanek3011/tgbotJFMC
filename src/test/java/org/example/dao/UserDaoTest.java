package org.example.dao;

import org.example.model.Role;
import org.example.model.User;
import org.example.util.Factory;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.util.Factory.FACTORY;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
        User sanya = User.builder()
                .id(1L)
                .name("sanya")
                .role(Role.ADMIN)
                .build();
        try (Session session = FACTORY.openSession()) {
            User user = session.get(User.class, 1L);
            Assertions.assertEquals(sanya, user);
        }
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}