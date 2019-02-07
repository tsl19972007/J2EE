package dao;

import model.Commodity;
import model.User;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.persistence.*;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();

    @PersistenceUnit(name = "ServletTest")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;


    public UserDaoImpl() {
        factory = Persistence.createEntityManagerFactory("ServletTest");
        em = factory.createEntityManager();
    }

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public User findUser(String username, String password) {
        User user=null;
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = ?1 and u.password=?2", User.class);
        query.setParameter(1, username);
        query.setParameter(2, password);
        for (Object object : query.getResultList()) {
            user=(User)object;
        }
        em.clear();
        return user;
    }

    @Override
    public boolean addUser(User user) {
        ArrayList<String> usernameList = new ArrayList<String>();
        TypedQuery<String> query = em.createQuery("SELECT u.username FROM User u", String.class);
        for (Object object : query.getResultList()) {
            usernameList.add((String)object);
        }
        if (usernameList.contains((String) user.getUsername())) {
            return false;
        }
        else{
            em.persist(user);
        }
        return true;
    }

    @Override
    public void updateUser(User user) {
        User u=em.find(User.class,user.getId());
        u.setBalance(user.getBalance());
    }
}
