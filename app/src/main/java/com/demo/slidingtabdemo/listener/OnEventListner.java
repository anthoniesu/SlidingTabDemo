package com.demo.slidingtabdemo.listener;

import com.android.volley.VolleyError;
import com.demo.slidingtabdemo.entity.DataSet;

/**
 * Created by Anthonie Su on 2017/11/18.
 */
public interface OnEventListner {
    void getResponse(DataSet dataSet);

    void getErrorResponse(VolleyError error);
}
