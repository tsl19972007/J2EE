package factory;
import dao.OrderDao;
import dao.OrderDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import dao.CommodityDao;
import dao.CommodityDaoImpl;

public class DaoFactory {
    public static OrderDao getOrderDao() {
        return OrderDaoImpl.getInstance();
    }

    public static UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

    public static CommodityDao getCommodityDao() {
        return CommodityDaoImpl.getInstance();
    }
}
