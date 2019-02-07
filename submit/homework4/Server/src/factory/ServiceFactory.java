package factory;

import service.OrderService;
import service.OrderServiceBean;
import service.UserService;
import service.UserServiceBean;
import service.CommodityService;
import service.CommodityServiceBean;

public class ServiceFactory {
    public static OrderService getOrderService() {
        return OrderServiceBean.getInstance();
    }

    public static UserService getUserService() {
        return UserServiceBean.getInstance();
    }

    public static CommodityService getCommodityService() {
        return CommodityServiceBean.getInstance();
    }
}
