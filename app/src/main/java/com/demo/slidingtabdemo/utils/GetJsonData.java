package com.demo.slidingtabdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.demo.slidingtabdemo.entity.DataSet;
import com.demo.slidingtabdemo.listener.OnEventListner;

import java.io.IOException;
import java.io.InputStream;

public class GetJsonData implements Response.Listener, Response.ErrorListener {

    public final static int TYPE_CITY_GUIDE = 0;
    public final static int TYPE_SHOP = 1;
    public final static int TYPE_EAT = 2;
    // Fake URL which use make sure it can be connected and got response.
    private final String URL = "https://www.google.com.tw";
    private RequestQueue mQueue = null;
    private OnEventListner mListner = null;
    private Context mContext = null;
    private int mType = TYPE_CITY_GUIDE;

    public GetJsonData(Context context, OnEventListner listner) {
        mContext = context;
        mQueue = Volley.newRequestQueue(context);
        mListner = listner;
    }

    public void getJsonData(int type) {
        mType = type;
        try {
            // GET method with fake URL to conntect Google.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, this, this);

            // We can use below code to send request and get JSON responses
            /*JSONObject obj = new JSONObject();
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, obj, this, this) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

            // Start to send request
            mQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            mListner.getErrorResponse(new VolleyError(e));
        }

    }

    @Override
    public void onResponse(Object response) {
        // Here we fake the JSON response from JSON file instead real response.
        try {
            mListner.getResponse(readJson(mType));
        } catch (Exception e) {
            e.printStackTrace();
            mListner.getErrorResponse(new VolleyError(e));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mListner.getErrorResponse(error);
    }

    // We load json file from assets and parse it by LoganSquare library.
    private DataSet readJson(int type) {
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = null;
        DataSet dataSet = null;
        try {
            switch (type) {
                case TYPE_CITY_GUIDE:
                    inputStream = assetManager.open("result_city_guide.json");
                    break;
                case TYPE_SHOP:
                    inputStream = assetManager.open("result_shop.json");
                    break;
                case TYPE_EAT:
                    inputStream = assetManager.open("result_eat.json");
                    break;
                default:
                    inputStream = assetManager.open("result_city_guide.json");
            }
            dataSet = LoganSquare.parse(inputStream, DataSet.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataSet;
    }
}
