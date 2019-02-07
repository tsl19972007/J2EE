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

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }


    @Override
    public void saveOrder(Order order) {
        Configuration config = new Configuration().configure();
        /*
        addAnnotatedClass需要添加全部涉及到的class
        Order 与 item 有一对多关系，都要添加
         */
        config.addAnnotatedClass(Order.class);
        config.addAnnotatedClass(OrderItem.class);
        config.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
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
