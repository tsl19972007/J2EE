package dao;

import model.Commodity;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommodityDaoImpl implements CommodityDao {

    private static CommodityDaoImpl commodityDao = new CommodityDaoImpl();

    @PersistenceUnit(name = "ServletTest")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;


    public CommodityDaoImpl() {
        factory = Persistence.createEntityManagerFactory("ServletTest");
        em = factory.createEntityManager();
    }

    public static CommodityDaoImpl getInstance() {
        return commodityDao;
    }

    @Override
    public ArrayList<Commodity> getCommodityList() {
        ArrayList<Commodity> list = new ArrayList<Commodity>();
        TypedQuery<Commodity> query = em.createQuery("SELECT c from Commodity c", Commodity.class);
        for (Object object : query.getResultList()) {
            list.add((Commodity) object);
        }
        em.clear();
        return list;
    }
}
