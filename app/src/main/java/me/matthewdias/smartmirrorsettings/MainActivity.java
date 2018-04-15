package me.matthewdias.smartmirrorsettings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MirrorAPIAdapter mirrorAPIAdapter;

    LocationFragment locationFragment;
    CalendarFragment calendarFragment;
    SettingsFragment settingsFragment;
    MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_location:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_calendar:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_settings:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        viewPager = findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(pageListener);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        locationFragment = new LocationFragment();
        adapter.addFragment(locationFragment);
        calendarFragment = new CalendarFragment();
        adapter.addFragment(calendarFragment);
        settingsFragment = new SettingsFragment();
        adapter.addFragment(settingsFragment);

        viewPager.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_title_key), Context.MODE_PRIVATE);
        mirrorAPIAdapter = new MirrorAPIAdapter(prefs.getString("ip", getString(R.string.mirror_host)), this);
    }

    public MirrorAPIAdapter getMirrorAPIAdapter() {
        if (mirrorAPIAdapter == null) {
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_title_key), Context.MODE_PRIVATE);
            mirrorAPIAdapter = new MirrorAPIAdapter(prefs.getString("ip", getString(R.string.mirror_host)), this);
        }
        return mirrorAPIAdapter;
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        Log.d("here", "on");
        super.onActivityResult(requestCode, resultCode, data);
        CalendarController.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateProfile(Uri image, final String name) {
        new AsyncTask<Uri, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Uri... params) {
                try {
                    URL url = new URL(params[0].toString());
                    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (Exception e) {
                    Log.d("error", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bmp) {
                ((ImageView)findViewById(R.id.profileImage)).setImageBitmap(bmp);
                ((TextView)findViewById(R.id.accountNameText)).setText(name);
                findViewById(R.id.googleButton).setVisibility(View.INVISIBLE);
            }
        }.execute(image);
    }

    public void updateLocation(String locality, String timeZoneName) {
        ((TextView)findViewById(R.id.localityText)).setText(locality);
        ((TextView)findViewById(R.id.timezoneText)).setText(timeZoneName);
    }
}
