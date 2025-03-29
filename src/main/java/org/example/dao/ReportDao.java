package org.example.dao;

import org.example.model.Report;
import org.example.util.Factory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.Factory.FACTORY;

public class ReportDao implements Dao<Report> {
    @Override
    public void delete(Long id) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            Report report = session.get(Report.class, id);
            session.remove(report);
            transaction.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении");
        }
    }

    @Override
    public List<Report> getAll() {
        try (Session session = FACTORY.openSession()) {
            Query<Report> fromReport = session.createQuery("from Report ", Report.class);
            return fromReport.list();
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Report getById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Report.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении");
        }
    }

    @Override
    public void save(Report entity) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении");
        }
    }

    @Override
    public void update(Report entity) {
        try (Session session = FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка при редактировании");
        }

    }
    public List<Report> getAllReportByUser(Long id) {
        try (Session session = FACTORY.openSession()) {
            Query<Report> query = session.createQuery("from Report where user.id = :id", Report.class);
            query.setParameter("id", id);
            return query.list();
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Report> getAllReportByUsernameAndDate(String userName, LocalDate date) {
        try (Session session = FACTORY.openSession()) {
            Query<Report> query = session.createQuery("from Report where user.name = :username and dateOfCreation between :date and :date2", Report.class);
            query.setParameter("username", userName);
            query.setParameter("date", date);
            query.setParameter("date2", LocalDate.now());
            return query.list();
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }


}
