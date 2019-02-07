package dao;

import model.Commodity;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class CommodityDaoImpl implements CommodityDao {

    private static CommodityDaoImpl commodityDao = new CommodityDaoImpl();

    public static CommodityDaoImpl getInstance() {
        return commodityDao;
    }

    @Override
    public ArrayList<Commodity> getCommodityList() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Commodity.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();

        ArrayList<Commodity> list = new ArrayList<Commodity>();
        Query query = session.createQuery("SELECT c from Commodity c");
        for (Object object : query.getResultList()) {
            list.add((Commodity) object);
        }
        session.close();
        return list;
    }
}
