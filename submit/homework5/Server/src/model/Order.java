package model;
import java.util.List;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private int user_id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id")
    List<OrderItem> itemList;

    private double order_sum;
    private String remarks;

    public Order(){}
    public Order(int id, int user_id, List<OrderItem> itemList, double order_sum, String remarks){
        this.id=id;
        this.user_id=user_id;
        this.itemList=itemList;
        this.order_sum=order_sum;
        this.remarks=remarks;
    }
    public Order(int user_id,List<OrderItem> itemList){
        this.user_id=user_id;
        this.itemList=itemList;
        double sum=0;
        for(int i=0;i<itemList.size();i++){
            sum+=itemList.get(i).getItemSum();
        }
        this.order_sum=sum;
        this.remarks="无";
    }

    public int getOrderId() {
        return id;
    }
    public void setOrderId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return user_id;
    }
    public void setUserId(int id) {
        this.user_id = id;
    }
    public void setItemList(List<OrderItem> itemList){
        this.itemList=itemList;
    }
    public List<OrderItem> getItemList() {
        return itemList;
    }
    public double getOrderSum() {
        return order_sum;
    }
    public void setOrderSum(double sum) {
        this.order_sum = sum;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String toString(){
            return id+","+user_id+","+orderListToString()+","+order_sum+","+remarks;
    }

    private String orderListToString(){
        String res="";
        for(int i=0;i<itemList.size();i++){
            if(i!=0) res+=";";
            res+=itemList.get(i).toString();
        }
        return res;
    }
}
