/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

/**
 * Created by arzucaki on 25/05/2017.
 */

public class ServiceParams {
    public ArrayList<PropertyInfo> properties;
    public String methodName;

    public ServiceParams(String methodName, ArrayList<PropertyInfo> properties) {
        this.properties = properties;
        this.methodName = methodName;
    }
}
