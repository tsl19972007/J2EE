package dao;

import model.Order;
import model.OrderItem;

import javax.persistence.*;
import java.sql.*;
import model.User;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();

    @PersistenceUnit(name = "ServletTest")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;

    public OrderDaoImpl() {
        factory = Persistence.createEntityManagerFactory("ServletTest");
        em = factory.createEntityManager();
    }

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }
    @Override
    public void saveOrder(Order order) {
        em.persist(order);
        User user=em.find(User.class,order.getUserId());
        user.setBalance(user.getBalance()-order.getOrderSum());
        em.persist(user);
    }
}