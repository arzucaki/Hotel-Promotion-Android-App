/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.sherwoodhotels.R;
import com.mobile.sherwoodhotels.models.Cam;

import java.util.ArrayList;

/**
 * Created by arzucaki on 27/05/2017.
 */

public class CamListAdapter extends ArrayAdapter<Cam> {

    private final ArrayList<Cam> list;
    private final Activity context;
    private int[] colors = new int[]{0x34a4a4a4, 0x34fafafa};

    public CamListAdapter(Activity context, ArrayList<Cam> list) {
        super(context, R.layout.fragment_camlist_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        public ImageView camlogo;
        public TextView txtCamName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.fragment_camlist_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.camlogo = (ImageView) view.findViewById(R.id.camlogo);
            viewHolder.txtCamName = (TextView) view.findViewById(R.id.txtCamList);
            view.setTag(viewHolder);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtCamName.setText(list.get(position).getCamName());
        //String camName=list.get(position).getCamName();
        holder.camlogo.setImageResource(R.drawable.camicon);
        view.setId(list.get(position).getCam_id());
        return view;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }

}

