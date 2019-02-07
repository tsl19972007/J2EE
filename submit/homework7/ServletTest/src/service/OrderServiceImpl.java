package service;

import model.Order;
import dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Override
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }
}
