package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public User findUser(String username, String password) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();

        User user=null;
        Query query = session.createQuery("SELECT u FROM User u WHERE u.username = ?1 and u.password=?2");
        query.setParameter(1, username);
        query.setParameter(2, password);
        for (Object object : query.getResultList()) {
            user=(User)object;
        }
        session.close();
        return user;
    }

    @Override
    public boolean addUser(User user) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        ArrayList<String> usernameList = new ArrayList<String>();
        Query query = session.createQuery("SELECT u.username FROM User u");
        for (Object object : query.getResultList()) {
            usernameList.add((String)object);
        }
        if (usernameList.contains((String) user.getUsername())) {
            return false;
        }
        else{
            session.save(user);
        }
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public void updateUser(User user) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        User u=session.find(User.class,user.getId());
        u.setBalance(user.getBalance());
        session.save(u);
        tx.commit();
        session.close();
    }
}
