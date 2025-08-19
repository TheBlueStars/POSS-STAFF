package com.example.possstaff.models;
public class OrderItem {
    public int id;           // menuId
    public String name;
    public int price;
    public int qty;
    public OrderItem(int id,String name,int price,int qty){
        this.id=id; this.name=name; this.price=price; this.qty=qty;
    }
    public int total(){ return price*qty; }
}
