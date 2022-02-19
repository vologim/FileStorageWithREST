package com.mikhail_golovackii.filestoragewithrest.repository.impl;

import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import com.mikhail_golovackii.filestoragewithrest.repository.UserFileRepository;
import com.mikhail_golovackii.filestoragewithrest.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserFileRepositoryImpl implements UserFileRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public UserFile getUserFileByFileName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return (UserFile) session.createQuery("FROM UserFile u WHERE u.fileName = '" + name + "'").getSingleResult();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void save(UserFile elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public void update(UserFile elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public UserFile getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserFile.class, id);
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<UserFile> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM UserFile").getResultList();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void delete(UserFile iserFile) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(iserFile);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

}
