package model;

public class User {
    private int id;
    private String username;
    private String password;
    private double balance;

    public User(){

    }
    public User(String username,String password,double balance){
        this.username=username;
        this.password=password;
        this.balance=balance;
    }
    public User(String username,String password){
        this.username=username;
        this.password=password;
        this.balance=0;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public double getPrice() {
        return balance;
    }
    public void setPrice(double balance) {
        this.balance = balance;
    }
}
