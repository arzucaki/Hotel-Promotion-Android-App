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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.sherwoodhotels.adapters.SpinnerAdapter;
import com.mobile.sherwoodhotels.models.Hotel;
import com.mobile.sherwoodhotels.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    Button hotelList[];
    int flagSelection=0;
    String CurrentLanguage;
    private int[] flags={R.drawable.turkish_flag, R.drawable.german_flag, R.drawable.russian_flag, R.drawable.english_flag};
    private String[] lang={"Turkish","German","Russian","English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Spinner languageSpinner=(Spinner)findViewById(R.id.spinner);
        CurrentLanguage=getResources().getConfiguration().locale.getLanguage();
        Locale.setDefault(Locale.getDefault());

        //ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //languageSpinner.setAdapter(adapter);
        Resources res = getResources();
        SpinnerAdapter adptr=new SpinnerAdapter(this, res.getStringArray(R.array.languages), flags);
        languageSpinner.setAdapter(adptr);


        if (CurrentLanguage.equals("tr")){
            languageSpinner.setSelection(0);
        }
        else if (CurrentLanguage.equals("en")){
            languageSpinner.setSelection(3);
        }
        else if (CurrentLanguage.equals("deu")){
            languageSpinner.setSelection(1);
        }
        else if (CurrentLanguage.equals("ru")){
            languageSpinner.setSelection(2);
        }
        int position=languageSpinner.getSelectedItemPosition();
        if (position==0){
            Utils.language_id=1;
        }
        else if (position==2){
            Utils.language_id=4;
        }
        else if (position==1){
            Utils.language_id=3;
        }
        else if (position==3){
            Utils.language_id=2;
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flagSelection++;
                if(flagSelection!=1){
                    Locale locale=new Locale("tr");
                    if (position==0){
                        locale = new Locale("tr");
                        Utils.language_id=1;
                    }
                    else if (position==2){
                        locale = new Locale("ru");
                        Utils.language_id=4;
                    }
                    else if (position==1){
                        locale = new Locale("deu");
                        Utils.language_id=3;
                    }
                    else if (position==3){
                        Utils.language_id=2;
                        locale = new Locale("en");
                    }
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    finish();//mevcut acivity i bitir.
                    startActivity(getIntent());//activity i baştan yükle
                    Toast.makeText(getApplicationContext(), getString(R.string.language_changed), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try{
            PropertyInfo propertyInfo1 = new PropertyInfo();
            properties.clear();

            //propertyInfo1.setName("Connection_String");
            //propertyInfo1.setType(String.class);
            //propertyInfo1.setValue(WebService.CONNECTION_STRING);
            //properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Company_id");
            propertyInfo1.setType(Integer.class);
            propertyInfo1.setValue(Utils.company_id);
            properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("language_id");
            propertyInfo1.setType(Integer.class);
            propertyInfo1.setValue(Utils.language_id);
            properties.add(propertyInfo1);

            new AsyncTaskService().execute(new ServiceParams("GetHotels", properties));

        } catch (Exception e) {
            Toast.makeText(Home.this, getString(R.string.no_internet), Toast.LENGTH_LONG);
        }
    }

    public class AsyncTaskService extends AsyncTask<ServiceParams, Void, Void> implements View.OnClickListener {
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
                Utils.hotelArrayList.clear();
                hotelList=new Button[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    Utils.hotelArrayList.add(new Hotel(ja.getJSONObject(i)));
                    Button btn=new Button(getApplicationContext());
                    btn.setId(Utils.hotelArrayList.get(i).getHotel_id());
                    btn.setText(Utils.hotelArrayList.get(i).getHotel_Name());
                    hotelList[i]=btn;
                }

                LinearLayout tblHotels=(LinearLayout) findViewById(R.id.tblLayout);
                if(hotelList != null){
                    int index=0;
                    for (int i=0; i<hotelList.length; i++) {
                        LinearLayout lo=new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) getResources().getDimension(R.dimen.HotelButtonHeight), 1);
                        lo.setOrientation(lo.HORIZONTAL);
                        para.setMargins(5,5,5,5);
                        lo.setLayoutParams(para);
                        lo.setGravity(Gravity.BOTTOM);
                        tblHotels.addView(lo);

                        for (int j = 0; j < 2; j++)
                        {
                            lo.addView(hotelList[index]);
                            LinearLayout.LayoutParams para2=new LinearLayout.LayoutParams(R.dimen.HotelButtonHeight, R.dimen.HotelButtonWidth, 2);
                            hotelList[index].setLayoutParams(para2);
                            para2.setMargins(5,5,5,5);
                            para2.gravity=Gravity.CENTER;
                            hotelList[index].getLayoutParams().height=(int) getResources().getDimension(R.dimen.HotelButtonHeight);
                            hotelList[index].getLayoutParams().width=(int) getResources().getDimension(R.dimen.HotelButtonWidth);
                            //hotelList[index].setTextSize(13);
                            hotelList[index].setGravity(Gravity.CENTER);
                            hotelList[index].setOnClickListener(this);
                            index++;
                        }
                        i++;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (progressDialog != null)
                progressDialog.dismiss();
        }
        public void onClick(View v)
        {
            Intent pchange=new Intent(Home.this, Menu.class);
            Utils.hotel_id=v.getId();
            startActivity(pchange);
        }
        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Home.this);
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
