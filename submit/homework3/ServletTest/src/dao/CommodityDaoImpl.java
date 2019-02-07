package dao;

import model.Commodity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommodityDaoImpl implements CommodityDao {

    private static CommodityDaoImpl commodityDao = new CommodityDaoImpl();

    public static CommodityDaoImpl getInstance() {
        return commodityDao;
    }

    @Override
    public ArrayList<Commodity> getCommodityList() {
        Connection connection = BaseDao.getConnection();
        ArrayList<Commodity> list = new ArrayList<Commodity>();

        try {
            String select = "select * from commodity";
            Statement sm = connection.createStatement();
            ResultSet result = sm.executeQuery(select);
            while (result.next()) {
                Commodity com = new Commodity();
                com.setId(result.getInt("id"));
                com.setName(result.getString("name"));
                com.setPrice(result.getDouble("price"));
                list.add(com);
                //System.out.println("comid: "+result.getInt("id"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }
}
