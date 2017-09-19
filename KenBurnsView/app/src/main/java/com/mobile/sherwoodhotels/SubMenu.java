/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.sherwoodhotels.utils.Utils;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class SubMenu extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    Button subMenuList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        ((TextView)findViewById(R.id.txtSubMenu)).setText(Utils.selectedMenuName);

        if (Utils.hotel_id==1){
            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.main_menu_background);
        }
        else if (Utils.hotel_id==2){
            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.dreams);
        }
        else if (Utils.hotel_id==3){
            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.sensimar1);
        }
        else if (Utils.hotel_id==4){
            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.greenwood);
        }
        else if (Utils.hotel_id==5){
            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.clubkemer);
        }
        else if (Utils.hotel_id==6) {

            (findViewById(R.id.submenuScreen)).setBackgroundResource(R.drawable.prize1);
        }

    }

}
