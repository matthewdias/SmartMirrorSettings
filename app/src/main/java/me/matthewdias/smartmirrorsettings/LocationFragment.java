package me.matthewdias.smartmirrorsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LocationFragment extends Fragment {
    private String city;
    private String timezone;
    public LocationFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.zipcodeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText zipEdit = getView().findViewById(R.id.zipcodeEdit);
                MirrorAPIAdapter adapter = ((MainActivity)getActivity()).getMirrorAPIAdapter();
                adapter.changeSetting("zipcode", zipEdit.getText().toString());
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getString(R.string.preference_title_key), Context.MODE_PRIVATE);
        city = prefs.getString("city", "");
        timezone = prefs.getString("timezone", "");

        if (!city.equals("")) {
            updateLocation();
        } else {
            MirrorAPIAdapter adapter = ((MainActivity)getActivity()).getMirrorAPIAdapter();
            adapter.getSetting("zipcode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    public void updateLocation() {
        TextView cityText = getView().findViewById(R.id.cityText);
        TextView tzText = getView().findViewById(R.id.timezoneText);
        cityText.setText(city);
        tzText.setText(timezone);
        cityText.setVisibility(View.VISIBLE);
        tzText.setVisibility(View.VISIBLE);
    }
}
