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
                LocationController.submitLocation(zipEdit.getText().toString(), (MainActivity) getActivity());
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getString(R.string.preference_title_key), Context.MODE_PRIVATE);
        city = prefs.getString("locality", "");
        timezone = prefs.getString("timeZoneName", "");

        if (!city.equals("")) {
            ((MainActivity)getActivity()).updateLocation(city, timezone);
        } else {
            LocationController.getLocation((MainActivity)getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }
}
