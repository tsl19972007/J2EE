package dao;

import model.Commodity;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommodityDaoImpl implements CommodityDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ArrayList<Commodity> getCommodityList() {
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
