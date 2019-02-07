package service;
import model.Commodity;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface CommodityService {
    public ArrayList<Commodity> getCommodityList();

    public String comListToString(ArrayList<Commodity> list);
}
