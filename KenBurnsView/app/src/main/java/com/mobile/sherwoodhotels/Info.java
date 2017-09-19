/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.sherwoodhotels.adapters.ViewPagerAdapter;
import com.mobile.sherwoodhotels.models.Info_Item;
import com.mobile.sherwoodhotels.models.Photo;
import com.mobile.sherwoodhotels.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.TimerTask;

public class Info extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    private ArrayList<PropertyInfo> properties2 = new ArrayList<>();
    Button liveStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ((TextView)findViewById(R.id.txtModule)).setText(Utils.selectedMenuName);

        liveStream=(Button)findViewById(R.id.watchLive);
        liveStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info.this, CamList.class);
                startActivity(intent);
            }
        });



        if (Utils.hotel_id==1){
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.sub_menu_background);
        }
        else if (Utils.hotel_id==2){
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.dreams2);
        }
        else if (Utils.hotel_id==3){
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.sensimar2);
        }
        else if (Utils.hotel_id==4){
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.greenwood2);
        }
        else if (Utils.hotel_id==5){
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.clubkemer2);
        }
        else if (Utils.hotel_id==6) {
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.prize2);
        }

        if (Utils.selectedMenuName.equals("Kids Club") || Utils.selectedMenuName.equals("детский клуб") ){
            liveStream.setVisibility(View.VISIBLE);
            (findViewById(R.id.infoScreen)).setBackgroundResource(R.drawable.kids_club_background);
        }
        else{
            liveStream.setVisibility(View.INVISIBLE);
        }
        try{
            PropertyInfo propertyInfo1 = new PropertyInfo();
            properties.clear();

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Module_id");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.selectedModule_id);
            properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("language_id");
            propertyInfo1.setType(Integer.class);
            propertyInfo1.setValue(Utils.language_id);
            properties.add(propertyInfo1);

            PropertyInfo propertyInfo2 = new PropertyInfo();
            properties2.clear();

            propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("Module_id");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(Utils.selectedModule_id);
            properties2.add(propertyInfo2);

            ServiceParams serviceparams=new ServiceParams("GetInfo", properties);
            ServiceParams serviceparams2=new ServiceParams("GetInfo_Photo", properties2);

            FirstAsyncTask asyncTask = new FirstAsyncTask(); // First
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serviceparams);
            SecondAsyncTask asyncTask2 = new SecondAsyncTask(); // Second
            asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serviceparams2);

        } catch (Exception e) {
            Toast.makeText(Info.this, getString(R.string.no_internet), Toast.LENGTH_LONG);
        }
    }

    public class FirstAsyncTask extends AsyncTask<ServiceParams, Void, Void> {
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
                Info_Item info_item=new Info_Item(ja.getJSONObject(0));
                ((TextView)findViewById(R.id.txtInfo)).setText(info_item.getInfo());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Info.this);
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
    public class SecondAsyncTask extends AsyncTask<ServiceParams, Void, Void> implements View.OnClickListener{
        ViewPager slider;
        String resp = "";
        ProgressDialog progressDialog;
        Button next;
        Button prev;

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
                Utils.photosArrayList.clear();
                for (int i=0;i<ja.length();i++){
                    Photo photo=new Photo(ja.getJSONObject(i));
                    Utils.photosArrayList.add(photo);
                }

                slider=(ViewPager)findViewById(R.id.infoSlider);

                ViewPagerAdapter adp=new ViewPagerAdapter(getApplicationContext(), Utils.photosArrayList);
                slider.setAdapter(adp);

                //Timer t=new Timer();
                //t.scheduleAtFixedRate(new mytimertask(), 2000, 4000);

                next=(Button)findViewById(R.id.nextinfo);
                prev=(Button)findViewById(R.id.previnfo);
                next.setOnClickListener(this);
                prev.setOnClickListener(this);
                next.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // PRESSED
                                next.setBackgroundResource(R.drawable.arrow_right_active);
                                return true; // if you want to handle the touch event
                            case MotionEvent.ACTION_UP:
                                // RELEASED
                                next.setBackgroundResource(R.drawable.arrow_right_passive);
                                slider.setCurrentItem(slider.getCurrentItem()+1);
                                return true; // if you want to handle the touch event
                        }
                        return false;
                    }
                });
                prev.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // PRESSED
                                prev.setBackgroundResource(R.drawable.arrow_left_active);
                                return true; // if you want to handle the touch event
                            case MotionEvent.ACTION_UP:
                                // RELEASED
                                prev.setBackgroundResource(R.drawable.arrow_left_passive);
                                slider.setCurrentItem(slider.getCurrentItem()-1);
                                return true; // if you want to handle the touch event
                        }
                        return false;
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        public void onClick(View v) {

        }

        public class mytimertask extends TimerTask{

            @Override
            public void run() {

                Info.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0 ; i<Utils.photosArrayList.size(); i++)
                        if (slider.getCurrentItem()==Utils.photosArrayList.size()-1){
                            slider.setCurrentItem(0);
                        }
                        else{
                            slider.setCurrentItem(slider.getCurrentItem()+1);
                        }
                    }
                });
            }
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Info.this);
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
