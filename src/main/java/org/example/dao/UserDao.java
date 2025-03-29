package org.example.dao;

import org.example.model.User;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.Factory.FACTORY;

public class UserDao implements Dao<User> {
    @Override
    public void delete(Long id) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            User entity = session.get(User.class, id);
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении");

        }
    }

    public User getUserByTgId(Long id) {
        try (Session session = FACTORY.openSession()) {
            Query<User> query = session.createQuery("from User where telegramId = :id", User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("EXCEPTION getUserByTgID");
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = FACTORY.openSession()) {
            Query<User> fromUser = session.createQuery("from User ", User.class);
            return fromUser.list();
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    @Override
    public User getById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении");
        }
    }

    @Override
    public void save(User entity) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении");
        }
    }

    @Override
    public void update(User entity) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении");
        }
    }

    public User getUserByUsername(String userName) throws Exception {
        try (Session session = FACTORY.openSession()) {
            Query<User> query = session.createQuery("from User where name = :name", User.class);
            query.setParameter("name",userName);
            return query.getSingleResult();
        }
    }
}
