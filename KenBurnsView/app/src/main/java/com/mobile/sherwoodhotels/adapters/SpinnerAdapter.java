/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.sherwoodhotels.R;

/**
 * Created by arzucaki on 27/05/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context c;
    private int[] flags;
    private String[] languages;

    public SpinnerAdapter(Activity context, String[] languages , int [] images) {
        super(context, R.layout.language_item, languages);
        this.c = context;
        this.flags=images;
        this.languages=languages;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflator=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.language_item,null);
        }
        TextView language=(TextView)convertView.findViewById(R.id.language);
        ImageView flag=(ImageView)convertView.findViewById(R.id.flagImage);

        language.setText(languages[position]);
        flag.setImageResource(flags[position]);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflator=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.language_item,null);
        }
        TextView language=(TextView)convertView.findViewById(R.id.language);
        ImageView flag=(ImageView)convertView.findViewById(R.id.flagImage);

        language.setText(languages[position]);
        flag.setImageResource(flags[position]);
        return convertView;
    }
}

