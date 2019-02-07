package service;

import dao.CommodityDao;
import factory.DaoFactory;
import model.Commodity;
import java.util.ArrayList;
import javax.ejb.Stateless;

@Stateless
public class CommodityServiceBean implements CommodityService{
    private CommodityDao commodityDao;

    private final static CommodityServiceBean commodityServiceBean = new CommodityServiceBean();

    public static CommodityServiceBean getInstance() {
        return commodityServiceBean;
    }

    public CommodityServiceBean() {
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
