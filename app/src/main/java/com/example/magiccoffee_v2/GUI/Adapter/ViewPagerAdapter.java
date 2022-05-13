package com.example.magiccoffee_v2.GUI.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.GUI.Fragment.AccountFragment;
import com.example.magiccoffee_v2.GUI.Fragment.HeartFragment;
import com.example.magiccoffee_v2.GUI.Fragment.HomeFragment;
import com.example.magiccoffee_v2.GUI.Fragment.ProductFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private User user;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, User user) {
        super(fm, behavior);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HomeFragment  homeFragment = new HomeFragment(user);
                return homeFragment;
            case 1:
                ProductFragment productFragment = new ProductFragment();
                return productFragment;
            case 2:
                HeartFragment heartFragment = new HeartFragment();
                return heartFragment;
            case 3:
                AccountFragment accountFragment = new AccountFragment(user);
                return accountFragment;
            default:
                HomeFragment homeFragment2 = new HomeFragment(user);
                return homeFragment2;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
