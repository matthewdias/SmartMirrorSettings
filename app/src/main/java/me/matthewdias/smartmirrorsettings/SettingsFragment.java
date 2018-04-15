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


public class SettingsFragment extends Fragment {
    public SettingsFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.ipButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ipText = getView().findViewById(R.id.ipText);
                SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getString(R.string.preference_title_key), Context.MODE_PRIVATE);
                String ip = ipText.getText().toString();
                prefs.edit().putString("ip", ip).commit();
                ((MainActivity)getActivity()).getMirrorAPIAdapter().setHost(ip);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
