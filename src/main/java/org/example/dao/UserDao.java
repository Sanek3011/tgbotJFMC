package org.example.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.Factory.FACTORY;
@Slf4j
public class UserDao implements Dao<User> {
    @Override
    public void delete(Long id) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            User entity = session.get(User.class, id);
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            log.warn("Ошибка при удалении пользователя с id {}", id);
            throw new RuntimeException("Ошибка при удалении");

        }
    }
    public Integer deleteByUsername(String username) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User where name = :name");
            query.setParameter("name", username);
            Integer result = query.executeUpdate();
            transaction.commit();
            return result;
        }
    }
    public List<Long> getAllTgIdUsers() {
        try (Session session = FACTORY.openSession()) {
            Query<Long> query = session.createQuery("select telegramId from User where telegramId is not null", Long.class);
            return query.list();
        }
    }

    public User getUserByTgId(Long id) {
        try (Session session = FACTORY.openSession()) {
            Query<User> query = session.createQuery("from User where telegramId = :id", User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("EXCEPTION getUserByTgID");
            log.warn("Пользователь с tgId {} не найден", id);
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
            log.warn("Пользователь с id {} не найден", id);
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
            log.warn("Ошибка при сохранении user с id {} name {} role {} tgId {}",
                    entity.getId() != null ? entity.getId() : "N/A",
                    entity.getName() != null ? entity.getName() : "N/A",
                    entity.getRole() != null ? entity.getRole() : "N/A",
                    entity.getTelegramId() != null ? entity.getTelegramId() : "N/A"
                    );
            throw new RuntimeException("Ошибка при сохранении");
        }
    }


    @Override
    public void update(User user) {
        try (Session session = FACTORY.openSession()) {
            log.info("Изменяем роль у пользователя id {}: новая роль {}", user.getId(), user.getRole());

            Transaction transaction = session.beginTransaction();
            session.merge(user);

            transaction.commit();
            log.info("Обновление пользователя id {}: старая роль {} -> новая роль {}",
                    user.getId(), getById(user.getId()).getRole(), user.getRole());
        } catch (Exception e) {
            log.warn("Ошибка при редактировании user с id {} name {} role {} tgId {}",
                    user.getId() != null ? user.getId() : "N/A",
                    user.getName() != null ? user.getName() : "N/A",
                    user.getRole() != null ? user.getRole() : "N/A",
                    user.getTelegramId() != null ? user.getTelegramId() : "N/A"
            );
            throw new RuntimeException("Ошибка при обновлении", e);
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
