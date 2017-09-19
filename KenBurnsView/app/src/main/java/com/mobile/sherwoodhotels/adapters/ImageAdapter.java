/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Gallery;

import com.mobile.sherwoodhotels.R;

/**
 * Created by arzucaki on 12/06/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int itemBackground;
    Integer [] imageIDs;

    public ImageAdapter(Context c, Integer[] imageIDs){
        context=c;
        TypedArray a=c.obtainStyledAttributes(R.styleable.Gallery1);
        itemBackground=a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
        this.imageIDs=imageIDs;
    }

    @Override
    public int getCount() {
        return imageIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageview=new ImageView(context);
        imageview.setImageResource(imageIDs[position]);
        imageview.setScaleType(ImageView.ScaleType.FIT_XY);
        imageview.setLayoutParams(new Gallery.LayoutParams(150, 120));
        imageview.setBackgroundResource(itemBackground);
        return imageview;
    }
}
