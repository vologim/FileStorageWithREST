
package com.mikhail_golovackii.filestoragewithrest.utils;

import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.model.User;
import com.mikhail_golovackii.filestoragewithrest.model.UserFile;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static HibernateUtil instance;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserFile.class)
                .buildSessionFactory();
    }
    
    public HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }
    
    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return sessionFactory;
    }
}
