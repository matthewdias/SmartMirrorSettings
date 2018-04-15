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

import org.json.JSONObject;

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

    public void setHost(String host) {
        this.host = host;
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

    public void changeSetting(final String setting, String value) {
        String url = host + "/settings/" + setting + "?value=" + value;
        StringRequest request = new StringRequest(POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(setting, response);
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

    private void onSuccess(String setting, String response) {
        Log.d("Response", response);
        SharedPreferences.Editor prefs = context.getSharedPreferences(context.getString(R.string.preference_title_key), Context.MODE_PRIVATE).edit();
        JSONObject reader;
        try {
            reader = new JSONObject(response);
            if (setting.equals("zipcode")) {
                String locality = reader.getString("locality");
                prefs.putString("locality", locality);
                String timeZoneName = reader.getString("timeZoneName");
                prefs.putString("timeZoneName", timeZoneName);
                ((MainActivity)context).updateLocation(locality, timeZoneName);
            } else {
                prefs.putString(setting, response);
            }
            prefs.commit();
        } catch (Exception e) {
            Log.d("json", e.toString());
        }
    }

    private void onError(VolleyError error) {
        Log.e("Request Error", error.toString());
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
