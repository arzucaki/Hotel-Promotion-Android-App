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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.sherwoodhotels.models.Info_Item;
import com.mobile.sherwoodhotels.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class CamLogin extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    EditText mail;
    EditText roomNum;
    Button login;
    boolean loged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_login);
        mail=(EditText)findViewById(R.id.mail);
        roomNum=(EditText)findViewById(R.id.roomNum);
        login=(Button)findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PropertyInfo propertyInfo1 = new PropertyInfo();
                    properties.clear();

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Module_id");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(Utils.selectedModule_id);
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Module_id");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(Utils.selectedModule_id);
                    properties.add(propertyInfo1);

                    ServiceParams serviceparams=new ServiceParams("GetInfo", properties);

                    CamLogin.FirstAsyncTask asyncTask = new CamLogin.FirstAsyncTask(); // First
                    asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serviceparams);

                    if (loged){
                        Toast.makeText(CamLogin.this, "Giriş Başarılı", Toast.LENGTH_LONG);
                        Intent pchange=new Intent(CamLogin.this, CamList.class);
                        startActivity(pchange);
                    }
                    else{
                        Toast.makeText(CamLogin.this, "Please check your e-mail adress or room number.", Toast.LENGTH_LONG);
                    }

                } catch (Exception e) {
                    Toast.makeText(CamLogin.this, "Please check your internet connection.", Toast.LENGTH_LONG);
                }
            }
        });
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

            progressDialog = new ProgressDialog(CamLogin.this);
            if (progressDialog != null) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("İşlem yapılıyor ...");
                progressDialog.show();
            }
        }

        protected void onProgressUpdate(Integer... progress) {
            if (progressDialog != null)
                progressDialog.setProgress(progress[0]);
        }

    }
}
