package com.example.reds0n.foodorderclient;

import java.util.List;

/**
 * Created by reds0n on 4/2/18.
 */

public class Order {

    private String name;
    private String phone;
    private String address;
    private String total;
    private String status="0";
    private List<orders> foods;

public  Order(){

}
    public Order(String name, String phone, String address, String total, List<orders> foods) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0"; // by default 0=placed,,...     2=taken... 3=Done
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<orders> getFoods() {
        return foods;
    }

    public void setFoods(List<orders> foods) {
        this.foods = foods;
    }



}
