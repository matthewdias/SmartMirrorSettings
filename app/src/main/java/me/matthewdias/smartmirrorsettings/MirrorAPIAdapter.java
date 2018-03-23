package me.matthewdias.smartmirrorsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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
    private MirrorAPIAdapter _mirrorAPIAdapter;
    private Context context;

    public MirrorAPIAdapter(String host, Context context) {
        this.host = host;
        this.context = context;
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
                        onError(error);
                    }
                });

        queue.add(request);
    }

    public void changeSetting(String setting, String value) {
        StringRequest request = new StringRequest(POST, host + "/settings/" + setting + "?value=" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError(error);
                    }
                }
        );

        queue.add(request);
    }

    public void onResponse(String response) {
        Log.d("Response", response);
        String setting = "a";
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_title_key), Context.MODE_PRIVATE);
        prefs.edit().putString(setting, response).commit();
    }

    private void onError(VolleyError error) {
        Log.e("Request Error", error.toString());
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
