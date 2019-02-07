package service;

import dao.CommodityDao;
import model.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CommodityServiceImpl implements CommodityService{
    @Autowired
    private CommodityDao commodityDao;

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
