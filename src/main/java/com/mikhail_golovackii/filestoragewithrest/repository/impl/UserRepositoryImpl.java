
package com.mikhail_golovackii.filestoragewithrest.repository.impl;

import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.repository.UserRepository;
import com.mikhail_golovackii.filestoragewithrest.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    @Override
    public void save(User elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public void update(User elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return (User) session.createQuery("FROM User u LEFT JOIN FETCH u.files WHERE u.id = " + id).getSingleResult();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User u LEFT JOIN FETCH u.files").getResultList();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }
}
