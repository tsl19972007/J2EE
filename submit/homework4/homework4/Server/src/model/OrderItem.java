package model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderItem implements Serializable {
    private int commodity_id;
    private double price;
    private int num;
    private double item_sum;

    public OrderItem(){ }
    public OrderItem(int commodity_id, double price, int num, double item_sum){
        this.commodity_id=commodity_id;
        this.price=price;
        this.num=num;
        this.item_sum=item_sum;
    }

    public int getCommodityId() {
        return commodity_id;
    }
    public void setCommodityId(int id) {
        this.commodity_id = id;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public double getItemSum() {
        return item_sum;
    }
    public void setItemSum(double sum) {
        this.item_sum = sum;
    }

    public String toString(){
        return commodity_id+":"+price+":"+num+":"+item_sum;
    }
}
