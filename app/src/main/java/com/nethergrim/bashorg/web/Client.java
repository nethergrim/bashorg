package com.nethergrim.bashorg.web;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.nethergrim.bashorg.App;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class Client {

    public static final String BASE_URL = "http://bash.im/index/";

    public static void getPage(int pageNumber, final WebStringCallback callback){
        String url = BASE_URL + String.valueOf(pageNumber);


        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                callback.callback(response, true);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                callback.callback(null, false);
            }
        });

        App.getInstance().addToRequestQueue(strReq);
    }
}
