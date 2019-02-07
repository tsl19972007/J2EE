package service;

import model.Order;
import dao.OrderDao;
import factory.DaoFactory;

public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao;

    private static OrderServiceImpl orderServiceImpl = new OrderServiceImpl();

    public static OrderServiceImpl getInstance() {
        return orderServiceImpl;
    }

    public OrderServiceImpl() {
        orderDao = DaoFactory.getOrderDao();
    }
    @Override
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }
}
