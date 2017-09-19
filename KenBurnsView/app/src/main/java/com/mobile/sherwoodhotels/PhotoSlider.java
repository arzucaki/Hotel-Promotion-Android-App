/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels;

import android.app.ProgressDialog;
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

import com.mobile.sherwoodhotels.adapters.SliderViewPagerAdapter;
import com.mobile.sherwoodhotels.models.Photo;
import com.mobile.sherwoodhotels.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.TimerTask;

public class PhotoSlider extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_slider);

        ((TextView)findViewById(R.id.txtModuleName)).setText(Utils.selectedMenuName);

        try{
            PropertyInfo propertyInfo1 = new PropertyInfo();
            properties.clear();

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Module_id");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.selectedModule_id);
            properties.add(propertyInfo1);

            ServiceParams serviceparams=new ServiceParams("GetInfo_Photo", properties);
            new AsyncTaskService().execute(serviceparams);

        } catch (Exception e) {
            Toast.makeText(PhotoSlider.this, "Please check your internet connection.", Toast.LENGTH_LONG);
        }
    }
    public class AsyncTaskService extends AsyncTask<ServiceParams, Void, Void>{
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
                Utils.sliderPhotosArrayList.clear();
                for (int i=0;i<ja.length();i++){
                    Photo photo=new Photo(ja.getJSONObject(i));
                    Utils.sliderPhotosArrayList.add(photo);
                }

                slider=(ViewPager)findViewById(R.id.photoSlider);

                SliderViewPagerAdapter adp=new SliderViewPagerAdapter(getApplicationContext(), Utils.sliderPhotosArrayList);
                slider.setAdapter(adp);

                next=(Button)findViewById(R.id.next);
                prev=(Button)findViewById(R.id.prev);


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

        public class mytimertask2 extends TimerTask {

            @Override
            public void run() {

                PhotoSlider.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0 ; i<Utils.sliderPhotosArrayList.size(); i++)
                            if (slider.getCurrentItem()==(Utils.sliderPhotosArrayList.size()-1)){
                                slider.setCurrentItem(0);
                            }
                            else{
                                slider.setCurrentItem(i+1);
                            }
                    }
                });
            }
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(PhotoSlider.this);
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
