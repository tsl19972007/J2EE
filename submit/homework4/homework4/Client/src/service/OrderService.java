package service;

import model.Order;
import javax.ejb.Remote;

@Remote
public interface OrderService {
    public void saveOrder(Order order);
}
