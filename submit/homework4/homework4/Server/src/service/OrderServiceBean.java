package service;

import model.Order;
import dao.OrderDao;
import factory.DaoFactory;
import javax.ejb.Stateless;

@Stateless
public class OrderServiceBean implements OrderService{
    private OrderDao orderDao;

    private final static OrderServiceBean ORDER_SERVICE_BEAN = new OrderServiceBean();

    public static OrderServiceBean getInstance() {
        return ORDER_SERVICE_BEAN;
    }

    public OrderServiceBean() {
        orderDao = DaoFactory.getOrderDao();
    }
    @Override
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }
}
