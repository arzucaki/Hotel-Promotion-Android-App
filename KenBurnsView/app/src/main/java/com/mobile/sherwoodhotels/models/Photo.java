/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arzucaki on 01/06/2017.
 */

public class Photo {
    private int Photo_id;
    private Bitmap Image;
    private int Module_id;

    public int getModule_id() {
        return Module_id;
    }

    public void setModule_id(int module_id) {
        Module_id = module_id;
    }

    public int getPhoto_id() {
        return Photo_id;
    }

    public void setPhoto_id(int photo_id) {
        Photo_id = photo_id;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public Photo(JSONObject jo) {
        try {
            setPhoto_id(jo.getInt("Photo_Rec_id"));
            setModule_id(jo.getInt("Module_id"));
            setImage(base64toBitmap(jo.getString("Photo")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap base64toBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return Bitmap.createScaledBitmap(decodedByte, 1700, 1100, true);
    }
}
