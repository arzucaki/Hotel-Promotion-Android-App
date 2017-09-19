/*******************************************************************************
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.mobile.sherwoodhotels;

/**
 * Created by arzucaki on 25/05/2017.
 */

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class WebService {

    private static String NAMESPACE = "http://tempuri.org/";
    //private static String URL = "http://10.0.0.179/SunTech/HotelApps_WebServices.asmx"; //Local
    private static String URL = "http://service.ceylaner.com.tr/suntech/HotelApps_WebServices.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";
    //public static String CONNECTION_STRING = "Data Source=mssql.ramazangunes.com;Initial Catalog=SunTech_Main;Persist Security Info=True;User ID=SunTech;Password=Metehan923259;Connect Timeout=350000";

    public static String invoke(String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {
            //When timeout occurs handles this....
            Log.w("SocketTimeoutException", "TIMEOUT");

        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error";
            Log.w("WebService Connection", "Connection Failed");
        }
        return resTxt;
    }

    public static String invoke(ArrayList<PropertyInfo> properties, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        for (int i = 0; i < properties.size(); i++) {
            request.addProperty(properties.get(i));
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        //String wifi=wifiIpAddress();


        try {
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            // Invole web service
            androidHttpTransport.debug = true;
            androidHttpTransport.call((SOAP_ACTION + webMethName), envelope);
            //String xml = androidHttpTransport.responseDump;
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {
            //When timeout occurs handles this....
            Log.w("SocketTimeout Exception", "" + e.getMessage());
            resTxt = "SocketTimeout Exception";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        Log.i("SERVICE RESPONSE", resTxt);
        return resTxt;
    }

    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }

}


