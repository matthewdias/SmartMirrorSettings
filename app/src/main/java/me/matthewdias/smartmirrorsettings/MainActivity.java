package me.matthewdias.smartmirrorsettings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    LocationFragment locationFragment;
    CalendarFragment calendarFragment;
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
//                case R.id.navigation_notifications:
//                    viewPager.setCurrentItem(2);
//                    return true;
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

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(pageListener);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        locationFragment = new LocationFragment();
        adapter.addFragment(locationFragment);
        calendarFragment = new CalendarFragment();
        adapter.addFragment(calendarFragment);
        viewPager.setAdapter(adapter);
    }

}
