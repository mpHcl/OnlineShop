package com.onlineshop.shop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String brand;
    private String productName;
    private String description;
    private Date addedDate;
    private double price;
    private double onSale;
    private int amount;
    private int sold;
    private String imgPath;


    public int getID() {
        return ID;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }


    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public double getOnSale() {
        return onSale;
    }

    public void setOnSale(double onSale) {
        this.onSale = onSale;
    }
}
