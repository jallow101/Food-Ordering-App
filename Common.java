package com.example.reds0n.foodorderclient;

/**
 * Created by reds0n on 4/2/18.
 */

public class Common {

    public static users currentuser;

    public static final String DELETE = "Delete";
    public static final String UPDATE = "UPDATE";

    public static String convertCodeToStatus(String code) {

        if (code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "Taken";
        else
            return "Done";
    }

}
