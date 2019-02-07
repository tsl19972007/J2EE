package factory;

import service.OrderService;
import service.OrderServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import service.CommodityService;
import service.CommodityServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {
    public static OrderService getOrderService() {
        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
        return (OrderService) applicationContext.getBean("orderService");
    }

    public static UserService getUserService() {
        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
        return (UserService) applicationContext.getBean("userService");
    }

    public static CommodityService getCommodityService() {
        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
        return (CommodityService) applicationContext.getBean("commodityService");
    }
}
