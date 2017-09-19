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
 * Created by arzucaki on 25/05/2017.
 */

public class Menu_Item {

    private String MenuName;
    private int Menu_id;
    private int ConnectedModule_id;
    private int HasSubMenu;

    public int getHasSubMenu() {
        return HasSubMenu;
    }

    public void setHasSubMenu(int hasSubMenu) {
        HasSubMenu = hasSubMenu;
    }

    public Bitmap getMenuImage() {
        return MenuImage;
    }

    public void setMenuImage(Bitmap menuImage) {
        MenuImage = menuImage;
    }

    private Bitmap MenuImage;

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public int getMenu_id() {
        return Menu_id;
    }

    public void setMenu_id(int menu_id) {
        Menu_id = menu_id;
    }
    public int getConnectedModule_id() {
        return ConnectedModule_id;
    }

    public void setConnectedModule_id(int connectedModule_id) {
        ConnectedModule_id = connectedModule_id;
    }

    public Menu_Item(JSONObject jo) {
        try {
            setMenu_id(jo.getInt("Module_id"));
            setMenuName(jo.getString("Module_Name"));
            setConnectedModule_id(jo.getInt("Connected_Module"));
            setMenuImage(base64toBitmap(jo.getString("Photo")));
            setHasSubMenu(jo.getInt("HasSubMenu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap base64toBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
