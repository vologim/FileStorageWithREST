
package com.mikhail_golovackii.filestoragewithrest.repository.impl;

import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.repository.EventRepository;
import com.mikhail_golovackii.filestoragewithrest.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EventRepositoryImpl implements EventRepository {

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    @Override
    public void save(Event elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public void update(Event elem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(elem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public Event getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Event.class, id);
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Event").getResultList();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void delete(Event event) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(event);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
        }
    }

    @Override
    public Event getEventByFileName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return (Event) session.createQuery("FROM Event e WHERE e.fileName = '" + name + "'").getSingleResult();
        } catch (Exception ex) {
            System.out.println("Error + " + ex.getMessage());
            return null;
        }
    }

}
