package com.example.reds0n.foodorderclient;

/**
 * Created by reds0n on 4/2/18.
 */

public class users {

    private String name;
    private String phone;
    private String password;
    private String isStaff;

    public users(){

    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public users(String name , String password, String phone) {

        this.name = name;
        this.password = password;
        this.phone = phone;

    }

    public  String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
