package factory;

import service.OrderService;
import service.OrderServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import service.CommodityService;
import service.CommodityServiceImpl;

public class ServiceFactory {
    public static OrderService getOrderService() {
        return OrderServiceImpl.getInstance();
    }

    public static UserService getUserService() {
        return UserServiceImpl.getInstance();
    }

    public static CommodityService getCommodityService() {
        return CommodityServiceImpl.getInstance();
    }
}
