package dao;

import model.Order;
import model.OrderItem;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println(order.getOrderSum());
        session.save(order);
        User user = session.find(User.class, order.getUserId());
        System.out.println(user.getId());
        user.setBalance(user.getBalance() - order.getOrderSum());
        session.save(user);
        tx.commit();
        session.close();
    }
}
