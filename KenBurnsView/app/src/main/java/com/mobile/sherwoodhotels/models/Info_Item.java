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
 * Created by arzucaki on 31/05/2017.
 */

public class Info_Item {
    private int Info_id;
    private int Module_id;
    private String Info;

    public int getInfo_id() {
        return Info_id;
    }

    public void setInfo_id(int info_id) {
        Info_id = info_id;
    }

    public int getModule_id() {
        return Module_id;
    }

    public void setModule_id(int module_id) {
        Module_id = module_id;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public Info_Item(JSONObject jo) {
        try {
            setInfo_id(jo.getInt("Rec_id"));
            setModule_id(jo.getInt("Module_id"));
            setInfo(jo.getString("Module_Text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
