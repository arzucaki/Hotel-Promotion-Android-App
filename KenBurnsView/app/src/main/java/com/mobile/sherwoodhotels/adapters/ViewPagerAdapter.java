/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.mobile.sherwoodhotels.R;
import com.mobile.sherwoodhotels.models.Photo;

import java.util.ArrayList;

/**
 * Created by arzucaki on 03/06/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private  Context context;
    private LayoutInflater layoutinflator;
    private Bitmap images[];


    public ViewPagerAdapter(Context context, ArrayList<Photo> photoList) {
        images=new Bitmap[photoList.size()];
        for (int i=0;i<photoList.size(); i++){
            images[i]=photoList.get(i).getImage();
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inf=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inf.inflate(R.layout.costum_layout, null);
        ImageView img=(ImageView)view.findViewById(R.id.imageView);
        img.setImageBitmap(images[position]);

        ((ViewPager)container).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=(View)object;
        ((ViewPager)container).removeView(view);
    }
}
