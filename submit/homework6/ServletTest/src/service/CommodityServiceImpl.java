package service;

import dao.CommodityDao;
import factory.DaoFactory;
import model.Commodity;
import java.util.ArrayList;

public class CommodityServiceImpl implements CommodityService{
    private CommodityDao commodityDao;

    private static CommodityServiceImpl commodityServiceImpl = new CommodityServiceImpl();

    public static CommodityServiceImpl getInstance() {
        return commodityServiceImpl;
    }

    public CommodityServiceImpl() {
        commodityDao = DaoFactory.getCommodityDao();
    }

    @Override
    public ArrayList<Commodity> getCommodityList() {
        return commodityDao.getCommodityList();
    }

    @Override
    public String comListToString(ArrayList<Commodity> list){
        String res="";
        for(int i=0;i<list.size();i++){
            if(i!=0){
                res+=";";
            }
            res+=list.get(i).toString();
        }
        return res;
    }
}
