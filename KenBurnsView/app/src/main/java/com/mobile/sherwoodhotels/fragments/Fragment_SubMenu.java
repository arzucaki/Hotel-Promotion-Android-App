/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobile.sherwoodhotels.Info;
import com.mobile.sherwoodhotels.R;
import com.mobile.sherwoodhotels.ServiceParams;
import com.mobile.sherwoodhotels.SubMenu;
import com.mobile.sherwoodhotels.WebService;
import com.mobile.sherwoodhotels.adapters.SubMenuListAdapter;
import com.mobile.sherwoodhotels.models.Menu_Item;
import com.mobile.sherwoodhotels.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_SubMenu extends Fragment {

    ListView listMenu;
    private ArrayList<PropertyInfo> properties = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submenu_item_list, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        listMenu = (ListView) view.findViewById(R.id.subMenuList);
        listMenu.setAdapter(new SubMenuListAdapter(getActivity(), Utils.subMenuArrayList));
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Utils.connected_module_id=view.getId();
                Utils.selectedMenuName=Utils.subMenuArrayList.get(i).getMenuName();
                Utils.selectedModule_id=Utils.subMenuArrayList.get(i).getMenu_id();

                if (Utils.subMenuArrayList.get(i).getHasSubMenu()==1)
                {
                    startActivityForResult(new Intent(getActivity(), SubMenu.class), 0);
                }
                else
                {
                    startActivityForResult(new Intent(getActivity(), Info.class), 0);
                }
            }
        });
        try {
            PropertyInfo propertyInfo1 = new PropertyInfo();
            properties.clear();

            Calendar cal = Calendar.getInstance();

            //propertyInfo1.setName("Connection_String");
            //propertyInfo1.setType(String.class);
            //propertyInfo1.setValue(WebService.CONNECTION_STRING);
            //properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Hotel_id");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.hotel_id);
            properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Connected_Module");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.selectedModule_id);
            properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("language_id");
            propertyInfo1.setType(Integer.class);
            propertyInfo1.setValue(Utils.language_id);
            properties.add(propertyInfo1);

            new AsyncTaskService().execute(new ServiceParams("GetSubMenu", properties));

        }catch (Exception e){
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_LONG);
        }
        return view;

    }

    public class AsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(ServiceParams... params) {
            resp = WebService.invoke(params[0].properties, params[0].methodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.w("WEBSERVICE RESPONSE===", resp);

            try {
                JSONArray ja = new JSONArray(resp);
                Utils.subMenuArrayList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    Utils.subMenuArrayList.add(new Menu_Item(ja.getJSONObject(i)));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listMenu.setAdapter(new SubMenuListAdapter(getActivity(), Utils.subMenuArrayList));
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
            if (progressDialog != null) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.processing));
                progressDialog.show();
            }

        }

        protected void onProgressUpdate(Integer... progress) {
            if (progressDialog != null)
                progressDialog.setProgress(progress[0]);
        }

    }
}
