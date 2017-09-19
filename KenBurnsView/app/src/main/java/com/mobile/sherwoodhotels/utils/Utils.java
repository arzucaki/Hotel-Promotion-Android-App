/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.utils;

import com.mobile.sherwoodhotels.models.Cam;
import com.mobile.sherwoodhotels.models.Hotel;
import com.mobile.sherwoodhotels.models.Menu_Item;
import com.mobile.sherwoodhotels.models.Photo;

import java.util.ArrayList;

/**
 * Created by arzucaki on 27/05/2017.
 */

public class Utils {
    //public static User user;
    public static ArrayList<Hotel> hotelArrayList = new ArrayList<>();
    public static ArrayList<Menu_Item> menuArrayList = new ArrayList<>();
    public static ArrayList<Menu_Item> subMenuArrayList = new ArrayList<>();
    public static ArrayList<Menu_Item> galeryMenuArrayList = new ArrayList<>();
    public static ArrayList<Photo> photosArrayList = new ArrayList<>();
    public static ArrayList<Photo> sliderPhotosArrayList = new ArrayList<>();
    public static ArrayList<Cam> camArrayList = new ArrayList<>();
    public static int company_id=1;
    public static int hotel_id;
    public static String hotel_name;
    public static String selectedMenuName;
    public static int selectedModule_id;
    public static String selectedCamName;
    public static int selectedCam_id;
    public static String selectedCamLink;
    public static int connected_module_id;
    public static int language_id;
}
