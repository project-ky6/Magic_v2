package com.example.magiccoffee_v2.gui.admin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.Window;

import com.example.magiccoffee_v2.dto.Member;
import com.example.magiccoffee_v2.gui.dataLocal.DataLocalManager;
import com.example.magiccoffee_v2.gui.login.LoginMemberActivity;
import com.example.magiccoffee_v2.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer_layout;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_THONGKE = 1;

    private int mCurrentFragment = FRAGMENT_HOME;

    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.primary));
        }
        DataLocalManager.init(getApplicationContext());

        drawer_layout = findViewById(R.id.drawer_layout);

        Bundle bundle = getIntent().getBundleExtra("Data");

        member = (Member)bundle.getSerializable("Member");

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new DashboardFragment(member));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                System.out.println(member.getId());
                Intent intent = new Intent(AdminActivity.this, SettingActivity.class);
                intent.putExtra("ID", member.getId());
                startActivity(intent);

                return true;
            case R.id.action_logout:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new DashboardFragment(member));
                mCurrentFragment = FRAGMENT_HOME;
            }
        }else if(id == R.id.nav_statistical){
            if(mCurrentFragment != FRAGMENT_THONGKE){
                replaceFragment(new StatisticalFragment(member));
                mCurrentFragment = FRAGMENT_THONGKE;
            }
        }
        else if(id == R.id.nav_logout){
            Logout();
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout() {
        Intent intent = new Intent(AdminActivity.this, LoginMemberActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}