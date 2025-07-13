package com.Akaei.Store.BountyMarket.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer uid;
    @Column(name = "username")
    private String username;
    @Column(name = "link")
    private String link;
    @Column(name = "price")
    private double price;

    public Product(){

    }
    public Product(String username, String link, double price) {
        this.username = username;
        this.link = link;
        this.price = price;
    }

    public Integer getUid() {
        return uid;
    }

    public String getUsername(){
        return username;
    }

    public String getLink(){
        return link;
    }

    public double getPrice(){
        return price;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPrice(double price) {
        this.price = price;
    }   

    public void setUid(Integer uid){
        this.uid = uid;
    }
}
