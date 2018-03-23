package me.matthewdias.smartmirrorsettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CalendarFragment extends Fragment {
    public CalendarFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.googleButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEdit = getView().findViewById(R.id.emailEdit);
                EditText passEdit = getView().findViewById(R.id.passwordEdit);
                MirrorAPIAdapter adapter = ((MainActivity)getActivity()).getMirrorAPIAdapter();
                adapter.changeSetting("google", emailEdit.getText().toString() + '/' + passEdit.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
