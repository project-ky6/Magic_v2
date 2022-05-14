package com.example.magiccoffee_v2.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.GUI.Adapter.ViewPagerAdapter;
import com.example.magiccoffee_v2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private ViewPager viewPager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.bottomNav_view);
        viewPager = findViewById(R.id.view_pager);

        Bundle bundle = getIntent().getBundleExtra("Data");
        user = (User) bundle.getSerializable("User");


        setUpViewPager();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnHome:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.mnProduct:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.mnHeart:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.mnMy:
                        viewPager.setCurrentItem(3);
                        return true;
                    default:
                        viewPager.setCurrentItem(0);
                        return true;
                }
            }
        });
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, user);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }
}