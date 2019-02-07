package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "commodity")
public class Commodity implements Serializable{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String name;
    private double price;

    public Commodity(){

    }
    public Commodity(int id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return id+","+name+","+price;
    }
}
