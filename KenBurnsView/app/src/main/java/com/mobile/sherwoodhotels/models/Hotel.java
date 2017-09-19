/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arzucaki on 25/05/2017.
 */

public class Hotel{
    private String Hotel_Name;
    private int Hotel_id;

    public String getHotel_Name() {
        return Hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        Hotel_Name = hotel_Name;
    }

    public int getHotel_id() {
        return Hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        Hotel_id = hotel_id;
    }

    public Hotel(JSONObject jo) {
        try {
            setHotel_id(jo.getInt("Hotel_id"));
            setHotel_Name(jo.getString("Hotel_Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
