package service;
import model.Commodity;
import java.util.ArrayList;

public interface CommodityService {
    public ArrayList<Commodity> getCommodityList();

    public String comListToString(ArrayList<Commodity> list);
}
