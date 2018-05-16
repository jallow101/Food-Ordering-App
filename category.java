package com.example.reds0n.foodorderclient;

/**
 * Created by reds0n on 3/15/18.
 */

public class category {

      private String Name;
      private String Image;



  public category(){

  }


    public category (String name, String image){

      this.Name = name;
      this.Image = image;
    }


    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }
}
