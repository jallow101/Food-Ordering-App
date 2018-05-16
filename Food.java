package com.example.reds0n.foodorderclient;

/**
 * Created by reds0n on 2/3/18.
 */

public class Food {

    private String Name,Image,Price,Description,MenuId,Discount;

    public Food(){

    }


    public Food(String name,String price, String image, String desc,String MenuId,String discount){

        this.Name= name;
        this.Price = price;
        this.Image = image;
        this.Description = desc;
        this.MenuId=MenuId;
        this.Discount=discount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setDescription(String desc) {
        this.Description = desc;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getImage() {
        return Image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public String getDescription() {
        return Description;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }
}
