package me.matthewdias.smartmirrorsettings;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class MirrorAPIAdapter {
    private String host;
    private RequestQueue queue;

    public MirrorAPIAdapter(String host, Context context) {
        this.host = host;
        queue = Volley.newRequestQueue(context);
    }

    public void getSetting(String setting) {
        StringRequest request = new StringRequest(GET, host + "/settings/" + setting,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        // update setting in memory
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Request Error", error.toString());
                    }
                });

        queue.add(request);
    }

    public void changeSetting(String setting, String value) {
        StringRequest request = new StringRequest(POST, host + "/settings/" + setting + "?value=" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        // update setting in memory
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Request Error", error.toString());
                    }
                }
        );

        queue.add(request);
    }
}
