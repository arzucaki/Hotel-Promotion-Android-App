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

public class Cam {

    private String CamName;
    private String CamLink;
    private int Cam_id;

    public String getCamName() {
        return CamName;
    }

    public void setCamName(String camName) {
        CamName = camName;
    }

    public String getCamLink() {
        return CamLink;
    }

    public void setCamLink(String camLink) {
        CamLink = camLink;
    }

    public int getCam_id() {
        return Cam_id;
    }

    public void setCam_id(int cam_id) {
        Cam_id = cam_id;
    }

    public Cam(JSONObject jo) {
        try {
            setCam_id(jo.getInt("Camera_id"));
            setCamName(jo.getString("Camera_Name"));
            setCamLink(jo.getString("Link"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
