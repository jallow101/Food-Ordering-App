package com.example.reds0n.foodorderclient;

/**
 * Created by reds0n on 3/31/18.
 */

public class orders {

    private String Productid;
    private String Productname;
    private String Quantity;
    private String Price;
    private String Discount;
    private String Image;


    public orders(){

    }

    public orders(String productid, String productname, String quantity, String price, String discount, String image) {
        Productid = productid;
        Productname = productname;
        Price = price;
        Quantity = quantity;
        Discount = discount;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
